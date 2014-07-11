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

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

import java.util.ArrayList;
import java.util.List;

import cvc3.Expr;
import cvc3.Rational;
import cvc3.Type;

public class ProblemCVC3BitVector extends ProblemCVC3 {

	public Object makeBitVectorVar(String name, int N) {
		try {
			return vc.varExpr(name, vc.bitvecType(N));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVec: Could not create a bitvector var" + e);
		}
	}
	
	

	public Object and(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVAndExpr(vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object and(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVAndExpr((Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object and(Object exp1, Object exp2) {
		try {
			return vc.newBVAndExpr((Expr) exp1,  (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object or(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVOrExpr(vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object or(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVOrExpr((Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object or(Object exp1, Object exp2) {
		try {
			return vc.newBVOrExpr((Expr) exp1,  (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object shiftL(int value, Object exp) {
		try {
			return vc.newFixedLeftShiftExpr((Expr) exp, value);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object shiftL(Object exp, int value) {
		try {
			return vc.newFixedLeftShiftExpr((Expr) exp, value);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object shiftL(Object exp1, Object exp2) {
		try {
			return vc.newBVSHL((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object shiftR(int value, Object exp) {
		try {
			return vc.newFixedRightShiftExpr((Expr) exp, value);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object shiftR(Object exp, int value) {
		try {
			return vc.newFixedRightShiftExpr((Expr) exp, value);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object shiftR(Object exp1, Object exp2) {
		try {
			return vc.newBVASHR((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}	}

	public Object shiftUR(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString(
					(0xffffffffL << (32 - value)) ^
					(0xffffffffL << (32 - value))),
					vc.embeddedManager());

			return vc.newBVAndExpr(vc.newFixedRightShiftExpr((Expr) exp, value),
					vc.newBVConstExpr(val, 32));

		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}

	}

	public Object shiftUR(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString(
					(0xffffffffL << (32 - value)) ^
					(0xffffffffL << (32 - value))),
					vc.embeddedManager());

			return vc.newBVAndExpr(vc.newFixedRightShiftExpr((Expr) exp, value),
					vc.newBVConstExpr(val, 32));

		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object shiftUR(Object exp1, Object exp2) {
		throw new RuntimeException("## Shifting by an expression not supported by CVC3");
	}

	public Object xor(int value, Object exp) {
		try {
			//Integer.toString(value);
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVXorExpr(vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}

	public Object xor(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVXorExpr((Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object xor(Object exp1, Object exp2) {
		try {
			return vc.newBVXorExpr((Expr) exp1,  (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error CVC3BitVector ");
		}
	}


	public Object div(int value, Object exp) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVSDivExpr(vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object div(Object exp, int value) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVSDivExpr((Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	public Object div(Object exp1, Object exp2) {
		try{
			return vc.newBVSDivExpr((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	@Override
	public Object div(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object div(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object eq(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.eqExpr(vc.newBVConstExpr(val, 32), (Expr)exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object eq(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.eqExpr((Expr)exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object eq(Object exp1, Object exp2) {
		try {
			return vc.eqExpr((Expr)exp1, (Expr)exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object eq(double value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( (long)value &
										0xffffffffffffffffL ), vc.embeddedManager());
			return vc.eqExpr(vc.newBVConstExpr(val, 32), (Expr)exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object eq(Object exp, double value) {
		try {
			Rational val = new Rational(Long.toString( (long)value &
										0xffffffffffffffffL ), vc.embeddedManager());
			return vc.eqExpr((Expr)exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object geq(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.newBVLTExpr(vc.newBVConstExpr(val, 32), (Expr)exp));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object geq(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.newBVLTExpr((Expr)exp, vc.newBVConstExpr(val, 32)));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object geq(Object exp1, Object exp2) {
		try {
			return vc.notExpr(vc.newBVLTExpr((Expr) exp1, (Expr) exp2));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}

	@Override
	public Object geq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object geq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public int getIntValue(Object dpVar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRealValue(Object dpVar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRealValueInf(Object dpvar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRealValueSup(Object dpVar) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object gt(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.newBVLEExpr(vc.newBVConstExpr(val, 32), (Expr)exp));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}

	public Object gt(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.newBVLEExpr((Expr)exp, vc.newBVConstExpr(val, 32)));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object gt(Object exp1, Object exp2) {
		try {
			return vc.notExpr(vc.newBVLEExpr((Expr) exp1, (Expr) exp2));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}

	@Override
	public Object gt(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object gt(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object leq(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVLEExpr(vc.newBVConstExpr(val, 32), (Expr)exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object leq(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVLEExpr((Expr)exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object leq(Object exp1, Object exp2) {
		try {
			return vc.newBVLEExpr((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object leq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object leq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object lt(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVLTExpr(vc.newBVConstExpr(val, 32), (Expr)exp);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}

	@Override
	public Object lt(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVLTExpr((Expr)exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}


	public Object lt(Object exp1, Object exp2) {
		try {
			return vc.newBVLTExpr((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			throw new RuntimeException("## Error: CVC3 BitVector");
		}
	}

	@Override
	public Object lt(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object lt(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object makeIntVar(String name, int min, int max) {
		return makeBitVectorVar(name, 32);
	}

	@Override
	public Object makeRealVar(String name, double min, double max) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object minus(int value, Object exp) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVSubExpr(vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object minus(Object exp, int value) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVSubExpr((Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	@Override
	public Object minus(Object exp1, Object exp2) {
		try{
			return vc.newBVSubExpr((Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	@Override
	public Object minus(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object minus(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object mixed(Object exp1, Object exp2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object mult(int value, Object exp) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVMultExpr(32, vc.newBVConstExpr(val, 32), (Expr) exp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object mult(Object exp, int value) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.newBVMultExpr(32, (Expr) exp, vc.newBVConstExpr(val, 32));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object mult(Object exp1, Object exp2) {
		try{
			return vc.newBVMultExpr(32, (Expr) exp1, (Expr) exp2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	@Override
	public Object mult(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object mult(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object neq(int value, Object exp) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.eqExpr(vc.newBVConstExpr(val, 32), (Expr) exp));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector");
		}
	}

	public Object neq(Object exp, int value) {
		try {
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			return vc.notExpr(vc.eqExpr((Expr) exp, vc.newBVConstExpr(val, 32)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector");
		}
	}


	public Object neq(Object exp1, Object exp2) {
		try {
			return vc.notExpr(vc.eqExpr((Expr) exp1, (Expr) exp2));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector");
		}
	}

	@Override
	public Object neq(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	@Override
	public Object neq(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}

	public Object plus(int value, Object exp) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			List<Expr> exprs = new ArrayList<Expr>();
			exprs.add(vc.newBVConstExpr(val, 32));
			exprs.add((Expr) exp);
			return vc.newBVPlusExpr(32, exprs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object plus(Object exp, int value) {
		try{
			Rational val = new Rational(Long.toString( value & 0xffffffffL ),
																vc.embeddedManager());
			List<Expr> exprs = new ArrayList<Expr>();
			exprs.add((Expr) exp);
			exprs.add(vc.newBVConstExpr(val, 32));
			return vc.newBVPlusExpr(32, exprs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}


	public Object plus(Object exp1, Object exp2) {
		try{
			List<Expr> exprs = new ArrayList<Expr>();
			exprs.add((Expr) exp1);
			exprs.add((Expr) exp2);
			return vc.newBVPlusExpr(32, exprs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("## Error CVC3BitVector" + e);
		}
	}

	@Override
	public Object plus(double value, Object exp) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


	public Object plus(Object exp, double value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("bit vector");
	}


}