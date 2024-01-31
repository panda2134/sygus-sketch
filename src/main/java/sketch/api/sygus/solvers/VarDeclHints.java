package sketch.api.sygus.solvers;

import sketch.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VarDeclHints {
    private Map<String, List<List<Pair<String, Integer>>>> hints;

    public VarDeclHints(Map<String, List<List<Pair<String, Integer>>>> hints) {
        this.hints = hints;
    }

    public List<Pair<String, Integer>> varDeclsForChoice(String nonterminalID, int choice) {
        List<List<Pair<String, Integer>>> varDeclsLists = hints.get(nonterminalID);
        // Return empty list when choice is out of index for ease of implementation
        if (choice >= varDeclsLists.size()) {
            return new ArrayList<>();
        } else {
            return hints.get(nonterminalID).get(choice);
        }
    }
}
