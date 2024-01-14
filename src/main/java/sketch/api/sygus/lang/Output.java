package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.Expression;

import java.util.Map;

public class Output {
    public enum Result {
        REALIZABLE,
        UNKNOWN,
        UNREALIZABLE
    }

    private Result result;
    private Map<String, Expression> solutions;

    public Output(Result result, Map<String, Expression> solutions) {
        this.result = result;
        this.solutions = solutions;
    }

    public Result getResult() { return result; }
    public Map<String, Expression> getSolutions() { return solutions; }

    public boolean isRealizable() { return result == Result.REALIZABLE; }
    public boolean isUnknown() { return result == Result.UNKNOWN; }
    public boolean isUnrealizable() { return result == Result.UNREALIZABLE; }
}
