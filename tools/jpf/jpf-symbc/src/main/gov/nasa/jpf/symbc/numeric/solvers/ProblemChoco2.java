package gov.nasa.jpf.symbc.numeric.solvers;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;

/**
 * Integration of the Choco CP library version 2 (2.1.1, specifically).
 * Currently only supports integer operations.
 *
 * @author staats
 *
 */

public class ProblemChoco2 extends ProblemGeneral {
	private CPSolver solver;
	private Model model;
	public static int timeBound = 300;

	public ProblemChoco2() {
		model = new CPModel();
		solver = new CPSolver();
	}

	public Object and(int value, Object exp) {	throw new RuntimeException("## Unsupported and "); }
	public Object and(Object exp, int value) {	throw new RuntimeException("## Unsupported and "); }
	public Object and(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported and "); }

	public Object div(int value, Object exp) { return Choco.div(value, (IntegerExpressionVariable) exp); }
	public Object div(Object exp, int value) { return Choco.div((IntegerExpressionVariable) exp, value); }
	public Object div(Object exp1, Object exp2) { return Choco.div((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object div(double value, Object exp) {	throw new RuntimeException("## Unsupported double div "); }
	public Object div(Object exp, double value) {	throw new RuntimeException("## Unsupported double div "); }

	public Object eq(int value, Object exp) { return Choco.eq(value, (IntegerExpressionVariable) exp);	}
	public Object eq(Object exp, int value) { return Choco.eq((IntegerExpressionVariable) exp, value);	}
	public Object eq(Object exp1, Object exp2) { return Choco.eq((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp1);	}

	public Object eq(double value, Object exp) {	throw new RuntimeException("## Unsupported eq "); }
	public Object eq(Object exp, double value) {	throw new RuntimeException("## Unsupported eq "); }

	public Object geq(int value, Object exp) { return Choco.geq(value, (IntegerExpressionVariable) exp);	}
	public Object geq(Object exp, int value) { return Choco.geq((IntegerExpressionVariable) exp, value);	}
	public Object geq(Object exp1, Object exp2) { return Choco.geq((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp1);	}

	public Object geq(double value, Object exp) {	throw new RuntimeException("## Unsupported geq "); }
	public Object geq(Object exp, double value) {	throw new RuntimeException("## Unsupported geq "); }

	public int getIntValue(Object dpVar) {
		try {
			return solver.getVar((IntegerVariable) dpVar).getVal();
		} catch (Throwable t) {
			return ((IntegerVariable) dpVar).getLowB();
		}
	}

	public double getRealValue(Object dpVar) {	throw new RuntimeException("## Unsupported get real value "); }
	public double getRealValueInf(Object dpvar) {	throw new RuntimeException("## Unsupported get real value "); }
	public double getRealValueSup(Object dpVar) {	throw new RuntimeException("## Unsupported get real value "); }

	public Object gt(int value, Object exp) { return Choco.gt(value, (IntegerExpressionVariable) exp); }
	public Object gt(Object exp, int value) { return Choco.gt((IntegerExpressionVariable) exp, value); }
	public Object gt(Object exp1, Object exp2) { return Choco.gt((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object gt(double value, Object exp) {	throw new RuntimeException("## Unsupported double gt "); }
	public Object gt(Object exp, double value) {	throw new RuntimeException("## Unsupported double gt "); }

	public Object leq(int value, Object exp) { return Choco.leq(value, (IntegerExpressionVariable) exp); }
	public Object leq(Object exp, int value) { return Choco.leq((IntegerExpressionVariable) exp, value); }
	public Object leq(Object exp1, Object exp2) { return Choco.leq((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object leq(double value, Object exp) {	throw new RuntimeException("## Unsupported double leq "); }
	public Object leq(Object exp, double value) {	throw new RuntimeException("## Unsupported double leq "); }

	public Object lt(int value, Object exp) { return Choco.lt(value, (IntegerExpressionVariable) exp); }
	public Object lt(Object exp, int value) { return Choco.lt((IntegerExpressionVariable) exp, value); }
	public Object lt(Object exp1, Object exp2) { return Choco.lt((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object lt(double value, Object exp) {	throw new RuntimeException("## Unsupported double lt "); }
	public Object lt(Object exp, double value) {	throw new RuntimeException("## Unsupported double lt "); }

	public Object makeIntVar(String name, int min, int max) {
		return Choco.makeIntVar(name, min, max);
	}

	public Object makeRealVar(String name, double min, double max) {	throw new RuntimeException("## Unsupported make real "); }

	public Object minus(int value, Object exp) { return Choco.minus(value, (IntegerExpressionVariable) exp); }
	public Object minus(Object exp, int value) { return Choco.minus((IntegerExpressionVariable) exp, value); }
	public Object minus(Object exp1, Object exp2)  { return Choco.minus((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object minus(double value, Object exp) {	throw new RuntimeException("## Unsupported double minus "); }
	public Object minus(Object exp, double value) {	throw new RuntimeException("## Unsupported double minus "); }
	public Object mixed(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported mixed "); }

	public Object mult(int value, Object exp) { return Choco.mult(value, (IntegerExpressionVariable) exp); }
	public Object mult(Object exp, int value) { return Choco.mult((IntegerExpressionVariable) exp, value); }
	public Object mult(Object exp1, Object exp2)  { return Choco.mult((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object mult(double value, Object exp) {	throw new RuntimeException("## Unsupported double mult "); }
	public Object mult(Object exp, double value) {	throw new RuntimeException("## Unsupported double mult "); }

	public Object neq(int value, Object exp) { return Choco.neq(value, (IntegerExpressionVariable) exp); }
	public Object neq(Object exp, int value) { return Choco.neq((IntegerExpressionVariable) exp, value); }
	public Object neq(Object exp1, Object exp2)  { return Choco.neq((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object neq(double value, Object exp) {	throw new RuntimeException("## Unsupported double NEQ "); }
	public Object neq(Object exp, double value) {	throw new RuntimeException("## Unsupported double NEQ "); }

	public Object or(int value, Object exp) {	throw new RuntimeException("## Unsupported OR "); }
	public Object or(Object exp, int value) {	throw new RuntimeException("## Unsupported OR "); }
	public Object or(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported OR "); }

	public Object plus(int value, Object exp) { return Choco.plus(value, (IntegerExpressionVariable) exp); }
	public Object plus(Object exp, int value) { return Choco.plus((IntegerExpressionVariable) exp, value); }
	public Object plus(Object exp1, Object exp2)  { return Choco.plus((IntegerExpressionVariable) exp1, (IntegerExpressionVariable) exp2); }

	public Object plus(double value, Object exp) {	throw new RuntimeException("## Unsupported double plus "); }
	public Object plus(Object exp, double value) {	throw new RuntimeException("## Unsupported double plus "); }

	public void post(Object constraint) {
		model.addConstraint((Constraint) constraint);
	}

	public Object shiftL(int value, Object exp) {	throw new RuntimeException("## Unsupported shiftL"); }
	public Object shiftL(Object exp, int value) {	throw new RuntimeException("## Unsupported shiftL"); }
	public Object shiftL(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported shiftL"); }

	public Object shiftR(int value, Object exp) {	throw new RuntimeException("## Unsupported shiftR"); }
	public Object shiftR(Object exp, int value) {	throw new RuntimeException("## Unsupported shiftR"); }
	public Object shiftR(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported shiftR"); }

	public Object shiftUR(int value, Object exp) {	throw new RuntimeException("## Unsupported shiftUR"); }
	public Object shiftUR(Object exp, int value) {	throw new RuntimeException("## Unsupported shiftUR"); }
	public Object shiftUR(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported shiftUR"); }

	/*
	 * One potential issue is determining the best way to build constraints.
	 * Currently the model is reset after solving, and the solver
	 * is reset right before solving. Is this the best way to do this?
	 * We could alternatively pop constraints when backtracking,
	 * though this would require some changes to how ProblemGeneral
	 * is interfaced.
	 *
	 */
	public Boolean solve() {
		solver.read(model);

		System.out.println("Model:" + model.constraintsToString());

		solver.setTimeLimit(timeBound);
		Boolean solved = solver.solve();
		boolean feasible = solver.isFeasible();

		System.out.println("Solved: " + solved);
		System.out.println("Feasible: " + feasible);

		return solved;
	}

	public Object xor(int value, Object exp) { throw new RuntimeException("## Unsupported XOR "); }
	public Object xor(Object exp, int value) { throw new RuntimeException("## Unsupported XOR"); }
	public Object xor(Object exp1, Object exp2) {	throw new RuntimeException("## Unsupported XOR"); }

	@Override
	public void postLogicalOR(Object[] constraint) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Choco2 does not support LogicalOR");
	}

}
