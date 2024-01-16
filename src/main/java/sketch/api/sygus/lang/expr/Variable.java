package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * A SyGuS variable.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Variable extends Expression {

    private String id;
    private String type;

    public Variable(String id, String type) {
        super();
        this.type = type;
        this.id = String.valueOf(id);
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitVariable(this); }

    public String getID() { return id; }
    public String getType() { return type; }

    public String toString() { return id; }
    public String toFullString() {
        return String.format("(%s %s)", id, type);
    }
}
