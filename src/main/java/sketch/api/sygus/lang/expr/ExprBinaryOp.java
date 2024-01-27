package sketch.api.sygus.lang.expr;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for binary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprBinaryOp extends SygusExpression {

    private BinaryOp op;
    private SygusExpression left, right;
    public ExprBinaryOp(BinaryOp op, SygusExpression left, SygusExpression right) {
        super();
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitExprBinaryOp(this);
    }

    public BinaryOp getOp() {
        return op;
    }

    public SygusExpression getLeft() {
        return left;
    }

    public SygusExpression getRight() {
        return right;
    }

    public String binaryOpToString(BinaryOp op) {
        switch (op) {
            case BINOP_ADD:
                return "+";
            case BINOP_SUB:
                return "-";
            case BINOP_MUL:
                return "*";
            case BINOP_DIV:
                return "/";
            case BINOP_MOD:
                return "mod";
            case BINOP_AND:
                return "and";
            case BINOP_OR:
                return "or";
            case BINOP_EQ:
                return "=";
            case BINOP_NEQ:
                return "!=";
            case BINOP_LE:
                return "<=";
            case BINOP_LT:
                return "<";
            case BINOP_GE:
                return ">=";
            case BINOP_GT:
                return ">";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return String.format("(%s %s %s)", binaryOpToString(op), left.toString(), right.toString());
    }

    public enum BinaryOp {
        BINOP_ADD, BINOP_SUB, BINOP_MUL, BINOP_DIV, BINOP_MOD,
        BINOP_AND, BINOP_OR,
        BINOP_EQ, BINOP_NEQ, BINOP_LT, BINOP_LE, BINOP_GT, BINOP_GE,
        // below expressions are not supported now
        BINOP_BAND, BINOP_BOR, BINOP_BXOR,
        BINOP_LSHIFT, BINOP_RSHIFT
    }
}
