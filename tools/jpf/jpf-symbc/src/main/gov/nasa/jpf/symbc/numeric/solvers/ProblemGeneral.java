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

public abstract class ProblemGeneral{
	public abstract Object makeIntVar(String name, int min, int max);
	public abstract Object makeRealVar(String name, double min, double max);

	public abstract Object eq(int value, Object exp) ;
	public abstract Object eq(Object exp, int value) ;
	public abstract Object eq(Object exp1, Object exp2) ;
	public abstract Object eq(double value, Object exp) ;
	public abstract Object eq(Object exp, double value) ;
	public abstract Object neq(int value, Object exp) ;
	public abstract Object neq(Object exp, int value) ;
	public abstract Object neq(Object exp1, Object exp2) ;
	public abstract Object neq(double value, Object exp) ;
	public abstract Object neq(Object exp, double value) ;
	public abstract Object leq(int value, Object exp) ;
	public abstract Object leq(Object exp, int value) ;
	public abstract Object leq(Object exp1, Object exp2) ;
	public abstract Object leq(double value, Object exp) ;
	public abstract Object leq(Object exp, double value) ;
	public abstract Object geq(int value, Object exp) ;
	public abstract Object geq(Object exp, int value) ;
	public abstract Object geq(Object exp1, Object exp2) ;
	public abstract Object geq(double value, Object exp) ;
	public abstract Object geq(Object exp, double value) ;
	public abstract Object lt(int value, Object exp) ;
	public abstract Object lt(Object exp, int value) ;
	public abstract Object lt(Object exp1, Object exp2) ;
	public abstract Object lt(double value, Object exp) ;
	public abstract Object lt(Object exp, double value) ;
	public abstract Object gt(int value, Object exp) ;
	public abstract Object gt(Object exp, int value) ;
	public abstract Object gt(Object exp1, Object exp2) ;
	public abstract Object gt(double value, Object exp) ;
	public abstract Object gt(Object exp, double value) ;

	public abstract Object plus(int value, Object exp) ;
	public abstract Object plus(Object exp, int value) ;
	public abstract Object plus(Object exp1, Object exp2) ;
	public abstract Object plus(double value, Object exp) ;
	public abstract Object plus(Object exp, double value) ;
	public abstract Object minus(int value, Object exp) ;
	public abstract Object minus(Object exp, int value) ;
	public abstract Object minus(Object exp1, Object exp2) ;
	public abstract Object minus(double value, Object exp) ;
	public abstract Object minus(Object exp, double value) ;
	public abstract Object mult(int value, Object exp) ;
	public abstract Object mult(Object exp, int value) ;
	public abstract Object mult(Object exp1, Object exp2) ;
	public abstract Object mult(double value, Object exp) ;
	public abstract Object mult(Object exp, double value) ;
	public abstract Object div(int value, Object exp) ;
	public abstract Object div(Object exp, int value) ;
	public abstract Object div(Object exp1, Object exp2) ;
	public abstract Object div(double value, Object exp) ;
	public abstract Object div(Object exp, double value) ;

	public abstract Object and(int value, Object exp) ;
	public abstract Object and(Object exp, int value) ;
	public abstract Object and(Object exp1, Object exp2) ;

	public abstract Object or(int value, Object exp) ;
	public abstract Object or(Object exp, int value) ;
	public abstract Object or(Object exp1, Object exp2) ;

	public abstract Object xor(int value, Object exp) ;
	public abstract Object xor(Object exp, int value) ;
	public abstract Object xor(Object exp1, Object exp2) ;

	public abstract Object shiftL(int value, Object exp) ;
	public abstract Object shiftL(Object exp, int value) ;
	public abstract Object shiftL(Object exp1, Object exp2) ;

	public abstract Object shiftR(int value, Object exp) ;
	public abstract Object shiftR(Object exp, int value) ;
	public abstract Object shiftR(Object exp1, Object exp2) ;


	public abstract Object shiftUR(int value, Object exp) ;
	public abstract Object shiftUR(Object exp, int value) ;
	public abstract Object shiftUR(Object exp1, Object exp2) ;

	public Object constant(double d) {
		throw new RuntimeException("## Error: constant not supported");
	}

	public Object sin(Object exp) {
		throw new RuntimeException("## Error: Math.sin not supported");
	}
	public Object cos(Object exp) {
		throw new RuntimeException("## Error: Math.cos not supported");
	}

	public Object round(Object exp) {
		throw new RuntimeException("## Error: Math.round not supported");
	}
	public Object exp(Object exp) {
		throw new RuntimeException("## Error: Math.exp not supported");
	}
	public Object asin(Object exp) {
		throw new RuntimeException("## Error: Math.asin not supported");

	}
	public Object acos(Object exp) {
		throw new RuntimeException("## Error: Math.acos not supported");

	}
	public Object atan(Object exp) {
		throw new RuntimeException("## Error: Math.atan not supported");

	}
	public Object log(Object exp) {
		throw new RuntimeException("## Error: Math.log not supported");

	}
	public Object tan(Object exp) {
		throw new RuntimeException("## Error: Math.tan not supported");

	}
	public Object sqrt(Object exp) {
		throw new RuntimeException("## Error: Math.sqrt not supported");

	}
	public Object power(Object exp1, Object exp2) {
		throw new RuntimeException("## Error: Math.power not supported");
	}
	public Object power(Object exp1, double exp2) {
		throw new RuntimeException("## Error: Math.power not supported");
	}
	public Object power(double exp1, Object exp2) {
		throw new RuntimeException("## Error: Math.power not supported");
	}

	public Object atan2(Object exp1, Object exp2) {
		throw new RuntimeException("## Error: Math.atan2 not supported");
	}
	public Object atan2(Object exp1, double exp2) {
		throw new RuntimeException("## Error: Math.atan2 not supported");
	}
	public Object atan2(double exp1, Object exp2) {
		throw new RuntimeException("## Error: Math.atan2 not supported");
	}

	public abstract Object mixed(Object exp1, Object exp2);

	public abstract Boolean solve();
	
	public abstract double getRealValueInf(Object dpvar);
	public abstract double getRealValueSup(Object dpVar);
	public abstract double getRealValue(Object dpVar);
	public abstract int getIntValue(Object dpVar);

	public abstract void post(Object constraint);

	public abstract void postLogicalOR(Object [] constraint);

}
