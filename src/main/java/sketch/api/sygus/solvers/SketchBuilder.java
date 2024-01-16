package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.lang.SynthFunction;
import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.*;

import java.util.ArrayList;
import java.util.List;

public class SketchBuilder implements SygusNodeVisitor {

    private Program prog;

    public SketchBuilder() {
        prog = Program.emptyProgram();
    }

    public Program program() {
        return prog;
    }

    @Override
    public Program visitSygusProblem(SygusProblem problem) {
        String pkgName = "SYGUS";
        FEContext pkgContext = null;
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StructDef> structs = new ArrayList<StructDef>();
        List<Function> funcs = new ArrayList<Function>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Package pkg = new Package(pkgContext, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        prog = prog.creator().streams(namespaces).create();

        return prog;
    }

    @Override
    public Object visitSynthFunction(SynthFunction func) {
        return null;
    }

    @Override
    public Object visitConstBool(ConstBool b) {
        return null;
    }

    @Override
    public Object visitConstInt(ConstInt n) {
        return null;
    }

    @Override
    public Object visitExprUnaryOp(ExprUnaryOp e) {
        return null;
    }

    @Override
    public Object visitExprBinaryOp(ExprBinaryOp e) {
        return null;
    }

    @Override
    public Object visitFunctionCall(ExprFunctionCall f) {
        return null;
    }

    @Override
    public Object visitVariable(Variable v) {
        return null;
    }

    @Override
    public Object visitGrammar(Grammar g) {
        return null;
    }

    @Override
    public Object visitNonterminal(Nonterminal n) {
        return null;
    }

    @Override
    public Object visitProduction(Production prod) {
        return null;
    }

    @Override
    public Object visitRHSConstBool(RHSConstBool b) {
        return null;
    }

    @Override
    public Object visitRHSConstInt(RHSConstInt n) {
        return null;
    }

    @Override
    public Object visitRHSFunctionCall(RHSFunctionCall f) {
        return null;
    }

    @Override
    public Object visitRHSUnaryOp(RHSUnaryOp e) {
        return null;
    }

    @Override
    public Object visitRHSBinaryOp(RHSBinaryOp e) {
        return null;
    }
}
