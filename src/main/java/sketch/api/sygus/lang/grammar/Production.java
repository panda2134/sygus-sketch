package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNode;
import sketch.api.sygus.lang.SygusNodeVisitor;

import java.util.List;

public class Production extends SygusNode {
    private Nonterminal lhs;
    private List<RHSTerm> rhsList;

    public Production(Nonterminal lhs, List<RHSTerm> rhsList) {
        this.lhs = lhs;
        this.rhsList = rhsList;
    }

    public Object accept(SygusNodeVisitor visitor) {
        return visitor.visitProduction(this);
    }

    public Nonterminal getLHS() {
        return lhs;
    }

    public List<RHSTerm> getRHSList() {
        return rhsList;
    }
}
