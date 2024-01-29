package sketch.api.sygus.lang.type;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.util.exception.ParseException;

/**
 * Class for primitive types
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class TypePrimitive extends SygusType {

    private PredefinedType ty;

    public final static TypePrimitive intType = new TypePrimitive("Int");
    public final static TypePrimitive boolType = new TypePrimitive("Bool");

    public TypePrimitive(String id) {
        super(id);
        if (id.equals("Int")) {
            ty = PredefinedType.TYPE_INT;
        } else if (id.equals("Bool")) {
            ty = PredefinedType.TYPE_BOOLEAN;
        } else {
            throw new ParseException("Unknown primitive type");
        }
    }

    @Override
    public Object accept(SygusNodeVisitor v) {
        return v.visitTypePrimitive(this);
    }

    public PredefinedType getPredefinedType() {
        return ty;
    }

    public enum PredefinedType {
        TYPE_INT,
        TYPE_BOOLEAN
    }
}
