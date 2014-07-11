//
//Copyright (C) 2006 United States Government as represented by the
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

//TODO: problem: we do not distinguish between ints and reals?
// still needs a lot of work: do not use!


import java.util.HashMap;
import java.util.List;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.RealExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;



public class ProblemZ3 extends ProblemGeneral {
	Context ctx;
	Solver solver;

	public ProblemZ3() {
		HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");

		try{
			ctx = new Context(cfg);
			solver = ctx.MkSolver();
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
	    }
	}


	//if min or max are passed in as null objects 
	//it will use minus and plus infinity
	// TODO: to add ranges
	public Object makeIntVar(String name, int min, int max) {
		try{
			
			return ctx.MkIntConst(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}


	// TODO: to add ranges
	public Object makeRealVar(String name, double min, double max) {

		try{
			return ctx.MkReal(name); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object eq(int value, Object exp){
		try{
			return ctx.MkEq( ctx.MkInt(value), (IntExpr)exp);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object eq(Object exp, int value){
		try{
			return ctx.MkEq( ctx.MkInt(value), (IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	// should we use Expr or ArithExpr?
	public Object eq(Object exp1, Object exp2){
		try{
			return  ctx.MkEq((Expr)exp1, (Expr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	// TODO: should convert double to rational
//	public Object eq(double value, Object exp){
//		try{
//			return  ctx.MkEq(ctx.MkReal(arg0, arg1), (RealExpr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
//
//	    }
//	}

//	public Object eq(Object exp, double value){
//		try{
//			return  ctx.MkEq(ctx.MkReal(arg0, arg1), (RealExpr)exp);;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
//
//	    }
//	}

	public Object neq(int value, Object exp){
		try{
			return ctx.MkNot(ctx.MkEq(ctx.MkInt(value), (IntExpr)exp));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object neq(Object exp, int value){
		try{
			return ctx.MkNot(ctx.MkEq(ctx.MkInt(value), (IntExpr)exp));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object neq(Object exp1, Object exp2){
		try{
			return  ctx.MkNot(ctx.MkEq((Expr)exp1, (Expr)exp2));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object not(Object exp1){
		try{
			return  ctx.MkNot((BoolExpr)exp1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);

	    }
	}

	// TODO: convert doubles to rationals
//	public Object neq(double value, Object exp){
//		try{
//			return  vc.notExpr(vc.eqExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//	public Object neq(Object exp, double value){
//		try{
//			return  vc.notExpr(vc.eqExpr((Expr)exp, vc.ratExpr(Double.toString(value), base)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}

	public Object leq(int value, Object exp){
		try{
			return  ctx.MkLe(ctx.MkInt(value), (IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object leq(Object exp, int value){
		try{
			return  ctx.MkLe((IntExpr)exp,ctx.MkInt(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object leq(Object exp1, Object exp2){
		try{
			return  ctx.MkLe((ArithExpr)exp1, (ArithExpr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

//	public Object leq(double value, Object exp){
//		try{
//			return  vc.leExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//	public Object leq(Object exp, double value){
//		try{
//			return  vc.leExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
	public Object geq(int value, Object exp){
		try{
			return  ctx.MkGe(ctx.MkInt(value),(IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object geq(Object exp, int value){
		try{
			return  ctx.MkGe((IntExpr)exp,ctx.MkInt(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object geq(Object exp1, Object exp2){
		try{
			return  ctx.MkGe((ArithExpr)exp1,(ArithExpr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

//	public Object geq(double value, Object exp){
//		try{
//			return  vc.geExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//	public Object geq(Object exp, double value){
//		try{
//			return  vc.geExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
	public Object lt(int value, Object exp){
		try{
			return  ctx.MkLt(ctx.MkInt(value),(IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object lt(Object exp, int value){
		try{
			return  ctx.MkLt((IntExpr)exp,ctx.MkInt(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object lt(Object exp1, Object exp2){
		try{
			return  ctx.MkLt((ArithExpr)exp1,(ArithExpr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

//	public Object lt(double value, Object exp){
//		try{
//			return  vc.ltExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//	public Object lt(Object exp, double value){
//		try{
//			return  vc.ltExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//
	public Object gt(int value, Object exp){
		try{
			return  ctx.MkGt(ctx.MkInt(value),(IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object gt(Object exp, int value){
		try{
			return  ctx.MkGt((IntExpr)exp,ctx.MkInt(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

	public Object gt(Object exp1, Object exp2){
		try{
			return  ctx.MkGt((ArithExpr)exp1,(ArithExpr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);

	    }
	}

//	public Object implies(Object exp1, Object exp2){
//		try{
//			return  vc.impliesExpr((Expr)exp1, (Expr)exp2);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}

//	public Object gt(double value, Object exp){
//		try{
//			return  vc.gtExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//	public Object gt(Object exp, double value){
//		try{
//			return  vc.gtExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//
//	    }
//	}
//
//
//
//
	public Object plus(int value, Object exp) {
		try{
			return  ctx.MkAdd(new ArithExpr[] { ctx.MkInt(value), (IntExpr)exp});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object plus(Object exp, int value) {
		try{
			return  ctx.MkAdd(new ArithExpr[] { ctx.MkInt(value), (IntExpr)exp});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object plus(Object exp1, Object exp2) {
		try{
			return  ctx.MkAdd(new ArithExpr[] { (ArithExpr)exp1, (ArithExpr)exp2});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

//	public Object plus(double value, Object exp) {
//		try{
//			return  vc.plusExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}
//
//	public Object plus(Object exp, double value) {
//		try{
//			return  vc.plusExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}

	public Object minus(int value, Object exp) {
		try{
			return  ctx.MkSub(new ArithExpr[] { ctx.MkInt(value), (IntExpr)exp});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object minus(Object exp, int value) {
		try{
			return  ctx.MkSub(new ArithExpr[] {(IntExpr)exp, ctx.MkInt(value)});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object minus(Object exp1, Object exp2) {
		try{
			return  ctx.MkSub(new ArithExpr[] { (ArithExpr)exp1, (ArithExpr)exp2});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

//	public Object minus(double value, Object exp) {
//		try{
//			return  vc.minusExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}
//
//	public Object minus(Object exp, double value) {
//		try{
//			return  vc.minusExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}

	public Object mult(int value, Object exp) {
		try{
			return  ctx.MkMul(new ArithExpr[] {(IntExpr)exp, ctx.MkInt(value)});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object mult(Object exp, int value) {
		try{
			return  ctx.MkMul(new ArithExpr[] {(IntExpr)exp, ctx.MkInt(value)});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object mult(Object exp1, Object exp2) {
		try{
			return  ctx.MkMul(new ArithExpr[] {(ArithExpr)exp1, (ArithExpr)exp2});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}
//	public Object mult(double value, Object exp) {
//		try{
//			return  vc.multExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}
//	public Object mult(Object exp, double value) {
//		try{
//			return  vc.multExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}
//
//	

	public Object div(int value, Object exp) {
		try{
			return  ctx.MkDiv(ctx.MkInt(value), (IntExpr)exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object div(Object exp, int value) {
		try{
			return  ctx.MkDiv((IntExpr)exp,ctx.MkInt(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}

	public Object div(Object exp1, Object exp2) {
		try{
			return  ctx.MkDiv((ArithExpr)exp1,(ArithExpr)exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in Z3 JNI: \n" + e);
		}
	}
//	public Object div(double value, Object exp) {
//		try{
//			return  vc.divideExpr(vc.ratExpr(Double.toString(value), base), (Expr)exp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}
//	public Object div(Object exp, double value) {
//		try{
//			return  vc.divideExpr((Expr)exp, vc.ratExpr(Double.toString(value), base));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("## Error CVC3: Exception caught in CVC3 JNI: \n" + e);
//		}
//	}



	
	

	public int getIntValue(Object dpVar) { 
		try{
			Model model = null;
			 if (Status.SATISFIABLE == solver.Check())
	         {
	             model = solver.Model();
	             return Integer.parseInt((model.Evaluate((IntExpr)dpVar,false)).toString());
	         }
	         else {
	        	 assert false; // should not be reachable
	             return 0;
	         }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error Z3: Exception caught in CVC3 JNI: \n" + e);
		}
	}

	

	private Expr test(){
		Expr e = (Expr)makeIntVar("Z",-10, 10);
		Expr f = (Expr)makeIntVar("f", -10,10);
		Expr plus = (Expr)plus(10,e);
		Expr plus2 = (Expr)plus(1,e);
		Expr eq = (Expr)eq(plus, plus2);
		return eq;
	}

	public Boolean solve() {
        try {
        	/* find model for the constraints above */
            Model model = null;
            if (Status.SATISFIABLE == solver.Check())
            {
                model = solver.Model();
                System.out.println(model);
                return true;
            } else
          
                return false;
        	
   
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException("## Error Z3: " + e);
        }
	}

	public void post(Object constraint) {
		try{
			solver.Assert((BoolExpr)constraint);
		} catch (Exception e) {
			e.printStackTrace();
        	throw new RuntimeException("## Error Z3 \n" + e);
	    }
	}


	
	// need to implement all of these
	@Override
	public Object eq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object eq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object neq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object neq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object leq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object leq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}




	@Override
	public Object geq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object geq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}





	@Override
	public Object lt(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object lt(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}





	@Override
	public Object gt(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object gt(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}



	@Override
	public Object minus(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object minus(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}



	@Override
	public Object mult(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object mult(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}

	@Override
	public Object div(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object div(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object and(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object and(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object and(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object or(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object or(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object or(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object xor(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object xor(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object xor(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftL(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftL(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftL(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftR(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftR(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftR(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftUR(int value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftUR(Object exp, int value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object shiftUR(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object mixed(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public double getRealValueInf(Object dpvar) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");//return 0;
	}


	@Override
	public double getRealValueSup(Object dpVar) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");//return 0;
	}


	@Override
	public double getRealValue(Object dpVar) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");//return 0;
	}


	@Override
	public void postLogicalOR(Object[] constraint) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");	
	}


	@Override
	public Object plus(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	@Override
	public Object plus(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error Z3 \n");
	}


	

}