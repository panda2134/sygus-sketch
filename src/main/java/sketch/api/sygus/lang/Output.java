package sketch.api.sygus.lang;

import sketch.api.sygus.lang.expr.SygusExpression;

import java.util.Map;

public class Output {
    private Result result;
    private Map<String, SygusExpression> solutions;
    public Output(Result result, Map<String, SygusExpression> solutions) {
        this.result = result;
        this.solutions = solutions;
    }

    public Result getResult() {
        return result;
    }

    public Map<String, SygusExpression> getSolutions() {
        return solutions;
    }

    public boolean isRealizable() {
        return result == Result.REALIZABLE;
    }

    public boolean isUnknown() {
        return result == Result.UNKNOWN;
    }

    public boolean isUnrealizable() {
        return result == Result.UNREALIZABLE;
    }

    public enum Result {
        REALIZABLE,
        UNKNOWN,
        UNREALIZABLE
    }
}
