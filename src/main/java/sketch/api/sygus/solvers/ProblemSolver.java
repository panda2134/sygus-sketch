package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.Output;
import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.lang.expr.SygusExpression;
import sketch.api.sygus.util.exception.SketchConversionException;
import sketch.api.sygus.util.exception.SolverException;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.stencilSK.EliminateStarStatic;
import sketch.util.Pair;

import java.util.List;
import java.util.Map;

public class ProblemSolver extends SequentialSketchMain {
    private SygusProblem sygusProblem;
    private VarDeclHints varDeclHints;

    public ProblemSolver(SygusProblem sygusProblem) {
        super(new SolverOptions());
        this.sygusProblem = sygusProblem;
        PlatformLocalization.getLocalization().setTempDirs();
    }

    public void setInlineAmnt(int bnd) {
        super.options.bndOpts.inlineAmnt = bnd;
    }

    private Program buildSketchProgram() {
        SketchBuilder builder = new SketchBuilder();
        sygusProblem.accept(builder);
        varDeclHints = builder.varDeclHints();
        return builder.program();
    }

    public Output solve() {
        this.log(1, "Benchmark = SyGuS");
        Program prog = null;

        try {
            prog = buildSketchProgram();
            // prog.debugDump();
        } catch (SolverException se) {
            se.printStackTrace();
            throw se;
        } catch (IllegalArgumentException ia) {
            throw ia;
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new SketchConversionException("Failed to translate SyGuS problem to Sketch: " + re.getMessage());
        }

        prog = this.preprocAndSemanticCheck(prog);
        // prog.debugDump();

        SynthesisResult synthResult = this.partialEvalAndSolve(prog);
        prog = synthResult.lowered.result;
        // prog.debugDump();

        Program finalCleaned = synthResult.lowered.highLevelC;
        // finalCleaned.debugDump();
        
        Program substituted;
        if (synthResult.solution != null) {
            EliminateStarStatic eliminate_star = new EliminateStarStatic(synthResult.solution);
            substituted = (Program) finalCleaned.accept(eliminate_star);
        } else {
            substituted = finalCleaned;
            return new Output(Output.Result.UNREALIZABLE ,null);
        }

//        substituted.debugDump();
        SynthResultExtractor extractor = new SynthResultExtractor(
                this.sygusProblem.getTargetFunctions(), synthResult.solution, varDeclHints);
        finalCleaned.accept(extractor);
        Map<String, SygusExpression> solutions = extractor.getSynthResult();

        this.log(1, "[SyGuS-Sketch] DONE");

        return new Output(Output.Result.REALIZABLE, solutions);
    }
}
