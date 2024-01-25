package sketch.api.sygus.util.exception;

/**
 * Exception from constructing sketch query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class OutputParseException extends SolverException {
    private static final long serialVersionUID = 5725610527291503461L;

    public OutputParseException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Sketch Output Parse Error";
    }
}
