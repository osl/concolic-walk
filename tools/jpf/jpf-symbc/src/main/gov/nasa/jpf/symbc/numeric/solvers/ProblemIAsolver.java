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

import ia_math.RealInterval;
import ia_parser.Exp;
import ia_parser.IAParser;
import ia_parser.RealIntervalTable;
//import ia_parser.sym;

public class ProblemIAsolver extends ProblemGeneral {
	String pb;
	String format = "%20.10f";

	public ProblemIAsolver() {
		pb = "";
	}

	public String makeIntVar(String name, int min, int max) {
		pb = pb + name + " >= " + min + "; "+ name + " <= " + max + "; ";
		return name;
	}

	public String makeRealVar(String name, double min, double max) {
		pb = pb + name + " >= " + min + "; "+ name + " <= " + max + "; ";
		return name;
	}

	public Object eq(int value, Object exp){return  value + " = " + (String)exp + "; ";}
	public Object eq(Object exp, int value){return  (String)exp + " = " + value + "; ";}
	public Object eq(Object exp1, Object exp2){
		return  (String)exp1 + " = " + (String)exp2 + "; ";
	}
	// could be a problem here with the number format
	public Object eq(double value, Object exp){return  String.format(format,value) + " = " + (String)exp + "; ";}
	public Object eq(Object exp, double value){return  (String)exp + " = " + String.format(format,value) + "; ";}

	public Object neq(int value, Object exp){return  value + " != " + (String)exp + "; ";}
	public Object neq(Object exp, int value){return  (String)exp + " != " + value + "; ";}
	public Object neq(Object exp1, Object exp2){
		return  (String)exp1 + " != " + (String)exp2 + "; ";
	}
	public Object neq(double value, Object exp){return  String.format(format,value) + " != " + (String)exp + "; ";}
	public Object neq(Object exp, double value){return  (String)exp + " != " + String.format(format,value) + "; ";}


	public Object leq(int value, Object exp){return  value + " <= " + (String)exp + "; ";}
	public Object leq(Object exp, int value){return  (String)exp + " <= " + value + "; ";}
	public Object leq(Object exp1, Object exp2){
		return  (String)exp1 + " <= " + (String)exp2 + "; ";
	}
	public Object leq(double value, Object exp){return  String.format(format,value) + " <= " + (String)exp + "; ";}
	public Object leq(Object exp, double value){return  (String)exp + " <= " + String.format(format,value) + "; ";}

	public Object geq(int value, Object exp){return  value + " >= " + (String)exp + "; ";}
	public Object geq(Object exp, int value){return  (String)exp + " >= " + value + "; ";}
	public Object geq(Object exp1, Object exp2){
		return  (String)exp1 + " >= " + (String)exp2 + "; ";
	}
	public Object geq(double value, Object exp){return  String.format(format,value) + " >= " + (String)exp + "; ";}
	public Object geq(Object exp, double value){return  (String)exp + " >= " + String.format(format,value) + "; ";}

	public Object lt(int value, Object exp){return  value + " < " + (String)exp + "; ";}
	public Object lt(Object exp, int value){return  (String)exp + " < " + value + "; ";}
	public Object lt(Object exp1, Object exp2){
		return  (String)exp1 + " < " + (String)exp2 + "; ";
	}
	public Object lt(double value, Object exp){return  String.format(format,value) + " < " + (String)exp + "; ";}
	public Object lt(Object exp, double value){return  (String)exp + " < " + String.format(format,value) + "; ";}


	public Object gt(int value, Object exp){return  value + " > " + (String)exp + "; ";}
	public Object gt(Object exp, int value){return  (String)exp + " > " + value + "; ";}
	public Object gt(Object exp1, Object exp2){
		return  (String)exp1 + " > " + (String)exp2 + "; ";
	}
	public Object gt(double value, Object exp){return  String.format(format,value) + " > " + (String)exp + "; ";}
	public Object gt(Object exp, double value){return  (String)exp + " > " + String.format(format,value) + "; ";}

	public Object plus(int value, Object exp) {return  "("+value + "+" + exp +")" ;}
	public Object plus(Object exp, int value) {return  "("+exp + "+" + value +")" ;}
	public Object plus(Object exp1, Object exp2) {return  "("+exp1 + "+" + exp2 +")" ;}
	public Object plus(double value, Object exp) {return  "("+String.format(format,value) + "+" + exp +")" ;}
	public Object plus(Object exp, double value) {return  "("+exp + "+" + String.format(format,value) +")" ;}

	public Object minus(int value, Object exp) {return  "("+value + "-" + exp +")" ;}
	public Object minus(Object exp, int value) {return  "("+exp + "-" + value +")" ;}
	public Object minus(Object exp1, Object exp2) {return  "("+exp1 + "-" + exp2 +")" ;}
	public Object minus(double value, Object exp) {return  "("+String.format(format,value) + "-" + exp +")" ;}
	public Object minus(Object exp, double value) {return  "("+exp + "-" + String.format(format,value) +")" ;}

	public Object mult(int value, Object exp) {return  "("+value + "*" + exp +")" ;}
	public Object mult(Object exp, int value) {return  "("+exp + "*" + value +")" ;}
	public Object mult(Object exp1, Object exp2) {return  "("+exp1 + "*" + exp2 +")" ;}
	public Object mult(double value, Object exp) {return  "("+String.format(format,value) + "*" + exp +")" ;}
	public Object mult(Object exp, double value) {return  "("+exp + "*" + String.format(format,value) +")" ;}

