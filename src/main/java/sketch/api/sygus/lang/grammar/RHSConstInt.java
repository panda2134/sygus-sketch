package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for constant integer expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSConstInt extends RHSConstant {

    private int value;

    public RHSConstInt(int value) {
        super();
        this.value = value;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitRHSConstInt(this);
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
