package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for function call expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSFunctionCall extends RHSTerm {

    private String functionID;
    private List<RHSTerm> args;

    public RHSFunctionCall(String functionID, List<RHSTerm> args) {
        super();
        this.functionID = String.valueOf(functionID);
        this.args = new ArrayList<>(args);
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitRHSFunctionCall(this); }

    public String getFunctionID() { return functionID; }
    public List<RHSTerm> getArgs() { return args; }

    public String toString() {
        String argsString = args.stream().map(RHSTerm::toString)
                .collect(Collectors.joining(" "));
        return String.format("(%s %s)", functionID, argsString);
    }
}