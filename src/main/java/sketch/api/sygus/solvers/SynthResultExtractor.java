package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.SynthFunction;
import sketch.api.sygus.lang.expr.SygusExpression;
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
    private List<SynthFunction> targetFunctions;
    private Set<String> targetFunctionSymbols;
    private Map<String, SygusExpression> synthResult;
    private ValueOracle oracle;

    public SynthResultExtractor(List<SynthFunction> targetFunctions, ValueOracle oracle) {
        super(null);
        this.targetFunctions = targetFunctions;
        this.targetFunctionSymbols = targetFunctions.stream()
                .map(SynthFunction::getFunctionID)
                .collect(Collectors.toSet());
        this.synthResult = new HashMap<>();
        this.oracle = oracle;
    }

    public Object visitFunction(Function func)
    {
        String functionName = func.getName();
        if (targetFunctionSymbols.contains(functionName)) {
            SygusExpression e;

            Statement body = func.getBody();
            if (!body.isBlock()) {
                throw new OutputParseException("Synthesized function body must be a block");
            }

            Statement mainImpl = ((StmtBlock) body).getStmts().get(2);
            if (!mainImpl.isBlock()) {
                throw new OutputParseException("Failed to get main part of implementation body");
            }

            Pair<Integer, Statement> choice = getChoice(mainImpl);
            System.out.println(choice.getFirst());
            System.out.println(choice.getSecond());

            // TODO Implement
            e = null;

            synthResult.put(functionName, e);
        }

        Object result = super.visitFunction(func);
        return result;
    }

    public Pair<Integer, Statement> getChoice(Statement mainImpl) {
        List<Statement> stmts = ((StmtBlock) mainImpl).getStmts();
        Statement condsBlock = stmts.get(stmts.size() - 1);
        stmts = ((StmtBlock) condsBlock).getStmts();

        int idx = 0;
        for(Statement stmt: stmts) {
            System.out.println(idx);
            System.out.println(stmt.getClass());
            System.out.println(stmt);
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
            return new Pair(0, firstConditional.getCons());

        for (idx = firstCond + 1; idx < stmts.size(); idx++) {
            List<Statement> block = ((StmtBlock) stmts.get(idx)).getStmts();

            List<StmtIfThen> ifThenStmts = block.stream()
                    .filter(innerStmt -> (innerStmt instanceof StmtIfThen))
                    .map(innerStmt -> (StmtIfThen) innerStmt)
                    .collect(Collectors.toList());

            for(StmtIfThen stmtInner: ifThenStmts.subList(0, ifThenStmts.size() - 1)) {
                StmtBlock innerBlock = (StmtBlock) ((StmtBlock) stmtInner.getCons()).getStmts().get(0);

                Pair<Integer, Statement> innerChoice = getChoice(innerBlock);
                System.out.println(innerChoice.getFirst());
                System.out.println(innerChoice.getSecond());
            }

            StmtIfThen stmt = ifThenStmts.get(ifThenStmts.size() - 1);
            stmt = (StmtIfThen) ((StmtBlock) stmt.getCons()).getStmts().get(0);
            ExprStar star = (ExprStar) stmt.getCond();
            t = star.getType();
            value = (ExprConstInt) oracle.popValueForNode(star.getDepObject(0), t);
            if (value.getVal() != 0)
                return new Pair(idx - firstCond, stmt.getCons());
        }

        throw new OutputParseException("Failed to parse generator choice");
    }
}