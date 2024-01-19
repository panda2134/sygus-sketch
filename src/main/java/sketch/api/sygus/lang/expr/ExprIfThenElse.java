package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for if-then-else expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprIfThenElse extends SygusExpression {

    private SygusExpression cond;
    private SygusExpression cons;
    private SygusExpression alt;

    public ExprIfThenElse(SygusExpression cond, SygusExpression cons, SygusExpression alt) {
        super();
        this.cond = cond;
        this.cons = cons;
        this.alt = alt;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitExprIfThenElse(this);
    }

    public SygusExpression getCond() { return cond; }
    public SygusExpression getCons() { return cons; }
    public SygusExpression getAlt() { return alt; }

    public String toString() {
        return String.format("(ite %s %s %s)", cond.toString(), cons.toString(), alt.toString());
    }
}