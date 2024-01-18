package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;

public interface SygusNodeVisitor {
    public Object visitSygusProblem(SygusProblem problem);
    public Object visitSynthFunction(SynthFunction func);

    public Object visitConstBool(ConstBool b);
    public Object visitConstInt(ConstInt n);
    public Object visitExprUnaryOp(ExprUnaryOp e);
    public Object visitExprBinaryOp(ExprBinaryOp e);
    public Object visitFunctionCall(ExprFunctionCall f);
    public Object visitVariable(Variable v);

    public Object visitGrammar(Grammar g);
    public Object visitNonterminal(Nonterminal n);
    public Object visitProduction(Production prod);
    public Object visitRHSNonterminal(RHSNonterminal non);
    public Object visitRHSConstBool(RHSConstBool b);
    public Object visitRHSConstInt(RHSConstInt n);
    public Object visitRHSFunctionCall(RHSFunctionCall f);
    public Object visitRHSUnaryOp(RHSUnaryOp e);
    public Object visitRHSBinaryOp(RHSBinaryOp e);
}
