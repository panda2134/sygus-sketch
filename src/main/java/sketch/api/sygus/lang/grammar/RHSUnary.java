package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for unary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSUnary extends RHSTerm {

    public enum UnaryOp {
        UNOP_NOT, UNOP_BNOT, UNOP_NEG,
        // below expressions are not supported now
        UNOP_PREINC, UNOP_POSTINC, UNOP_PREDEC, UNOP_POSTDEC
    }

    private UnaryOp op;
    private RHSTerm expr;

    public RHSUnary(UnaryOp op, RHSTerm expr) {
        super();
        this.op = op;
        this.expr = expr;
    }

    public void accept(SygusNodeVisitor visitor) { visitor.visitRHSUnary(this); }

    public UnaryOp getOp() { return op; }
    public RHSTerm getExpr() { return expr; }

    public String unaryOpToString(UnaryOp op) {
        switch(op) {
            case UNOP_NOT:
            case UNOP_BNOT:
                return "not";
            case UNOP_NEG:
                return "neg";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return String.format("(%s %s)", unaryOpToString(op), expr.toString());
    }
}
