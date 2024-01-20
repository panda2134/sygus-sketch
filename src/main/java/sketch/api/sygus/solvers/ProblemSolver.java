package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.Output;
import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.util.exception.SketchConversionException;
import sketch.api.sygus.util.exception.SolverException;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;

public class ProblemSolver extends SequentialSketchMain {
    private SygusProblem sygusProblem;

    public ProblemSolver(SygusProblem sygusProblem) {
        super(new SolverOptions());
        this.sygusProblem = sygusProblem;
        PlatformLocalization.getLocalization().setTempDirs();
    }

    private Program buildSketchProgram() {
        SketchBuilder builder = new SketchBuilder();
        sygusProblem.accept(builder);
        return builder.program();
    }

    public Output solve() {
        this.log(1, "Benchmark = " + this.benchmarkName());
        Program prog = null;

        try {
            prog = buildSketchProgram();
            prog.debugDump();
        } catch (SolverException se) {
            throw se;
        } catch (IllegalArgumentException ia) {
            throw ia;
        } catch (RuntimeException re) {
            throw new SketchConversionException("Failed to translate SyGuS problem to Sketch: " + re.getMessage());
        }
        // Program withoutConstsReplaced = this.preprocAndSemanticCheck(prog, false);

        prog = this.preprocAndSemanticCheck(prog);

        SynthesisResult synthResult = this.partialEvalAndSolve(prog);
        prog = synthResult.lowered.result;

        Program finalCleaned = synthResult.lowered.highLevelC;

        Program substituted;
        if (synthResult.solution != null) {
            substituted =
                    (new SubstituteSolution(varGen, options,
                            synthResult.solution)).visitProgram(finalCleaned);
        } else {
            substituted = finalCleaned;
        }

        Program substitutedCleaned =
                (new CleanupFinalCode(varGen, options,
                        visibleRControl(finalCleaned))).visitProgram(substituted);

        generateCode(substitutedCleaned);
        this.log(1, "[SKETCH] DONE");

        return null;
    }
}
