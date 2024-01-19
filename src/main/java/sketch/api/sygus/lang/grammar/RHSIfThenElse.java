package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.expr.SygusExpression;

/**
 * Class for if-then-else expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSIfThenElse extends RHSTerm {

    private RHSTerm cond;
    private RHSTerm cons;
    private RHSTerm alt;

    public RHSIfThenElse(RHSTerm cond, RHSTerm cons, RHSTerm alt) {
        super();
        this.cond = cond;
        this.cons = cons;
        this.alt = alt;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitRHSIfThenElse(this);
    }

    public RHSTerm getCond() { return cond; }
    public RHSTerm getCons() { return cons; }
    public RHSTerm getAlt() { return alt; }

    public String toString() {
        return String.format("(ite %s %s %s)", cond.toString(), cons.toString(), alt.toString());
    }
}