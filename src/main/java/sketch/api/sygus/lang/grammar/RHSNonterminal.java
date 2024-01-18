package sketch.api.sygus.lang.grammar;

import sketch.api.sygus.lang.SygusNodeVisitor;

/**
 * Class for nonterminal appears in RHS of production rule.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSNonterminal extends RHSTerm {
    private Nonterminal nonterminal;

    public RHSNonterminal(Nonterminal nonterminal) {
        this.nonterminal = nonterminal;
    }

    public Object accept(SygusNodeVisitor visitor) { return visitor.visitRHSNonterminal(this); }

    public Nonterminal getNonterminal() { return nonterminal; }

    public String toString() { return nonterminal.toString(); }
}

