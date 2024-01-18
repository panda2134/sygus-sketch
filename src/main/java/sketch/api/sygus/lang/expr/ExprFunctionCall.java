package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class for function call expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprFunctionCall extends SygusExpression {

    private String functionID;
    private List<SygusExpression> args;

    public ExprFunctionCall(String functionId, List<SygusExpression> args) {
        super();
        this.functionID = String.valueOf(functionId);
        this.args = new ArrayList<>(args);
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitFunctionCall(this); }

    public String getFunctionID() { return functionID; }
    public List<SygusExpression> getArgs() { return args; }

    public String toString() {
        String argsString = args.stream().map(SygusExpression::toString)
                .collect(Collectors.joining(" "));
        return String.format("(%s %s)", functionID, argsString);
    }
}