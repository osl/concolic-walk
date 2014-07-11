//
//Copyright (C) 2005 United States Government as represented by the
//Administrator of the National Aeronautics and Space Administration
//(NASA).  All Rights Reserved.
//
//This software is distributed under the NASA Open Source Agreement
//(NOSA), version 1.3.  The NOSA has been approved by the Open Source
//Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
//directory tree for the complete NOSA document.
//
//THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
//KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
//LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
//SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
//A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
//THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
//DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

package gov.nasa.jpf.symbc.numeric.solvers;

import symlib.SymBool;
import symlib.SymDouble;
import symlib.SymInt;
import symlib.SymLiteral;
import symlib.SymNumber;
import symlib.Util;
import coral.PC;
import coral.solvers.Env;
import coral.solvers.Result;
import coral.solvers.Solver;
import coral.solvers.SolverKind;
import coral.util.Config;

/**
 * Interface of SPF with the randomized solvers from CORAL
 * (http://pan.cin.ufpe.br/coral).
 *
 * Four kinds of methods in this implementation:
 *
 * (1) factory methods: create objects from the symbolic library of
 * CORAL to correspond to the symbolic expressions from JPF's symbolic
 * execution
 *
 * (2) post(): every time a constraint is created (i.e., a boolean
 * expression in the context of a branching decision) this method will
 * be called.
 *
 * (3) solve(): this is the actual call to the solver.
 *
 * (4) get*Value(): this method retrieves the solutions associated
 * with each variables if they exist.
 *
 * For examples of use, look at src/tests/gov.nasa.jpf.symbc/ExSymExeCoral.jpf
 *
 * @author Matheus Arrais (mbas@cin.ufpe.br)
 * @author Mateus Borges (mab@cin.ufpe.br)
 * @author Marcelo d'Amorim (damorim@cin.ufpe.br)
 *
 */

public class ProblemCoral extends ProblemGeneral {

//	private static final long timeout = -1; //Config.timeout; // 1s default
	private static SolverKind defaultKind;
	private SolverKind solverKind;
	private coral.PC pc = new coral.PC();
	private boolean optmize;

	public ProblemCoral() {
		this(defaultKind);
	}

	public ProblemCoral(SolverKind solverKind){
		this.solverKind = solverKind;
	}

	public coral.PC getPc() {
		return pc;
	}
	
	/**
	 * Set CORAL's parameters with the values from the .jpf file. 
	 * Look at ExSymExeCoral.jpf for more information. 
	 */
	
	public static void configure(gov.nasa.jpf.Config conf) {
		long seed = conf.getLong("coral.seed",464655);
		int nIterations = conf.getInt("coral.iterations",-1);
		SolverKind kind = SolverKind.valueOf(conf.getString("coral.solver","PSO_OPT4J").toUpperCase());
		boolean optimize = conf.getBoolean("coral.optimize", true);
		String intervalSolver = conf.getString("coral.interval_solver","none").toLowerCase();
		String intervalSolverPath = conf.getString("coral.interval_solver.path","none");
		
		Config.seed = seed;
		defaultKind = kind;
		if(optimize) {
			Config.toggleValueInference = true;
			Config.removeSimpleEqualities = true;
		}
		
		if(!intervalSolver.equals("none")) {
			Config.intervalSolver = intervalSolver;
			Config.enableIntervalBasedSolver = true;
			if(intervalSolver.equals("realpaver")) {
				Config.realPaverLocation = intervalSolverPath;
			} else if (intervalSolver.equals("icos")) {
				Config.icosLocation = intervalSolverPath;
			} else {
				throw new RuntimeException("Unsupported interval solver!");
			}
			
			Config.simplifyUsingIntervalSolver = optimize ? true : false;
		}
		
		/**
		 * setting maximum number of iterations allowed.
		 * the solver return with no solution in that
		 * case.  note that the constraint may still be
		 * satisfiable.
		 */
		if(nIterations != -1) {
			if(kind.equals(SolverKind.PSO_OPT4J)) {
				Config.nIterationsPSO = nIterations;
			} else if(kind.equals(SolverKind.RANDOM)) {
				Config.nIterationsRANDOM = nIterations;
			} else if(kind.equals(SolverKind.AVM)) {
				Config.nIterationsAVM = nIterations;
			} 
		}
	}
	
	public void cleanup() {
		//reset the id generator
		Util.resetID();
	}

	/**************************************************
	 * ignoring ranges passed from JPF.  We use a short
	 * range as default but that is dynamically reset
	 * based on relational constraints involving
	 * constants.  for example, x > 10 && x < 20
	 * redefines the initial range of x to [10,20].
	 **************************************************/

	@Override
	public Object makeIntVar(String name, int min, int max) {
		return Util.createSymLiteral(0/*default value*/);
	}

	@Override
	public Object makeRealVar(String name, double min, double max) {
		return Util.createSymLiteral(0d/*default value*/);
	}

