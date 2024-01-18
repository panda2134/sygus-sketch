package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.SygusExpression;
import sketch.api.sygus.lang.expr.Variable;

import java.util.List;
import java.util.ArrayList;

/**
 * Class for SyGuS problem
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SygusProblem extends SygusNode {
    private List<Variable> variables;
    private List<SynthFunction> targetFunctions;
    private List<SygusExpression> constraints;

    private SygusProblem(List<Variable> variables, List<SynthFunction> targetFunctions, List<SygusExpression> constraints) {
        this.variables = variables;
        this.targetFunctions = targetFunctions;
        this.constraints = constraints;
    }

    public SygusProblem emptyProblem() {
        return new SygusProblem(new ArrayList<Variable>(), new ArrayList<SynthFunction>(), new ArrayList<SygusExpression>());
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitSygusProblem(this); }

    public List<Variable> getVariables() { return variables; }
    public List<SynthFunction> getTargetFunctions() { return targetFunctions; }
    public List<SygusExpression> getConstraints() { return constraints; }

    public void addVariable(Variable v) { variables.add(v); }
    public void addTargetFunction(SynthFunction f) { targetFunctions.add(f); }
    public void addConstraints(SygusExpression e) { constraints.add(e); }

    public boolean removeVariable(Variable v) { return variables.remove(v); }
    public boolean removeTargetFunction(SynthFunction f) { return targetFunctions.remove(f); }
    public boolean removeConstraints(SygusExpression e) { return constraints.remove(e); }

    public Output solve() {
        return null;
    }
}
