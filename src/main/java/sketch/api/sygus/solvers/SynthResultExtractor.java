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

            Pair<Integer, Statement> choice = getChoice(func.getBody());

            System.out.println(choice.getFirst());
            System.out.println(choice.getSecond());

            // TODO Implement
            e = null;

            synthResult.put(functionName, e);
        }

        Object result = super.visitFunction(func);
        return result;
    }

    public Pair<Integer, Statement> getChoice(Statement body) {
        if (!body.isBlock()) {
            throw new OutputParseException("Synthesized function body must be a block");
        }

        Statement mainImpl = ((StmtBlock) body).getStmts().get(2);
        if (!mainImpl.isBlock()) {
            throw new OutputParseException("Failed to get main part of implementation body");
        }

        List<Statement> stmts = ((StmtBlock) mainImpl).getStmts();
        Statement condsBlock = stmts.get(stmts.size() - 1);
        stmts = ((StmtBlock) condsBlock).getStmts();

        int idx = 0;
        int firstCond = -1;
        for(Statement stmt: stmts) {
            if (stmt instanceof StmtIfThen) {
                firstCond = idx - 1;
                break;
            }
            idx += 1;
        }

        if (idx == stmts.size() || firstCond < 0) {
            throw new OutputParseException("Failed to find conditionals");
        }

        StmtIfThen firstConditional = (StmtIfThen) ((StmtBlock) stmts.get(firstCond)).getStmts().get(0);
        ExprStar firstStar = (ExprStar) firstConditional.getCond();

        Type t = firstStar.getType();
        ExprConstInt value = (ExprConstInt) oracle.popValueForNode(firstStar.getDepObject(0), t);
        if (value.getVal() != 0)
            return new Pair(0, firstConditional.getCons());

        for (idx = firstCond + 1; idx < stmts.size(); idx++) {
            StmtIfThen stmt = (StmtIfThen) stmts.get(idx);
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