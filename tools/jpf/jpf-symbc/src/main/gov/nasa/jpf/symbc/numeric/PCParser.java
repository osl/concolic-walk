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

package gov.nasa.jpf.symbc.numeric;

import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemCompare;
import gov.nasa.jpf.symbc.numeric.solvers.DebugSolvers;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemCVC3;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemCVC3BitVector;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemChoco;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemChoco2;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemCoral;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemGeneral;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemIAsolver;
import gov.nasa.jpf.symbc.numeric.solvers.ProblemYices;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


// parses PCs

public class PCParser {
	  static ProblemGeneral pb;
	  static public Map<SymbolicReal, Object>	symRealVar; // a map between symbolic real variables and DP variables
	  static Map<SymbolicInteger,Object>	symIntegerVar; // a map between symbolic variables and DP variables
	  //static Boolean result; // tells whether result is satisfiable or not
	  static int tempVars = 0; //Used to construct "or" clauses

	  //	 Converts IntegerExpression's into DP's IntExp's
	  static Object getExpression(IntegerExpression eRef) {
			assert eRef != null;
			assert !(eRef instanceof IntegerConstant);

			if (eRef instanceof SymbolicInteger) {

				Object dp_var = symIntegerVar.get(eRef);
				if (dp_var == null) {
					dp_var = pb.makeIntVar(((SymbolicInteger)eRef).getName(),
						((SymbolicInteger)eRef)._min, ((SymbolicInteger)eRef)._max);
					symIntegerVar.put((SymbolicInteger)eRef, dp_var);
				}
				return dp_var;
			}

			Operator    opRef;
			IntegerExpression	e_leftRef;
			IntegerExpression	e_rightRef;

			if(eRef instanceof BinaryLinearIntegerExpression) {
				opRef = ((BinaryLinearIntegerExpression)eRef).op;
				e_leftRef = ((BinaryLinearIntegerExpression)eRef).left;
				e_rightRef = ((BinaryLinearIntegerExpression)eRef).right;
			} else { // bin non lin expr
				if(pb instanceof ProblemCoral) {
					opRef = ((BinaryNonLinearIntegerExpression)eRef).op;
					e_leftRef = ((BinaryNonLinearIntegerExpression)eRef).left;
					e_rightRef = ((BinaryNonLinearIntegerExpression)eRef).right;
				}
				else
					throw new RuntimeException("## Error: Binary Non Linear Expression " + eRef);
			}
			switch(opRef){
			case PLUS:
				if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.plus(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.plus(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
				else
					return pb.plus(getExpression(e_leftRef),getExpression(e_rightRef));
			case MINUS:
				if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.minus(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.minus(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
				else
					return pb.minus(getExpression(e_leftRef),getExpression(e_rightRef));
			case MUL:
				if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.mult(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.mult(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else {
					 if(pb instanceof ProblemCoral)
						return pb.mult(getExpression(e_leftRef),getExpression(e_rightRef));
					 else
						throw new RuntimeException("## Error: Binary Non Linear Operation");
				}
			case DIV:
				if (e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant) // TODO: this might not be linear
					return pb.div(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.div(getExpression(e_leftRef),((IntegerConstant)e_rightRef).value);
				else {
					if(pb instanceof ProblemCoral)
						return pb.div(getExpression(e_leftRef),getExpression(e_rightRef));
					 else
						throw new RuntimeException("## Error: Binary Non Linear Operation");
				}
			case AND:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.and(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.and(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.and(getExpression(e_leftRef),getExpression(e_rightRef));
			case OR:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.or(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.or(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.or(getExpression(e_leftRef),getExpression(e_rightRef));
			case XOR:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.xor(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.xor(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.xor(getExpression(e_leftRef),getExpression(e_rightRef));
			case SHIFTR:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.shiftR(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.shiftR(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.shiftR(getExpression(e_leftRef),getExpression(e_rightRef));
			case SHIFTUR:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.shiftUR(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.shiftUR(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.shiftUR(getExpression(e_leftRef),getExpression(e_rightRef));
			case SHIFTL:
				if(e_leftRef instanceof IntegerConstant && e_rightRef instanceof IntegerConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof IntegerConstant)
					return pb.shiftL(((IntegerConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof IntegerConstant)
					return pb.shiftL(((IntegerConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.shiftL(getExpression(e_leftRef),getExpression(e_rightRef));
			default:
				throw new RuntimeException("## Error: Binary Non Linear Operation");
			}


		}


	// Converts RealExpression's into DP RealExp's
	static Object getExpression(RealExpression eRef) {
		assert eRef != null;
		assert !(eRef instanceof RealConstant);

		if (eRef instanceof SymbolicReal) {
			Object dp_var = symRealVar.get(eRef);
			if (dp_var == null) {
				dp_var = pb.makeRealVar(((SymbolicReal)eRef).getName(),
						((SymbolicReal)eRef)._min, ((SymbolicReal)eRef)._max);
				symRealVar.put((SymbolicReal)eRef, dp_var);
			}
			return dp_var;
		}

		if(eRef instanceof BinaryRealExpression) {
			Operator    opRef;
			RealExpression	e_leftRef;
			RealExpression	e_rightRef;
			opRef = ((BinaryRealExpression)eRef).op;
			e_leftRef = ((BinaryRealExpression)eRef).left;
			e_rightRef = ((BinaryRealExpression)eRef).right;

			switch(opRef){
			case PLUS:
				if (e_leftRef instanceof RealConstant && e_rightRef instanceof RealConstant)
					return pb.constant(((RealConstant)e_leftRef).value + ((RealConstant)e_rightRef).value);
					//throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof RealConstant)
					return pb.plus(((RealConstant)e_leftRef).value, getExpression(e_rightRef));
				else if (e_rightRef instanceof RealConstant)
					return pb.plus(getExpression(e_leftRef),((RealConstant)e_rightRef).value);
				else
					return pb.plus(getExpression(e_leftRef),getExpression(e_rightRef));
			case MINUS:
				if (e_leftRef instanceof RealConstant && e_rightRef instanceof RealConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof RealConstant)
					return pb.minus(((RealConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof RealConstant)
					return pb.minus(getExpression(e_leftRef),((RealConstant)e_rightRef).value);
				else
					return pb.minus(getExpression(e_leftRef),getExpression(e_rightRef));
			case MUL:
				if (e_leftRef instanceof RealConstant && e_rightRef instanceof RealConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof RealConstant)
					return pb.mult(((RealConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof RealConstant)
					return pb.mult(((RealConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.mult(getExpression(e_leftRef),getExpression(e_rightRef));
			case DIV:
				if (e_leftRef instanceof RealConstant && e_rightRef instanceof RealConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof RealConstant)
					return pb.div(((RealConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof RealConstant)
					return pb.div(getExpression(e_leftRef),((RealConstant)e_rightRef).value);
				else
					return pb.div(getExpression(e_leftRef),getExpression(e_rightRef));
			case AND:
				if (e_leftRef instanceof RealConstant && e_rightRef instanceof RealConstant)
					throw new RuntimeException("## Error: this is not a symbolic expression"); //
				else if (e_leftRef instanceof RealConstant)
					return pb.and(((RealConstant)e_leftRef).value,getExpression(e_rightRef));
				else if (e_rightRef instanceof RealConstant)
					return pb.and(((RealConstant)e_rightRef).value,getExpression(e_leftRef));
				else
					return pb.and(getExpression(e_leftRef),getExpression(e_rightRef));

			default:
				throw new RuntimeException("## Error: Expression " + eRef);
			}
		}

		if(eRef instanceof MathRealExpression) {
			MathFunction funRef;
			RealExpression	e_arg1Ref;
			RealExpression	e_arg2Ref;

			funRef = ((MathRealExpression)eRef).op;
			e_arg1Ref = ((MathRealExpression)eRef).arg1;
			e_arg2Ref = ((MathRealExpression)eRef).arg2;
			switch(funRef){
			case SIN: return pb.sin(getExpression(e_arg1Ref));
			case COS: return pb.cos(getExpression(e_arg1Ref));
			case EXP: return pb.exp(getExpression(e_arg1Ref));
			case ASIN: return pb.asin(getExpression(e_arg1Ref));
			case ACOS:return pb.acos(getExpression(e_arg1Ref));
			case ATAN: return pb.atan(getExpression(e_arg1Ref));
			case LOG:return pb.log(getExpression(e_arg1Ref));
			case TAN:return pb.tan(getExpression(e_arg1Ref));
			case SQRT:return pb.sqrt(getExpression(e_arg1Ref));
			case POW:
				if (e_arg2Ref instanceof RealConstant)
					return pb.power(getExpression(e_arg1Ref),((RealConstant)e_arg2Ref).value);
				else if (e_arg1Ref instanceof RealConstant)
					return pb.power(((RealConstant)e_arg1Ref).value,getExpression(e_arg2Ref));
				else
					return pb.power(getExpression(e_arg1Ref),getExpression(e_arg2Ref));
			case ATAN2:
				if (e_arg2Ref instanceof RealConstant)
					return pb.atan2(getExpression(e_arg1Ref),((RealConstant)e_arg2Ref).value);
				else if (e_arg1Ref instanceof RealConstant)
					return pb.atan2(((RealConstant)e_arg1Ref).value,getExpression(e_arg2Ref));
				else
					return pb.atan2(getExpression(e_arg1Ref),getExpression(e_arg2Ref));
			default:
				throw new RuntimeException("## Error: Expression " + eRef);
			}
		}

		throw new RuntimeException("## Error: Expression " + eRef);
	}

	//public Map<SymbolicReal, Object> getSymRealVar() {
		//return symRealVar;
	//}


	//public Map<SymbolicInteger, Object> getSymIntegerVar() {
		//return symIntegerVar;
	//}


	static public boolean createDPMixedConstraint(MixedConstraint cRef) { // TODO

		Comparator c_compRef = cRef.getComparator();
		RealExpression c_leftRef = (RealExpression)cRef.getLeft();
		IntegerExpression c_rightRef = (IntegerExpression)cRef.getRight();
		assert (c_compRef == Comparator.EQ);

		if (c_leftRef instanceof SymbolicReal && c_rightRef instanceof SymbolicInteger) {
			//pb.post(new MixedEqXY((RealVar)(getExpression(c_leftRef)),(IntDomainVar)(getExpression(c_rightRef))));
			pb.post(pb.mixed(getExpression(c_leftRef),getExpression(c_rightRef)));
		}
		else if (c_leftRef instanceof SymbolicReal) { // c_rightRef is an IntegerExpression
			Object tmpi = pb.makeIntVar(c_rightRef + "_" + c_rightRef.hashCode(),(int)(((SymbolicReal)c_leftRef)._min), (int)(((SymbolicReal)c_leftRef)._max));
			if (c_rightRef instanceof IntegerConstant)
				pb.post(pb.eq(((IntegerConstant)c_rightRef).value,tmpi));
			else
				pb.post(pb.eq(getExpression(c_rightRef),tmpi));
		    //pb.post(new MixedEqXY((RealVar)(getExpression(c_leftRef)),tmpi));
			pb.post(pb.mixed(getExpression(c_leftRef),tmpi));

		}
		else if (c_rightRef instanceof SymbolicInteger) { // c_leftRef is a RealExpression
			Object tmpr = pb.makeRealVar(c_leftRef + "_" + c_leftRef.hashCode(), ((SymbolicInteger)c_rightRef)._min, ((SymbolicInteger)c_rightRef)._max);
			if(c_leftRef instanceof RealConstant)
				pb.post(pb.eq(tmpr, ((RealConstant)c_leftRef).value));
			else
				pb.post(pb.eq(tmpr, getExpression(c_leftRef)));
		    //pb.post(new MixedEqXY(tmpr,(IntDomainVar)(getExpression(c_rightRef))));
			pb.post(pb.mixed(tmpr,getExpression(c_rightRef)));
		}
		else
			assert(false); // should not be reachable

		return true;
	}

	static public boolean createDPRealConstraint(RealConstraint cRef) {

		Comparator c_compRef = cRef.getComparator();
		RealExpression c_leftRef = (RealExpression)cRef.getLeft();
		RealExpression c_rightRef = (RealExpression)cRef.getRight();

		switch(c_compRef){
		case EQ:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value == ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.eq(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.eq(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.eq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case NE:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value != ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.neq(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.neq(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.neq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LT:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value < ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.lt(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.lt(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.lt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GE:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value >= ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.geq(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.geq(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.geq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LE:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value <= ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.leq(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.leq(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.leq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GT:
			if (c_leftRef instanceof RealConstant && c_rightRef instanceof RealConstant) {
				if (!(((RealConstant) c_leftRef).value > ((RealConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof RealConstant) {
				pb.post(pb.gt(((RealConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof RealConstant) {
				pb.post(pb.gt(getExpression(c_leftRef),((RealConstant)c_rightRef).value));
			}
			else
				pb.post(pb.gt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		}
		return true;
	}

	//Added by Gideon, to handle CNF style constraints
	static public boolean createDPLinearOrIntegerConstraint (LogicalORLinearIntegerConstraints c) {
		List<Object> orList = new ArrayList<Object>();

		for (LinearIntegerConstraint cRef: c.getList()) {
			Comparator c_compRef = cRef.getComparator();
			IntegerExpression c_leftRef = (IntegerExpression)cRef.getLeft();
			IntegerExpression c_rightRef = (IntegerExpression)cRef.getRight();
			//Removed all return false
			switch(c_compRef){
			case EQ:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value == ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.eq(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.eq(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.eq(tempVar1,tempVar2));
				}
				break;
			case NE:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value != ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.neq(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.neq(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.neq(tempVar1,tempVar2));
				}
				break;
			case LT:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value < ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.lt(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.lt(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.lt(tempVar1,tempVar2));
				}
				break;
			case GE:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value >= ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.geq(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.geq(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.geq(tempVar1,tempVar2));
				}
				break;
			case LE:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value <= ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.leq(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.leq(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.leq(tempVar1,tempVar2));
				}
				break;
			case GT:
				if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
					if (!(((IntegerConstant) c_leftRef).value > ((IntegerConstant) c_rightRef).value)) {
						//return false;
					}
					else
						return true;
				}
				else if (c_leftRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_rightRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar, part1));
					Object cc = pb.gt(((IntegerConstant)c_leftRef).value, tempVar);
					orList.add(cc);
				}
				else if (c_rightRef instanceof IntegerConstant) {
					Object part1 = getExpression(c_leftRef);
					Object tempVar = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt(""));
					pb.post(pb.eq(tempVar, part1)); tempVars++;
					orList.add(pb.gt(tempVar,((IntegerConstant)c_rightRef).value));
				}
				else {
					Object part1 = getExpression(c_leftRef);
					Object part2 = getExpression(c_rightRef);
					Object tempVar1 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					Object tempVar2 = pb.makeIntVar("mytemp" + tempVars, MinMax.getVarMinInt(""), MinMax.getVarMaxInt("")); tempVars++;
					pb.post(pb.eq(tempVar1, part1));
					pb.post(pb.eq(tempVar2, part2));
					orList.add(pb.gt(tempVar1,tempVar2));
				}
				break;
			}
		}
		//System.out.println("[SymbolicConstraintsGeneral] orList: " + orList.toString());
		if (orList.size() == 0) return true;
		Object constraint_array[] = new Object[orList.size()];
		orList.toArray(constraint_array);

		pb.postLogicalOR(constraint_array);

		return true;

	}

	static public boolean createDPLinearIntegerConstraint(LinearIntegerConstraint cRef) {

		Comparator c_compRef = cRef.getComparator();

		IntegerExpression c_leftRef = (IntegerExpression)cRef.getLeft();
		IntegerExpression c_rightRef = (IntegerExpression)cRef.getRight();

		switch(c_compRef){
		case EQ:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value == ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.eq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.eq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.eq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case NE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value != ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.neq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.neq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.neq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value < ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.lt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.lt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.lt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value >= ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.geq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.geq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.geq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value <= ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.leq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.leq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.leq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value > ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.gt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.gt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.gt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		}
		return true;
	}

	static public boolean createDPNonLinearIntegerConstraint(NonLinearIntegerConstraint cRef) {

		Comparator c_compRef = cRef.getComparator();

		IntegerExpression c_leftRef = (IntegerExpression)cRef.getLeft();
		IntegerExpression c_rightRef = (IntegerExpression)cRef.getRight();

		switch(c_compRef){
		case EQ:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value == ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.eq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.eq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.eq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case NE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value != ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.neq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.neq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.neq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value < ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.lt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.lt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.lt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value >= ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.geq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.geq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.geq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case LE:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value <= ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.leq(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.leq(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.leq(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		case GT:
			if (c_leftRef instanceof IntegerConstant && c_rightRef instanceof IntegerConstant) {
				if (!(((IntegerConstant) c_leftRef).value > ((IntegerConstant) c_rightRef).value))
					return false;
				else
					return true;
			}
			else if (c_leftRef instanceof IntegerConstant) {
				pb.post(pb.gt(((IntegerConstant)c_leftRef).value,getExpression(c_rightRef)));
			}
			else if (c_rightRef instanceof IntegerConstant) {
				pb.post(pb.gt(getExpression(c_leftRef),((IntegerConstant)c_rightRef).value));
			}
			else
				pb.post(pb.gt(getExpression(c_leftRef),getExpression(c_rightRef)));
			break;
		}
		return true;
	}
	//static Map<String,Boolean> dpMap = new HashMap<String,Boolean>();

	// result is in pb
	public static ProblemGeneral parse(PathCondition pc, ProblemGeneral pbtosolve) {
		pb=pbtosolve;
		if (pc == null || pc.count == 0) {
			if (SymbolicInstructionFactory.debugMode)
				System.out.println("## Warning: empty path condition");
			return null;
		}
		
		symRealVar = new HashMap<SymbolicReal,Object>();
		symIntegerVar = new HashMap<SymbolicInteger,Object>();
		//result = null;
		tempVars = 0;
		
		Constraint cRef = pc.header;

		while (cRef != null) {
			boolean constraintResult = true;

			if (cRef instanceof RealConstraint)
				constraintResult= createDPRealConstraint((RealConstraint)cRef);// create choco real constraint
			else if (cRef instanceof LinearIntegerConstraint)
				constraintResult= createDPLinearIntegerConstraint((LinearIntegerConstraint)cRef);// create choco linear integer constraint
			else if (cRef instanceof MixedConstraint)
				// System.out.println("Mixed Constraint");
				constraintResult= createDPMixedConstraint((MixedConstraint)cRef);
			else if (cRef instanceof LogicalORLinearIntegerConstraints) {
//				if (!(pb instanceof ProblemChoco)) {
//					throw new RuntimeException ("String solving only works with Choco for now");
//				}
				//System.out.println("[SymbolicConstraintsGeneral] reached");
				constraintResult= createDPLinearOrIntegerConstraint((LogicalORLinearIntegerConstraints)cRef);

			}
			else {
				System.out.println("## Warning: Non Linear Integer Constraint (only coral can handle it)" + cRef);
				if(pb instanceof ProblemCoral)
					constraintResult= createDPNonLinearIntegerConstraint((NonLinearIntegerConstraint)cRef);
				else
					throw new RuntimeException("## Error: Non Linear Integer Constraint not handled " + cRef);
			}

			cRef = cRef.and;
		}
		 
         return pb;

		
	}
}