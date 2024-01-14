package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.Expression;
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
    private List<Expression> constraints;

    private SygusProblem(List<Variable> variables, List<SynthFunction> targetFunctions, List<Expression> constraints) {
        this.variables = variables;
        this.targetFunctions = targetFunctions;
        this.constraints = constraints;
    }

    public SygusProblem emptyProblem() {
        return new SygusProblem(new ArrayList<Variable>(), new ArrayList<SynthFunction>(), new ArrayList<Expression>());
    }

    public void accept(SygusNodeVisitor visitor) { visitor.visitSygusProblem(this); }

    public List<Variable> getVariables() { return variables; }
    public List<SynthFunction> getTargetFunctions() { return targetFunctions; }
    public List<Expression> getConstraints() { return constraints; }

    public void addVariable(Variable v) { variables.add(v); }
    public void addTargetFunction(SynthFunction f) { targetFunctions.add(f); }
    public void addConstraints(Expression e) { constraints.add(e); }

    public boolean removeVariable(Variable v) { return variables.remove(v); }
    public boolean removeTargetFunction(SynthFunction f) { return targetFunctions.remove(f); }
    public boolean removeConstraints(Expression e) { return constraints.remove(e); }

    public Output solve() {
        return null;
    }
}
