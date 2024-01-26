package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.SynthFunction;
import sketch.api.sygus.lang.expr.SygusExpression;
import sketch.api.sygus.lang.grammar.Grammar;
import sketch.api.sygus.lang.grammar.Production;
import sketch.api.sygus.util.exception.OutputParseException;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.SymbolTable;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.passes.lowering.SymbolTableVisitor;
import sketch.compiler.solvers.constructs.ValueOracle;
import sketch.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SynthResultExtractor extends SymbolTableVisitor {
    private Map<String, SynthFunction> targetFunctionMap;
    private Map<String, SygusExpression> synthResult;
    private Set<String> nonterminals;
    private Map<String, Production> productionMap;
    private ValueOracle oracle;
    private VarDeclHints varDeclHints;

    public SynthResultExtractor(
            List<SynthFunction> targetFunctions,
            ValueOracle oracle,
            VarDeclHints varDeclHints
    ) {
        super(null);
        this.targetFunctionMap = targetFunctions.stream()
                .collect(Collectors.toMap(SynthFunction::getFunctionID, synthFunction -> synthFunction));
        this.synthResult = new HashMap<>();
        this.oracle = oracle;
        this.varDeclHints = varDeclHints;
    }

    public Map<String, SygusExpression> getSynthResult() {
        return synthResult;
    }

    public Object visitFunction(Function func)
    {
        String functionName = func.getName();
        if (targetFunctionMap.containsKey(functionName)) {
            Grammar g = targetFunctionMap.get(functionName).getGrammar();
            productionMap = g.getRules().stream()
                    .collect(Collectors.toMap(rule -> rule.getLHS().getName(), rule -> rule));
            nonterminals = productionMap.keySet();

            SygusExpression e;

            Statement body = func.getBody();
            if (!body.isBlock()) {
                throw new OutputParseException("Synthesized function body must be a block");
            }

            Statement mainImpl = ((StmtBlock) body).getStmts().get(2);
            if (!mainImpl.isBlock()) {
                throw new OutputParseException("Failed to get main part of implementation body");
            }

            Production startProd = g.getRules().get(0);
            e = extractExpression(startProd, mainImpl);

            synthResult.put(functionName, e);
        }

        return super.visitFunction(func);
    }

    private SygusExpression extractExpression(Production prod, Statement mainImpl) {
        List<Statement> stmts = ((StmtBlock) mainImpl).getStmts();
        Statement condsBlock = stmts.get(stmts.size() - 1);
        stmts = ((StmtBlock) condsBlock).getStmts();
        Map<String, SygusExpression> intermediateVars = new HashMap<>();

        // Find the first conditional block
        int idx = 0;
        for(Statement stmt: stmts) {
            if (stmt instanceof StmtBlock) {
                List<Statement> innerStmts = ((StmtBlock) stmt).getStmts();
                if (innerStmts.get(innerStmts.size() - 1) instanceof StmtIfThen)
                    break;
            }
            idx += 1;
        }

        int firstCond = idx - 1;
        List<Statement> firstConditionalBlock = ((StmtBlock) stmts.get(firstCond)).getStmts();
        firstConditionalBlock = ((StmtBlock) firstConditionalBlock.get(0)).getStmts();
        StmtIfThen firstConditional = (StmtIfThen) firstConditionalBlock.get(firstConditionalBlock.size() - 1);
        ExprStar firstStar = (ExprStar) firstConditional.getCond();

        Type t = firstStar.getType();
        ExprConstInt value = (ExprConstInt) oracle.popValueForNode(firstStar.getDepObject(0), t);
        if (value.getVal() != 0)
            return constructExpressionFromChoice(prod, intermediateVars, 0);

        // If the first conditional was not chosen, look at other conditional blocks
        for (idx = firstCond + 1; idx < stmts.size(); idx++) {
            List<Statement> block = ((StmtBlock) stmts.get(idx)).getStmts();

            List<StmtIfThen> ifThenStmts = block.stream()
                    .filter(innerStmt -> (innerStmt instanceof StmtIfThen))
                    .map(innerStmt -> (StmtIfThen) innerStmt)
                    .collect(Collectors.toList());

            List<Pair<String, Integer>> varDeclList = varDeclHints
                    .varDeclsForChoice(prod.getLHS().getName(), idx - firstCond);

            // Handle variable declaration which appears before choice block
            int cnt = 0;
            for(StmtIfThen stmtInner: ifThenStmts.subList(0, ifThenStmts.size() - 1)) {
                StmtBlock innerBlock = (StmtBlock) ((StmtBlock) stmtInner.getCons()).getStmts().get(0);
                Pair<String, Integer> varDeclHint = varDeclList.get(cnt++);
                String nonterminalID = varDeclHint.getFirst();
                String varID = String.format("var_%s_%d", nonterminalID, varDeclHint.getSecond());

                SygusExpression innerExpr = extractExpression(productionMap.get(nonterminalID), innerBlock);
                intermediateVars.put(varID, innerExpr);
            }

            StmtIfThen stmt = ifThenStmts.get(ifThenStmts.size() - 1);
            stmt = (StmtIfThen) ((StmtBlock) stmt.getCons()).getStmts().get(0);
            ExprStar star = (ExprStar) stmt.getCond();
            t = star.getType();
            value = (ExprConstInt) oracle.popValueForNode(star.getDepObject(0), t);
            if (value.getVal() != 0)
                return constructExpressionFromChoice(prod, intermediateVars, idx - firstCond);
        }

        throw new OutputParseException("Failed to parse generator choice");
    }

    private SygusExpression constructExpressionFromChoice(
            Production prod,
            Map<String, SygusExpression> intermediateVars,
            int choice
    ) {
        SygusExpressionBuilder builder = new SygusExpressionBuilder(nonterminals, intermediateVars);
        return builder.doRHSTerm(prod.getRHSList().get(choice));
    }
}