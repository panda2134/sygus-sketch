package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.Output;
import sketch.api.sygus.lang.SygusProblem;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.seq.SequentialSketchMain;

public class ProblemSolver extends SequentialSketchMain {
    private SygusProblem sygusProblem;

    public ProblemSolver(SygusProblem sygusProblem) {
        super(new SolverOptions());
        this.sygusProblem = sygusProblem;
    }

    private Program buildSketchProgram() {
        Program prog = Program.emptyProgram();

        return prog;
    }

    public Output solve() {
        return null;
    }
}
