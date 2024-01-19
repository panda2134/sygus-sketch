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

    public RHSVariable(String id) {
        super();
        this.id = id;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitRHSVariable(this); }

    public String getID() { return id; }

    public String toString() { return id; }
}