	@Override
	public Object eq(int value, Object exp) {
		return Util.eq(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object eq(Object exp, int value) {
		return Util.eq((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object eq(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.eq((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.eq((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object eq(double value, Object exp) {
		return Util.eq(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object eq(Object exp, double value) {
		return Util.eq((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object neq(int value, Object exp) {
		return Util.ne(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object neq(Object exp, int value) {
		return Util.ne((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object neq(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.ne((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.ne((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object neq(double value, Object exp) {
		return Util.ne(Util.createConstant(value),(SymDouble)exp);
	}

	@Override
	public Object neq(Object exp, double value) {
		return Util.ne((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object leq(int value, Object exp) {
		return Util.le(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object leq(Object exp, int value) {
		return Util.le((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object leq(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.le((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.le((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object leq(double value, Object exp) {
		return Util.le(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object leq(Object exp, double value) {
		return Util.le((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object geq(int value, Object exp) {
		return Util.ge(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object geq(Object exp, int value) {
		return Util.ge((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object geq(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.ge((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.ge((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object geq(double value, Object exp) {
		return Util.ge(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object geq(Object exp, double value) {
		return Util.ge((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object lt(int value, Object exp) {
		return Util.lt(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object lt(Object exp, int value) {
		return Util.lt((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object lt(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.lt((SymDouble)exp1, (SymDouble)exp2);
		} if (exp1 instanceof SymInt) {
			return Util.lt((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object lt(double value, Object exp) {
		return Util.lt(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object lt(Object exp, double value) {
		return Util.lt((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object gt(int value, Object exp) {
		return Util.gt(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object gt(Object exp, int value) {
		return Util.gt((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object gt(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.gt((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.gt((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object gt(double value, Object exp) {
		return Util.gt(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object gt(Object exp, double value) {
		return Util.gt((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object plus(int value, Object exp) {
		return Util.add(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object plus(Object exp, int value) {
		return Util.add((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object plus(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.add((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.add((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object plus(double value, Object exp) {
		return Util.add(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object plus(Object exp, double value) {
		return Util.add((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object minus(int value, Object exp) {
		return Util.sub(Util.createConstant(value),(SymInt)exp);
	}

	@Override
	public Object minus(Object exp, int value) {
		return Util.sub((SymInt)exp,Util.createConstant(value));
	}

	@Override
	public Object minus(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.sub((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.sub((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object minus(double value, Object exp) {
		return Util.sub(Util.createConstant(value),(SymDouble)exp);
	}

	@Override
	public Object minus(Object exp, double value) {
		return Util.sub((SymDouble)exp,Util.createConstant(value));
	}

	@Override
	public Object mult(int value, Object exp) {
		return Util.mul(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object mult(Object exp, int value) {
		return Util.mul((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object mult(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.mul((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.mul((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object mult(double value, Object exp) {
		return Util.mul(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object mult(Object exp, double value) {
		return Util.mul((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object div(int value, Object exp) {
		return Util.div(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object div(Object exp, int value) {
		return Util.div((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object div(Object exp1, Object exp2) {
		if (exp1 instanceof SymDouble) {
			return Util.div((SymDouble)exp1, (SymDouble)exp2);
		} else if (exp1 instanceof SymInt) {
			return Util.div((SymInt)exp1, (SymInt)exp2);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object div(double value, Object exp) {
		return Util.div(Util.createConstant(value), (SymDouble)exp);
	}

	@Override
	public Object div(Object exp, double value) {
		return Util.div((SymDouble)exp, Util.createConstant(value));
	}

	@Override
	public Object and(int value, Object exp) {
		return Util.and(value==1?Util.TRUE:Util.FALSE, (SymBool)exp);
	}

	@Override
	public Object and(Object exp, int value) {
		return Util.and((SymBool)exp, value==1?Util.TRUE:Util.FALSE);
	}

	@Override
	public Object and(Object exp1, Object exp2) {
		return Util.and((SymBool)exp1, (SymBool)exp2);
	}

	@Override
	public Object or(int value, Object exp) {
		return Util.or(value==1?Util.TRUE:Util.FALSE, (SymBool)exp);
	}

	@Override
	public Object or(Object exp, int value) {
		return Util.or((SymBool)exp, value==1?Util.TRUE:Util.FALSE);
	}

	@Override
	public Object or(Object exp1, Object exp2) {
		return Util.or((SymBool)exp1, (SymBool)exp2);
	}

	@Override
	public Object xor(int value, Object exp) {
		return Util.xor(value==1?Util.TRUE:Util.FALSE, (SymBool)exp);
	}

	@Override
	public Object xor(Object exp, int value) {
		return Util.xor((SymBool)exp, value==1?Util.TRUE:Util.FALSE);
	}

	@Override
	public Object xor(Object exp1, Object exp2) {
		return Util.xor((SymBool)exp1, (SymBool)exp2);
	}

	@Override
	public Object shiftL(int value, Object exp) {
		return Util.sl(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object shiftL(Object exp, int value) {
		return Util.sl((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object shiftL(Object exp1, Object exp2) {
		return Util.sl((SymInt)exp1, (SymInt)exp2);
	}

	@Override
	public Object shiftR(int value, Object exp) {
		return Util.sr(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object shiftR(Object exp, int value) {
		return Util.sr((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object shiftR(Object exp1, Object exp2) {
		return Util.sr((SymInt)exp1, (SymInt)exp2);
	}

	@Override
	public Object shiftUR(int value, Object exp) {
		return Util.usr(Util.createConstant(value), (SymInt)exp);
	}

	@Override
	public Object shiftUR(Object exp, int value) {
		return Util.usr((SymInt)exp, Util.createConstant(value));
	}

	@Override
	public Object shiftUR(Object exp1, Object exp2) {
		return Util.usr((SymInt)exp1, (SymInt)exp2);
	}

	@Override
	public Object mixed(Object exp1, Object exp2) {
        if (exp1 instanceof SymDouble && exp2 instanceof SymInt)
            return Util.eq((SymDouble)exp1, Util.createASDouble((SymInt)exp2));
        else
            throw new RuntimeException("## Error CORAL: unsupported mixed case");
	}

	public Object sin(Object exp) {
		return Util.sin((SymDouble)exp);
	}

	public Object cos(Object exp) {
		return Util.cos((SymDouble)exp);
	}

	public Object round(Object exp) {
		return Util.round((SymDouble)exp);
	}

	public Object exp(Object exp) {
		return Util.exp((SymDouble)exp);
	}

	public Object asin(Object exp) {
		return Util.asin((SymDouble)exp);
	}

	public Object acos(Object exp) {
		return Util.acos((SymDouble)exp);
	}

	public Object atan(Object exp) {
		return Util.atan((SymDouble)exp);
	}

	public Object log(Object exp) {
		return Util.log((SymDouble)exp);
	}

	public Object tan(Object exp) {
		return Util.tan((SymDouble)exp);
	}

	public Object sqrt(Object exp) {
		return Util.sqrt((SymDouble)exp);
	}

	public Object power(Object exp1, Object exp2) {
		return Util.pow((SymDouble)exp1, (SymDouble)exp2);
	}

	public Object power(Object exp1, double exp2) {
		return Util.pow((SymDouble)exp1, Util.createConstant(exp2));
	}

	public Object power(double exp1, Object exp2) {
		return Util.pow(Util.createConstant(exp1), (SymDouble)exp2);
	}

	public Object atan2(Object exp1, Object exp2) {
		return Util.atan2((SymDouble)exp1, (SymDouble)exp2);
	}

	public Object atan2(Object exp1, double exp2) {
		return Util.atan2((SymDouble)exp1, Util.createConstant(exp2));
	}

	public Object atan2(double exp1, Object exp2) {
		return Util.atan2(Util.createConstant(exp1), (SymDouble)exp2);
	}

	Env sol = null;
	@Override
	/**
	 * JPF calls this method when it needs to solve the path condition
	 */
	public Boolean solve() {
		Solver solver = solverKind.get();
		Boolean result = null;
		try {
			sol = solveIt(pc, solver);
			/**
			 * this is to comply with the assumption
			 * of the calling method
			 */

			if (sol.getResult() == Result.SAT) {
				result = true;
			}
		} catch (Exception _) {
		}
//		finally {
//			System.out.printf(">>> %s %s %s\n", pc.toString(), sol, result);
//		}
		return result;
	}



	@SuppressWarnings("unused")
	private Env solveIt(final PC pc, final Solver solver) throws InterruptedException {
		final Env[] env = new Env[1];
		Runnable solverJob = new Runnable() {
			@Override
			public void run() {
				try {
					env[0] = solver.getCallable(pc).call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		/**
		 * If solving is based on timeouts (value > 0)
		 * the code spawns a timer thread.  otherwise,
		 * it calls the run() method directly.
		 */
//		if (timeout > 0) { // old code; not executed
//			Thread t = new Thread(solverJob);
//			t.start();
//			t.join(timeout);
//			solver.setPleaseStop(true);
//			Thread.sleep(10);
//		} else {
		solverJob.run();
//		}
		return env[0];
	}

	@Override
	public double getRealValueInf(Object dpvar) {
		return -1;
	}

	@Override
	public double getRealValueSup(Object dpVar) {
		return -1;
	}

	@Override
	public double getRealValue(Object dpVar) {
		SymNumber symNumber = sol.getValue((SymLiteral)dpVar);
		return symNumber.evalNumber().doubleValue();
	}

	@Override
	public int getIntValue(Object dpVar) {
		SymNumber symNumber = sol.getValue((SymLiteral)dpVar);
		try {
		return symNumber.evalNumber().intValue();
		} catch (NullPointerException _) {
			throw _;
		}
	}

	@Override
	/**
	 * JPF calls this method to add a new boolean expression
	 * to the path condition
	 */
	public void post(Object constraint) {
		pc.addConstraint((SymBool)constraint);
	}

	@Override
	public void postLogicalOR(Object[] constraints) {
		// TODO Auto-generated method stub

		SymBool orResult = Util.FALSE;
		for (int i =0; i<constraints.length; i++) {
			System.out.println("****** orResult"+ orResult + "************ " +i);
			orResult = Util.or(orResult, (SymBool) ( constraints[i]));
		}

		post(orResult);

	}
	
}
