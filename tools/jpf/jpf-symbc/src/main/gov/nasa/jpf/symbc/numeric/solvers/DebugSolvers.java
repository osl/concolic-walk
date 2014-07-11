package gov.nasa.jpf.symbc.numeric.solvers;

import gov.nasa.jpf.symbc.numeric.PathCondition;

public class DebugSolvers extends ProblemGeneral {

	private ProblemGeneral[] probs;
	private int numSolvers;
	private boolean alwaysPrint;
	private PathCondition p;

	public DebugSolvers(PathCondition pc) {
		p = pc;

		alwaysPrint = false;
		numSolvers = 3;
		probs = new ProblemGeneral[numSolvers];

		probs[0] = new ProblemChoco();
		probs[1] = new ProblemChoco2();
		probs[2] = new ProblemCoral();
	}

	public class SolverObjects {
		private Object[] cons;

		public SolverObjects() {
			cons = new Object[numSolvers];
		}

		public Object getConstraint(int i) {
			return cons[i];
		}

		public void setConstraint(int i, Object o) {
			cons[i] = o;
		}
	}

	@Override
	public Object and(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].and(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object and(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].and(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object and(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].and(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object div(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].div(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object div(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].div(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object div(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].div(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object div(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].and(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object div(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].div(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object eq(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].eq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object eq(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].eq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object eq(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].eq(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object eq(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].eq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object eq(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].eq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object geq(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].geq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object geq(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].geq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object geq(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].geq(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object geq(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].geq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object geq(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].geq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public int getIntValue(Object dpVar) {
		return probs[0].getIntValue(((SolverObjects) dpVar).getConstraint(0));
	}

	@Override
	public double getRealValue(Object dpVar) {
		return probs[0].getRealValue(((SolverObjects) dpVar).getConstraint(0));
	}

	@Override
	public double getRealValueInf(Object dpvar) {
		return probs[0].getRealValueInf(((SolverObjects) dpvar).getConstraint(0));
	}

	@Override
	public double getRealValueSup(Object dpVar) {
		return probs[0].getRealValueSup(((SolverObjects) dpVar).getConstraint(0));
	}

	@Override
	public Object gt(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].gt(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object gt(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].gt(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object gt(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].gt(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object gt(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].gt(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object gt(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].gt(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object leq(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].leq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object leq(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].leq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	public Object leq(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].leq(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object leq(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].leq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object leq(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].leq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object lt(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].lt(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object lt(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].lt(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object lt(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].lt(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object lt(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].lt(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object lt(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].lt(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object makeIntVar(String name, int min, int max) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].makeIntVar(name, min, max));
		}
		return so;
	}

	@Override
	public Object makeRealVar(String name, double min, double max) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].makeRealVar(name, min, max));
		}
		return so;
	}

	@Override
	public Object minus(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].minus(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object minus(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].minus(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object minus(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].minus(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object minus(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].minus(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object minus(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].minus(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object mixed(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].mixed(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object mult(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].mult(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object mult(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].mult(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object mult(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].mult(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object mult(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].mult(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object mult(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].mult(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object neq(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].neq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object neq(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].neq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object neq(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].neq(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object neq(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].neq(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object neq(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].neq(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object or(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].or(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object or(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].or(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object or(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].or(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object plus(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].plus(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object plus(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].plus(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object plus(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].plus(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object plus(double value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].plus(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object plus(Object exp, double value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].plus(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public void post(Object constraint) {
		for (int i = 0; i < numSolvers; i++) {
			probs[i].post(((SolverObjects) constraint).getConstraint(i));
		}
	}

	@Override
	public Object shiftL(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftL(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object shiftL(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftL(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object shiftL(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].shiftL(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object shiftR(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftR(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object shiftR(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftR(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object shiftR(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].shiftR(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object shiftUR(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftUR(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object shiftUR(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].shiftUR(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object shiftUR(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so
					.setConstraint(i, probs[i].shiftUR(((SolverObjects) exp1)
							.getConstraint(i), ((SolverObjects) exp2)
							.getConstraint(i)));
		}
		return so;
	}

	@Override
	public
	Boolean solve() {
		boolean[] solved = new boolean[numSolvers];
		for (int i = 0; i < numSolvers; i++) {
			Boolean s = probs[i].solve();
			if (s == null) {
				solved[i] = false;
			} else {
				solved[i] = s;
			}

			if (alwaysPrint) {
				System.out.println("Solver " + Integer.toString(i) + ": " + Boolean.toString(solved[i]));
			}
		}

		if (!alwaysPrint) {
			boolean first = solved[0];
			boolean print = false;
			for (int j = 1; j < numSolvers; j++) {
				if (solved[j] != first) {
					print = true;
					break;
				}
			}
			if (print) {
				System.out.println("---- SOLVERS DISAGREE! ------");
				System.out.println(p.toString());
				for (int i = 0; i < numSolvers; i++) {
					System.out.println("   Solver " + Integer.toString(i) + ": "
							+ Boolean.toString(solved[i]));
				}
			}
		}

		return solved[0];
	}

	@Override
	public Object xor(int value, Object exp) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].xor(value, ((SolverObjects) exp)
					.getConstraint(i)));
		}
		return so;
	}

	@Override
	public Object xor(Object exp, int value) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].xor(((SolverObjects) exp)
					.getConstraint(i), value));
		}
		return so;
	}

	@Override
	public Object xor(Object exp1, Object exp2) {
		SolverObjects so = new SolverObjects();
		for (int i = 0; i < numSolvers; i++) {
			so.setConstraint(i, probs[i].xor(((SolverObjects) exp1).getConstraint(i), ((SolverObjects) exp2).getConstraint(i)));
		}
		return so;
	}

	@Override
	public void postLogicalOR(Object[] constraint) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error LogicalOR not implemented");
	}

}
