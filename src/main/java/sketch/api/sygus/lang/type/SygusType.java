package sketch.api.sygus.lang.type;

import sketch.api.sygus.lang.SygusNode;

/**
 * Abstract class for variable data types.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class SygusType extends SygusNode {

    private String id;

    public SygusType(String id) {
        this.id = String.valueOf(id);
    }

    public static boolean isPrimitiveId(String id) {
        return (id.equals("int") || id.equals("boolean"));
    }

    public boolean isStruct() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public String getID() {
        return id;
    }

    public String toString() {
        return id;
    }
}
