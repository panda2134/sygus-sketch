package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for constant Boolean expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSConstBool extends RHSConstant {

    private boolean value;

    public RHSConstBool(boolean value) {
        super();
        this.value = value;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitRHSConstBool(this);
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
