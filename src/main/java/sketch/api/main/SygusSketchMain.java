package sketch.api.main;

import sketch.api.sygus.lang.SygusProblem;
import sketch.api.sygus.lang.SynthFunction;
import sketch.api.sygus.lang.expr.*;
import sketch.api.sygus.lang.grammar.*;
import sketch.api.sygus.lang.type.TypePrimitive;
import sketch.api.sygus.solvers.SketchBuilder;
import sketch.compiler.ast.core.Program;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SygusSketchMain {
    public static void main(String[] args) {
        SygusProblem prob = SygusProblem.emptyProblem();

        List<Variable> vars = new ArrayList<Variable>();
        Variable x = new Variable("x", new TypePrimitive("int"));
        Variable y = new Variable("y", new TypePrimitive("int"));
        vars.add(x);
        vars.add(y);

        Nonterminal nonE = new Nonterminal("E", new TypePrimitive("int"));
        Nonterminal nonB = new Nonterminal("B", new TypePrimitive("boolean"));
        RHSNonterminal rhsNonE = new RHSNonterminal(nonE);
        RHSNonterminal rhsNonB = new RHSNonterminal(nonB);

        List<RHSTerm> rhsE = new ArrayList<RHSTerm>();
        rhsE.add(new RHSVariable("x"));
        rhsE.add(new RHSVariable("y"));
        rhsE.add(new RHSIfThenElse(rhsNonB, rhsNonE, rhsNonE));

        List<RHSTerm> rhsB = new ArrayList<RHSTerm>();
        rhsE.add(new RHSConstBool(true));
        rhsE.add(new RHSConstBool(false));
        rhsE.add(new RHSBinaryOp(RHSBinaryOp.BinaryOp.BINOP_LT, rhsNonE, rhsNonE));

        Production prodE = new Production(nonE, rhsE);
        Production prodB = new Production(nonB, rhsB);

        Grammar g = Grammar.empty();
        g.addRule(prodE);
        g.addRule(prodB);

        SynthFunction f = new SynthFunction("f", vars, new TypePrimitive("int"), g);
        prob.addTargetFunction(f);

        SygusExpression constraint1 = new ExprBinaryOp(
                ExprBinaryOp.BinaryOp.BINOP_EQ,
                new ExprFunctionCall("f",
                        Arrays.asList(new ConstInt(3), new ConstInt(5))
                ),
                new ConstInt(5)
        );

        SygusExpression constraint2 = new ExprBinaryOp(
                ExprBinaryOp.BinaryOp.BINOP_EQ,
                new ExprFunctionCall("f",
                        Arrays.asList(new ConstInt(4), new ConstInt(1))
                ),
                new ConstInt(4)
        );

        prob.addConstraint(constraint1);
        prob.addConstraint(constraint2);

        SketchBuilder builder = new SketchBuilder();
        prob.accept(builder);

        Program prog = builder.program();
        System.out.println(prog.toString());

        prog.debugDump();
    }
}