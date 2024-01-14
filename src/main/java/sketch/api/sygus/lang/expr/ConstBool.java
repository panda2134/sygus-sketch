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

    public void accept(SygusNodeVisitor visitor) { visitor.visitConstBool(this); }

    public boolean getValue() { return value; }

    public String toString() { return String.valueOf(value); }
}
