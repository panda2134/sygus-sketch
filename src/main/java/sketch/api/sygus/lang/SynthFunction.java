package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.Variable;
import sketch.api.sygus.lang.grammar.Grammar;

import java.util.List;

/**
 * Class for synthesis target function
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SynthFunction extends SygusNode {
    private String functionID;
    private List<Variable> args;
    private String returnType;
    private Grammar grammar;

    public SynthFunction(String functionID, List<Variable> args, String returnType, Grammar grammar) {
        this.functionID = functionID;
        this.args = args;
        this.returnType = returnType;
        this.grammar = grammar;
    }

    public void accept(SygusNodeVisitor visitor) { visitor.visitSynthFunction(this); }

    public String getFunctionID() { return functionID; }
    public List<Variable> getArgs() { return args; }
    public String getReturnType() { return returnType; }
    public Grammar getGrammar() { return grammar; }
}
