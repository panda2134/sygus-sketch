package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNode;
import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * A nonterminal of SyGuS grammar.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Nonterminal extends SygusNode {
    private String name;
    private String type;

    public Nonterminal(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitNonterminal(this); }

    public String getName() { return name; }
    public String getType() { return type; }

    public String toString() { return name; }
    public String toFullString() {
        return String.format("(%s %s)", name, type);
    }
}
