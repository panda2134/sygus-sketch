package sketch.api.sygus.util.exception;

/**
 * Exception from constructing sketch query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SketchConversionException extends SolverException {
    private static final long serialVersionUID = 6075166005096891208L;

    public SketchConversionException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Sketch Query Construction Error";
    }
}
