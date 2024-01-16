package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for constant integer expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ConstInt extends Constant {

    private int value;

    public ConstInt(int value) {
        super();
        this.value = value;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitConstInt(this); }

    public int getValue() { return this.value; }

    public String toString() { return String.valueOf(value); }
}
