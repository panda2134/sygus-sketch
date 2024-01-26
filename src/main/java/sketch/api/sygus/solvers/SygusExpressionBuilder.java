package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.EmptyVisitor;
import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;
import sketch.api.sygus.util.exception.OutputParseException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SygusExpressionBuilder extends EmptyVisitor {

    protected Map<String, SygusExpression> cxt;
    protected Map<String, Integer> cnt;

    public SygusExpressionBuilder(Set<String> nonterminals, Map<String, SygusExpression> cxt) {
        this.cxt = cxt;
        this.cnt = nonterminals.stream()
                .collect(Collectors.toMap(key -> key, key -> 0));
    }

    public SygusExpression doRHSTerm(RHSTerm t) {
        return (SygusExpression) t.accept(this);
    }

    @Override
    public SygusExpression visitRHSNonterminal(RHSNonterminal non) {
        String nonterminalID = non.getNonterminal().getName();
        if (cnt.containsKey(nonterminalID)) {
            int nonterminalCnt = cnt.get(nonterminalID);
            String varID = String.format("var_%s_%d", nonterminalID, nonterminalCnt++);
            cnt.put(nonterminalID, nonterminalCnt);

            if (cxt.containsKey(varID))
                return cxt.get(varID);
            else
                throw new OutputParseException("Unknown variable: " + varID);
        } else
            throw new OutputParseException("Unknown nonterminal: " + nonterminalID);
    }

    @Override
    public SygusExpression visitRHSConstInt(RHSConstInt n) {
        return new ConstInt(n.getValue());
    }

    @Override
    public SygusExpression visitRHSConstBool(RHSConstBool b) {
        return new ConstBool(b.getValue());
    }

    @Override
    public SygusExpression visitRHSVariable(RHSVariable v) {
        return new Variable(v.getID(), v.getType());
    }

    @Override
    public SygusExpression visitRHSUnaryOp(RHSUnaryOp op) {
        SygusExpression e = doRHSTerm(op.getExpr());
        return new ExprUnaryOp(op.getOp(), e);
    }

    @Override
    public SygusExpression visitRHSBinaryOp(RHSBinaryOp op) {
        SygusExpression left = doRHSTerm(op.getLeft());
        SygusExpression right = doRHSTerm(op.getRight());
        return new ExprBinaryOp(op.getOp(), left, right);
    }

    @Override
    public SygusExpression visitRHSIfThenElse(RHSIfThenElse ite) {
        SygusExpression cond = doRHSTerm(ite.getCond());
        SygusExpression cons = doRHSTerm(ite.getCons());
        SygusExpression alt = doRHSTerm(ite.getAlt());
        return new ExprIfThenElse(cond, cons, alt);
    }

    @Override
    public SygusExpression visitRHSFunctionCall(RHSFunctionCall fc) {
        List<SygusExpression> args = fc.getArgs().stream()
                .map(this::doRHSTerm)
                .collect(Collectors.toList());
        return new ExprFunctionCall(fc.getFunctionID(), args);
    }
}