	public Object div(int value, Object exp) {return  "("+value + "/" + exp +")" ;}
	public Object div(Object exp, int value) {return  "("+exp + "/" + value +")" ;}
	public Object div(Object exp1, Object exp2) {return  "("+exp1 + "/" + exp2 +")" ;}
	public Object div(double value, Object exp) {return  "("+String.format(format,value) + "/" + exp +")" ;}
	public Object div(Object exp, double value) {return  "("+exp + "/" + String.format(format,value) +")" ;}


	public Object sin(Object exp) {
		return "sin("+exp+")";
	}
	public Object cos(Object exp) {
		return "cos("+exp+")";
	}

	public Object power(Object exp1, Object exp2) {
		//return "("+exp1+"**"+exp2+")";
		return "("+exp1+"^"+exp2+")"; // TODO: to ask Hank about the difference between ** and ^
	}

	public Object power(Object exp1, double exp2) {
		//return "("+exp1+"**"+exp2+")";
		return "("+exp1+"^"+exp2+")"; // TODO: to ask Hank about the difference between ** and ^
	}

	public Object power(double exp1, Object exp2) {
		//return "("+exp1+"**"+exp2+")";
		return "("+exp1+"^"+exp2+")"; // TODO: to ask Hank about the difference between ** and ^
	}

	public Object sqrt(Object exp) {
		//return "("+exp1+"**"+exp2+")";
		// TODO: add test exp >=0
		return "("+exp+"^"+0.5+")"; // TODO: to ask Hank about the difference between ** and ^
	}

	public Object exp(Object exp) {
		return "exp("+exp+")";
	}

	public Object asin(Object exp) {
		return "asin("+exp+")";
	}
	public Object acos(Object exp) {
		return "acos("+exp+")";
	}
	public Object atan(Object exp) {
		return "atan("+exp+")";
	}
	public Object log(Object exp) {
		return "log("+exp+")";
	}
	public Object tan(Object exp) {
		return "tan("+exp+")";
	}
	public Object mixed(Object exp1, Object exp2) {
		return (String)exp1 + " = " + (String)exp2 + "; ";
	}

	// IASolver specific
	RealIntervalTable vars;
	boolean narrow(Exp exp, int numNarrows) {
		vars = new RealIntervalTable();
		exp.bindVars(vars);
		for (int i = 0; i <= numNarrows; i++) {
			if (!exp.narrow()) {
				//System.out.println("narrow failed");
				return false;
			}
		}
//		String var_string = "";
//		for (int j = 0; j < vars.size(); j++)
//			var_string = var_string + "  " + vars.getEnvPairString2(j) + "\n";
//		System.out.println("narrow succeeded"+ var_string);
		return true;
	}

	// TODO: maybe cut chooseValue: not used
	static double chooseValue(RealInterval interval) {
		double lo = interval.lo();
		double hi = interval.hi();
		double chosen;

		if (Double.isInfinite(lo)) {
			if (Double.isInfinite(hi)) {
				chosen = 1.12;
			} else {
				chosen = hi - 1.0;
			}
		} else {
			if (Double.isInfinite(hi)) {
				chosen = lo + 1.0;
			} else {
				chosen = (hi + lo) / 2.0;
			}
		}
		return chosen;
	}


	public double getRealValueInf(Object dpVar) {
		assert(vars != null && dpVar !=null);
		return vars.lookup((String)dpVar).lo();
	}

	public double getRealValueSup(Object dpVar) {
		assert(vars != null && dpVar !=null);
		return vars.lookup((String)dpVar).hi();
	}
	public double getRealValue(Object dpVar) {
		throw new RuntimeException("# Error: IASolver can not compute real solution!");
	}

	public int getIntValue(Object dpVar) {
		throw new RuntimeException("# Error: IASolver can not compute int solution!");
	}

	public Boolean solve() {
		String c = pb;
		if(c==null || c=="")
			return true;
		try {
			int max_narrow = 100;// TODO: play with different values for max_narrow;

			// Solve the original system
			//System.out.println(" PARSE: "+c);
			Exp exp = IAParser.parseString(c);
			return narrow(exp, max_narrow);

		} catch (Exception parse_exception) {
			parse_exception.printStackTrace();
			throw new RuntimeException("## Error IASolver: "+pb);//parse_exception);
		}
	}

	public void post(Object constraint) {
		pb = pb + constraint;
	}

	public Object and(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise AND");
	}

	public Object and(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise AND");
	}

	public Object and(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise AND");
	}

	@Override
	public Object or(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise OR");
	}

	@Override
	public Object or(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise OR");
	}

	@Override
	public Object or(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise OR");
	}

	public Object shiftL(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftL(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftR(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftR(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object xor(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise XOR");
	}

	public Object xor(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise XOR");
	}

	public Object xor(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise XOR");
	}

	public Object shiftL(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftR(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftUR(int value, Object exp) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");

	}

	public Object shiftUR(Object exp, int value) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	public Object shiftUR(Object exp1, Object exp2) {
		throw new RuntimeException("## Error IASolver does not support bitwise SHIFT");
	}

	@Override
	public void postLogicalOR(Object[] constraint) {
		// TODO Auto-generated method stub
		throw new RuntimeException("## Error IASolver does not support LogicalOR");
	}


}
