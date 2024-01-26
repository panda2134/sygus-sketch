package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;
import sketch.api.sygus.lang.type.TypePrimitive;

public class EmptyVisitor implements SygusNodeVisitor {

    @Override
    public Object visitSygusProblem(SygusProblem problem) {
        return null;
    }

    @Override
    public Object visitSynthFunction(SynthFunction func) {
        return null;
    }

    @Override
    public Object visitTypePrimitive(TypePrimitive ty) {
        return null;
    }

    @Override
    public Object visitConstBool(ConstBool b) {
        return null;
    }

    @Override
    public Object visitConstInt(ConstInt n) {
        return null;
    }

    @Override
    public Object visitExprUnaryOp(ExprUnaryOp e) {
        return null;
    }

    @Override
    public Object visitExprBinaryOp(ExprBinaryOp e) {
        return null;
    }

    @Override
    public Object visitExprIfThenElse(ExprIfThenElse e) {
        return null;
    }

    @Override
    public Object visitFunctionCall(ExprFunctionCall f) {
        return null;
    }

    @Override
    public Object visitVariable(Variable v) {
        return null;
    }

    @Override
    public Object visitGrammar(Grammar g) {
        return null;
    }

    @Override
    public Object visitNonterminal(Nonterminal n) {
        return null;
    }

    @Override
    public Object visitProduction(Production prod) {
        return null;
    }

    @Override
    public Object visitRHSVariable(RHSVariable v) {
        return null;
    }

    @Override
    public Object visitRHSNonterminal(RHSNonterminal non) {
        return null;
    }

    @Override
    public Object visitRHSConstBool(RHSConstBool b) {
        return null;
    }

    @Override
    public Object visitRHSConstInt(RHSConstInt n) {
        return null;
    }

    @Override
    public Object visitRHSFunctionCall(RHSFunctionCall f) {
        return null;
    }

    @Override
    public Object visitRHSUnaryOp(RHSUnaryOp e) {
        return null;
    }

    @Override
    public Object visitRHSBinaryOp(RHSBinaryOp e) {
        return null;
    }

    @Override
    public Object visitRHSIfThenElse(RHSIfThenElse e) {
        return null;
    }
}
