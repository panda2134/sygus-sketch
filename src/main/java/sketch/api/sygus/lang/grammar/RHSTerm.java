package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNode;

/**
 * An abstract class for SyGuS expression.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class RHSTerm extends SygusNode {
    public abstract String toString();
    public boolean isConstant() { return false; }
}
