package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.Output;
import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.lang.expr.SygusExpression;
import sketch.api.sygus.util.exception.SketchConversionException;
import sketch.api.sygus.util.exception.SolverException;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.stencilSK.EliminateStarStatic;
import sketch.util.exceptions.SketchNotResolvedException;

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
    public void setControlBits(int numBits) { super.options.bndOpts.cbits = numBits; }
    public void setQuantifiedBits(int numBits) { super.options.bndOpts.inbits = numBits; }

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
            throw new SketchConversionException("Failed to translate SyGuS problem to Sketch: " + re.getMessage());
        }

        prog = this.preprocAndSemanticCheck(prog);

        Output output;
        try {
            SynthesisResult synthResult = this.partialEvalAndSolve(prog);
            output = getOutput(synthResult);
        } catch (SketchNotResolvedException e) {
            output = new Output(Output.Result.UNREALIZABLE, null);
        }

        this.log(1, "[SyGuS-Sketch] DONE");
        return output;
    }

    private Output getOutput(SynthesisResult synthResult) {
        Program finalCleaned = synthResult.lowered.highLevelC;
        Output output;
        if (synthResult.solution != null) {
            SynthResultExtractor extractor = new SynthResultExtractor(
                    this.sygusProblem.getTargetFunctions(), synthResult.solution, varDeclHints);
            finalCleaned.accept(extractor);
            Map<String, SygusExpression> solutions = extractor.getSynthResult();

            output = new Output(Output.Result.REALIZABLE, solutions);
        } else {
            output = new Output(Output.Result.UNREALIZABLE, null);
        }

        return output;
    }
}
