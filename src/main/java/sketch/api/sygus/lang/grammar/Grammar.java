package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNode;
import sketch.api.sygus.lang.SygusNodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class Grammar extends SygusNode {
    private List<Production> rules;

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitGrammar(this); }

    public Grammar(List<Production> rules) {
        this.rules = rules;
    }

    public static Grammar empty() {
        return new Grammar(new ArrayList<Production>());
    }

    public List<Production> getRules() { return rules; }
}
