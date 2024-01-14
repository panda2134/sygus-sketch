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
public class ExprFunctionCall extends Expression {

    private String functionID;
    private List<Expression> args;

    public ExprFunctionCall(String functionId, List<Expression> args) {
        super();
        this.functionID = String.valueOf(functionId);
        this.args = new ArrayList<>(args);
    }

    public void accept(SygusNodeVisitor visitor) { visitor.visitFunctionCall(this); }

    public String getFunctionID() { return functionID; }
    public List<Expression> getArgs() { return args; }

    public String toString() {
        String argsString = args.stream().map(Expression::toString)
                .collect(Collectors.joining(" "));
        return String.format("(%s %s)", functionID, argsString);
    }
}