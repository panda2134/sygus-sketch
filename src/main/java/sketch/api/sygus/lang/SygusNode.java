package sketch.api.sygus.lang;

public abstract class SygusNode {
    public abstract Object accept(SygusNodeVisitor visitor);
}
