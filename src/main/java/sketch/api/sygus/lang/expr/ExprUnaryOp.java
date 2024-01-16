package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for unary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprUnaryOp extends Expression {

    public enum UnaryOp {
        UNOP_NOT, UNOP_BNOT, UNOP_NEG,
        // below expressions are not supported now
        UNOP_PREINC, UNOP_POSTINC, UNOP_PREDEC, UNOP_POSTDEC
    }

    private UnaryOp op;
    private Expression expr;

    public ExprUnaryOp(UnaryOp op, Expression expr) {
        super();
        this.op = op;
        this.expr = expr;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitExprUnaryOp(this); }

    public UnaryOp getOp() { return op; }
    public Expression getExpr() { return expr; }

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
