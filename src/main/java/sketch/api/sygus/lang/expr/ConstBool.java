package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for constant Boolean expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ConstBool extends Constant {

    private boolean value;

    public ConstBool(boolean value) {
        super();
        this.value = value;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitConstBool(this);
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
