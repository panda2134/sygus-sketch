package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.type.SygusType;

/**
 * A SyGuS variable.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Variable extends SygusExpression {

    private String id;
    private SygusType type;

    public Variable(String id, SygusType type) {
        super();
        this.type = type;
        this.id = id;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitVariable(this);
    }

    public String getID() {
        return id;
    }

    public SygusType getType() {
        return type;
    }

    public String toString() {
        return id;
    }

    public String toFullString() {
        return String.format("(%s %s)", id, type.toString());
    }
}
