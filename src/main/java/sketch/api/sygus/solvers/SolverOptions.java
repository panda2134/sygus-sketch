package sketch.api.sygus.solvers;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.util.cli.SketchCliParser;

import java.io.File;
import java.util.Arrays;

public class SolverOptions extends SketchOptions {
    public SolverOptions() {
        super(new String[0]);
    }

    // Override parseCommandLine so that it does not require input file
    // Current implementation only allows default options
    @Override
    public void parseCommandline(SketchCliParser parser) {
        this.currentArgs = parser.inArgs;
        this.bndOpts.parse(parser);
        this.debugOpts.parse(parser);
        this.feOpts.parse(parser);
        this.spmdOpts.parse(parser);
        this.semOpts.parse(parser);

        args = solverOpts.parse(parser).get_args();
        this.backendArgs = parser.backendArgs;
        this.nativeArgs = parser.nativeArgs;

        // actions
        argsAsList = Arrays.asList(args);
        sketchFile = null;
        sketchName = "sygusSketchTemp";
        feOpts.outputCode |= feOpts.outputTest;
    }
}
