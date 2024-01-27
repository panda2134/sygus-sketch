package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.type.SygusType;

/**
 * Class for variable appears in RHS of production rule.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSVariable extends RHSTerm {
    private String id;
    private SygusType type;

    public RHSVariable(String id, SygusType type) {
        super();
        this.id = id;
        this.type = type;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitRHSVariable(this);
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

