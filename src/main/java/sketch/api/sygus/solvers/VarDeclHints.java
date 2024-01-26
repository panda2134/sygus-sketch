package sketch.api.sygus.solvers;

import sketch.util.Pair;

import java.util.List;
import java.util.Map;

public class VarDeclHints {
    private Map<String, List<List<Pair<String, Integer>>>> hints;

    public VarDeclHints(Map<String, List<List<Pair<String, Integer>>>> hints) {
        this.hints = hints;
    }

    public List<Pair<String, Integer>> varDeclsForChoice(String nonterminalID, int choice) {
        return hints.get(nonterminalID).get(choice);
    }
}
