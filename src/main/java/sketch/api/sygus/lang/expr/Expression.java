package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNode;

/**
 * An abstract class for SyGuS expression.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class Expression extends SygusNode {
    public abstract String toString();
    public boolean isConstant() { return false; }
}
