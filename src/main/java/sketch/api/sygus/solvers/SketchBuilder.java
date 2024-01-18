package sketch.api.sygus.solvers;

import sketch.api.sygus.lang.SygusNodeVisitor;
import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.lang.SynthFunction;
import sketch.api.sygus.lang.type.TypePrimitive;
import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;
import sketch.api.sygus.util.exception.SketchConversionException;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SketchBuilder implements SygusNodeVisitor {

    private Program prog;
    private List<Variable> sygusVars;

    public SketchBuilder() {
        prog = Program.emptyProgram();
    }

    public Program program() {
        return prog;
    }

    @Override
    public Program visitSygusProblem(SygusProblem problem) {
        String pkgName = "SYGUS";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StructDef> structs = new ArrayList<StructDef>();
        List<Function> funcs = new ArrayList<Function>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        sygusVars = problem.getVariables();

        Package pkg = new Package(prog, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        prog = prog.creator().streams(namespaces).create();

        return prog;
    }

    @Override
    public Function visitSynthFunction(SynthFunction func) {
        Function.FunctionCreator fc = Function.creator(prog, func.getFunctionID(), Function.FcnType.Static);

        // TODO Fill implementation
        Type returnType = (Type) func.getReturnType().accept(this);
        fc.returnType(returnType);

        return fc.create();
    }

    @Override
    public sketch.compiler.ast.core.typs.TypePrimitive visitTypePrimitive(TypePrimitive ty) {
        switch(ty.getPredefinedType()) {
            case TYPE_INT:
                return sketch.compiler.ast.core.typs.TypePrimitive.inttype;
            case TYPE_BOOLEAN:
                return sketch.compiler.ast.core.typs.TypePrimitive.bittype;
            default:
                throw new SketchConversionException("Unknown predefined type");
        }
    }

    @Override
    public ExprConstInt visitConstBool(ConstBool b) {
        return b.getValue() ? ExprConstInt.one : ExprConstInt.zero;
    }

    @Override
    public ExprConstInt visitConstInt(ConstInt n) {
        return ExprConstInt.createConstant(n.getValue());
    }

    @Override
    public ExprUnary visitExprUnaryOp(ExprUnaryOp e) {
        Expression subExpr = (Expression) e.getExpr().accept(this);

        switch(e.getOp()) {
            case UNOP_NOT:
                return new ExprUnary(prog, ExprUnary.UNOP_NOT, subExpr);
            case UNOP_BNOT:
                return new ExprUnary(prog, ExprUnary.UNOP_BNOT, subExpr);
            case UNOP_NEG:
                return new ExprUnary(prog, ExprUnary.UNOP_NEG, subExpr);
            default:
                throw new SketchConversionException("Unknown unary operator");
        }
    }

    @Override
    public ExprBinary visitExprBinaryOp(ExprBinaryOp e) {
        Expression left = (Expression) e.getLeft().accept(this);
        Expression right = (Expression) e.getRight().accept(this);

        switch(e.getOp()) {
            // Arithmetic
            case BINOP_ADD:
                return new ExprBinary(prog, ExprBinary.BINOP_ADD, left, right);
            case BINOP_SUB:
                return new ExprBinary(prog, ExprBinary.BINOP_SUB, left, right);
            case BINOP_MUL:
                return new ExprBinary(prog, ExprBinary.BINOP_MUL, left, right);
            case BINOP_DIV:
                return new ExprBinary(prog, ExprBinary.BINOP_DIV, left, right);
            case BINOP_MOD:
                return new ExprBinary(prog, ExprBinary.BINOP_MOD, left, right);
            // Boolean
            case BINOP_AND:
                return new ExprBinary(prog, ExprBinary.BINOP_AND, left, right);
            case BINOP_OR:
                return new ExprBinary(prog, ExprBinary.BINOP_OR, left, right);
            // Comparison
            case BINOP_EQ:
                return new ExprBinary(prog, ExprBinary.BINOP_EQ, left, right);
            case BINOP_NEQ:
                return new ExprBinary(prog, ExprBinary.BINOP_NEQ, left, right);
            case BINOP_LT:
                return new ExprBinary(prog, ExprBinary.BINOP_LT, left, right);
            case BINOP_LE:
                return new ExprBinary(prog, ExprBinary.BINOP_LE, left, right);
            case BINOP_GT:
                return new ExprBinary(prog, ExprBinary.BINOP_GT, left, right);
            case BINOP_GE:
                return new ExprBinary(prog, ExprBinary.BINOP_GE, left, right);
            default:
                throw new SketchConversionException("Unknown binary operator");
        }
    }

    @Override
    public ExprFunCall visitFunctionCall(ExprFunctionCall f) {
        List<Expression> params = f.getArgs().stream()
                .map(e -> (Expression) e.accept(this))
                .collect(Collectors.toList());

        return new ExprFunCall(prog, f.getFunctionID(), params);
    }

    @Override
    public Object visitVariable(Variable v) {
        return new ExprVar(prog, v.getID());
    }

    /**
     * Returns a list of generator function,
     * where the first function is the starting symbol
     */
    @Override
    public List<Function> visitGrammar(Grammar g) {
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
    public Object visitRHSNonterminal(RHSNonterminal n) {
        return null;
    }

    @Override
    public Object visitRHSConstBool(RHSConstBool b) {
        return b.getValue() ? ExprConstInt.one : ExprConstInt.zero;
    }

    @Override
    public Object visitRHSConstInt(RHSConstInt n) { return ExprConstInt.createConstant(n.getValue()); }

    @Override
    public Object visitRHSFunctionCall(RHSFunctionCall f) {
        List<Expression> params = f.getArgs().stream()
                .map(e -> (Expression) e.accept(this))
                .collect(Collectors.toList());

        return new ExprFunCall(prog, f.getFunctionID(), params);
    }

    @Override
    public Object visitRHSUnaryOp(RHSUnaryOp e) {
        Expression subExpr = (Expression) e.getExpr().accept(this);

        switch(e.getOp()) {
            case UNOP_NOT:
                return new ExprUnary(prog, ExprUnary.UNOP_NOT, subExpr);
            case UNOP_BNOT:
                return new ExprUnary(prog, ExprUnary.UNOP_BNOT, subExpr);
            case UNOP_NEG:
                return new ExprUnary(prog, ExprUnary.UNOP_NEG, subExpr);
            default:
                throw new SketchConversionException("Unknown unary operator");
        }
    }

    @Override
    public Object visitRHSBinaryOp(RHSBinaryOp e) {
            Expression left = (Expression) e.getLeft().accept(this);
            Expression right = (Expression) e.getRight().accept(this);

            switch(e.getOp()) {
                // Arithmetic
                case BINOP_ADD:
                    return new ExprBinary(prog, ExprBinary.BINOP_ADD, left, right);
                case BINOP_SUB:
                    return new ExprBinary(prog, ExprBinary.BINOP_SUB, left, right);
                case BINOP_MUL:
                    return new ExprBinary(prog, ExprBinary.BINOP_MUL, left, right);
                case BINOP_DIV:
                    return new ExprBinary(prog, ExprBinary.BINOP_DIV, left, right);
                case BINOP_MOD:
                    return new ExprBinary(prog, ExprBinary.BINOP_MOD, left, right);
                // Boolean
                case BINOP_AND:
                    return new ExprBinary(prog, ExprBinary.BINOP_AND, left, right);
                case BINOP_OR:
                    return new ExprBinary(prog, ExprBinary.BINOP_OR, left, right);
                // Comparison
                case BINOP_EQ:
                    return new ExprBinary(prog, ExprBinary.BINOP_EQ, left, right);
                case BINOP_NEQ:
                    return new ExprBinary(prog, ExprBinary.BINOP_NEQ, left, right);
                case BINOP_LT:
                    return new ExprBinary(prog, ExprBinary.BINOP_LT, left, right);
                case BINOP_LE:
                    return new ExprBinary(prog, ExprBinary.BINOP_LE, left, right);
                case BINOP_GT:
                    return new ExprBinary(prog, ExprBinary.BINOP_GT, left, right);
                case BINOP_GE:
                    return new ExprBinary(prog, ExprBinary.BINOP_GE, left, right);
                default:
                    throw new SketchConversionException("Unknown binary operator");
            }
    }
}
