package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.expr.ExprBinaryOp.BinaryOp;

/**
 * Class for binary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSBinaryOp extends RHSTerm {

    private BinaryOp op;
    private RHSTerm left, right;

    public RHSBinaryOp(BinaryOp op, RHSTerm left, RHSTerm right) {
        super();
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitRHSBinaryOp(this); }

    public BinaryOp getOp() { return op; }
    public RHSTerm getLeft() { return left; }
    public RHSTerm getRight() { return right; }

    public String binaryOpToString(BinaryOp op) {
        switch(op) {
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
}
