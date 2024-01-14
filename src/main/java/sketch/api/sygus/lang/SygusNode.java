package sketch.api.sygus.lang;

public abstract class SygusNode {
    public abstract void accept(SygusNodeVisitor visitor);
}
