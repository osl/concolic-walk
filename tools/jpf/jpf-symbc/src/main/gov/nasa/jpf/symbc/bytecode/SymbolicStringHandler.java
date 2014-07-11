/*  Copyright (C) 2005 United States Government as represented by the
Administrator of the National Aeronautics and Space Administration
(NASA).  All Rights Reserved.

Copyright (C) 2009 Fujitsu Laboratories of America, Inc.

DISCLAIMER OF WARRANTIES AND LIABILITIES; WAIVER AND INDEMNIFICATION

A. No Warranty: THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY
WARRANTY OF ANY KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY,
INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE
WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR FREEDOM FROM
INFRINGEMENT, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL BE ERROR
FREE, OR ANY WARRANTY THAT DOCUMENTATION, IF PROVIDED, WILL CONFORM TO
THE SUBJECT SOFTWARE. NO SUPPORT IS WARRANTED TO BE PROVIDED AS IT IS PROVIDED "AS-IS".

B. Waiver and Indemnity: RECIPIENT AGREES TO WAIVE ANY AND ALL CLAIMS
AGAINST FUJITSU LABORATORIES OF AMERICA AND ANY OF ITS AFFILIATES, THE
UNITED STATES GOVERNMENT, ITS CONTRACTORS AND SUBCONTRACTORS, AS WELL
AS ANY PRIOR RECIPIENT.  IF RECIPIENT'S USE OF THE SUBJECT SOFTWARE
RESULTS IN ANY LIABILITIES, DEMANDS, DAMAGES, EXPENSES OR LOSSES ARISING
FROM SUCH USE, INCLUDING ANY DAMAGES FROM PRODUCTS BASED ON, OR RESULTING
FROM, RECIPIENT'S USE OF THE SUBJECT SOFTWARE, RECIPIENT SHALL INDEMNIFY
AND HOLD HARMLESS FUJITSU LABORATORTIES OF AMERICA AND ANY OF ITS AFFILIATES,
THE UNITED STATES GOVERNMENT, ITS CONTRACTORS AND SUBCONTRACTORS, AS WELL
AS ANY PRIOR RECIPIENT, TO THE EXTENT PERMITTED BY LAW.  RECIPIENT'S SOLE
REMEDY FOR ANY SUCH MATTER SHALL BE THE IMMEDIATE, UNILATERAL
TERMINATION OF THIS AGREEMENT. */

// TODO: to review
// probably needs to be redone with env methods

package gov.nasa.jpf.symbc.bytecode;



import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.ClassLoaderInfo;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.SystemState;
import gov.nasa.jpf.vm.Types;
import gov.nasa.jpf.vm.VM;

import gov.nasa.jpf.vm.StackFrame;

import gov.nasa.jpf.vm.ThreadInfo;

import gov.nasa.jpf.jvm.bytecode.InvokeInstruction;
import gov.nasa.jpf.symbc.mixednumstrg.SpecialRealExpression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.RealExpression;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;


import gov.nasa.jpf.symbc.string.*;
import gov.nasa.jpf.symbc.mixednumstrg.*;


// Corina: this code is strange; I need to revise it carefully
public class SymbolicStringHandler {
	static int handlerStep = 0;
	static Instruction handlerStepSavedNext = null;
	static Object handlerStepSavedValue = null;

	public static final int intValueOffset = 5;

	/* this method checks if a method has as argument any symbolic strings */

	public boolean isMethodStringSymbolic(InvokeInstruction invInst, ThreadInfo th) {
		String cname = invInst.getInvokedMethodClassName();

		if (cname.equals("java.lang.String")
				|| cname.equals("java.lang.StringBuilder")
				|| cname.equals("java.lang.StringBuffer")
				|| cname.equals("java.lang.CharSequence")
				|| cname.equals("java.lang.Appendable")
				|| cname.equals("java.io.PrintStream")
				|| cname.equals("java.lang.Integer")
				|| cname.equals("java.lang.Float")
				|| cname.equals("java.lang.Double")
				|| cname.equals("java.lang.Long")
				|| cname.equals("java.lang.Short")
				|| cname.equals("java.lang.Byte")
				|| cname.equals("java.lang.Char")
				|| cname.equals("java.lang.Boolean")
				|| cname.equals("java.lang.Object")) {
	StackFrame sf = th.getModifiableTopFrame();

			int numStackSlots = invInst.getArgSize();

			for (int i = 0; i < numStackSlots; i++) {
				Expression sym_v1 = (Expression) sf.getOperandAttr(i);
				if (sym_v1 != null) {

					if (sym_v1 instanceof SymbolicStringBuilder) { // check if
						// StringBuilder has
						// empty attribute
						if (((SymbolicStringBuilder) sym_v1).getstr() != null)
							return true;
					}  else if (sym_v1 instanceof SymbolicInteger && cname.equals("java.lang.StringBuilder")){
						//concrete stringbuffers won't be handled here
						return false;
					} else {
						return true;
					}
				}
			}
			return false;
		} else
			return false;
	}

	public Instruction handleSymbolicStrings(InvokeInstruction invInst, ThreadInfo th) {

		boolean needToHandle = isMethodStringSymbolic(invInst, th);

		if (needToHandle) {
			// do the string manipulations
			String mname = invInst.getInvokedMethodName();
			String shortName = mname.substring(0, mname.indexOf("("));
			if (shortName.equals("concat")) {
				Instruction handled = handleConcat(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("equals")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleObjectEquals(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("equalsIgnoreCase")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleEqualsIgnoreCase(invInst,  th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("endsWith")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleEndsWith(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("startsWith")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleStartsWith(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals ("contains")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleContains(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("append")) {
				Instruction handled = handleAppend(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("length")) {
				handleLength(invInst, th);
			} else if (shortName.equals("indexOf")) {
				handleIndexOf(invInst, th);
			} else if (shortName.equals("lastIndexOf")) {
				handleLastIndexOf(invInst, th);
			} else if (shortName.equals("charAt")) {
				handleCharAt (invInst, th);
				//return invInst;
			} else if (shortName.equals("replace")) {
				Instruction handled = handleReplace(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("replaceFirst")) {
				Instruction handled = handleReplaceFirst(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("trim")) {
				handleTrim(invInst, th);
			} else if (shortName.equals("substring")) {
				Instruction handled = handleSubString(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("valueOf")) {
				Instruction handled = handleValueOf(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("parseInt")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleParseInt(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("parseFloat")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleParseFloat(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("parseLong")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleParseLong(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("parseDouble")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleParseDouble(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("parseBoolean")) {
				ChoiceGenerator<?> cg;
				if (!th.isFirstStepInsn()) { // first time around
					cg = new PCChoiceGenerator(2);
					th.getVM().setNextChoiceGenerator(cg);
					return invInst;
				} else {
					handleParseBoolean(invInst, th);
					return invInst.getNext(th);
				}
			} else if (shortName.equals("toString")) {
				Instruction handled = handletoString(invInst, th);
				if (handled != null) {
					return handled;
				}
			} else if (shortName.equals("println")) {
				handleprintln(invInst, th, true);
			} else if (shortName.equals("print")) {
				handleprintln(invInst, th, false);
			} else if (shortName.equals("<init>")) {
				Instruction handled = handleInit(invInst, th);
				if (handled != null) {
					return handled;
				} else {
					return null;
				}
			} else if (shortName.equals("intValue")) {
				handleintValue(invInst, th);
			} else if (shortName.equals("floatValue")) {
				handlefloatValue(invInst, th);
			} else if (shortName.equals("longValue")) {
				handlelongValue(invInst, th);
			} else if (shortName.equals("doubleValue")) {
				handledoubleValue(invInst, th);
			} else if (shortName.equals("booleanValue")) {
				handlefloatValue(invInst, th);
			} else {
				System.err.println("ERROR: symbolic method not handled: " + shortName);
				return null;
			}
			return invInst.getNext(th);
		} else {
			return null;
		}

	}

	private boolean handleCharAt (InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);
		boolean bresult = false;
		if ((sym_v1 == null) & (sym_v2 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleSubString1");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();

			IntegerExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete

				int val = s1;
				//System.out.println("[handleCharAt] Mmm...! " + val);
				result = sym_v2._charAt(new IntegerConstant(val));
			} else {
				//System.out.println("[handleCharAt] YES! " + sym_v1.getClass() + " " + sym_v1.toString());


				if (sym_v2 == null) {
					ElementInfo e1 = th.getElementInfo(s2);
					String val2 = e1.asString();
					sym_v2 = new StringConstant(val2);
					result = sym_v2._charAt(sym_v1);
				} else {
					result = sym_v2._charAt(sym_v1);
				}
				bresult = true;
				//System.out.println("[handleCharAt] Ignoring: " + result.toString());
				//th.push(0, false);
			}
			//th.push(objRef, true);
			sf.push(0, false);
			sf.setOperandAttr(result);
		}
		return bresult;

	}

	public void handleLength(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {
			sf.pop();
			sf.push(0, false); /* dont care value for length */
			IntegerExpression sym_v2 = sym_v1._length();
			sf.setOperandAttr(sym_v2);
		}

	}

	public void handleIndexOf(InvokeInstruction invInst, ThreadInfo th) {
		int numStackSlots = invInst.getArgSize();
		if (numStackSlots == 2) {
			handleIndexOf1(invInst, th);
		} else {
			handleIndexOf2(invInst, th);
		}

	}

	/* two possibilities int, or String in parameter */
	/* currently symbolic values in parameters are ignored */
	public void handleIndexOf1(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		/* Added by Gideon */
		//StringExpression argument = (StringExpression) sf.getOperandAttr(0);
		//boolean castException = false;
		StringExpression sym_v1 = null;
		StringExpression sym_v2 = null;
		sym_v1 = (StringExpression) sf.getOperandAttr(1);
		/*	*/
		sym_v2 = (StringExpression) sf.getOperandAttr(0);
		if (sym_v1 == null && sym_v2 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {
			/*int x1 = th.pop();
			int x2 = th.pop();
			System.out.printf("[SymbolicStringHandler] [handleIndexOf1] %d %d\n", x1, x2);
			th.push(0, false);
			IntegerExpression sym_v2 = sym_v1._indexOf();
			sf.setOperandAttr(sym_v2);*/
			// System.out.println("conditionValue: " + conditionValue);


			boolean s1char = true; //argument is char
			if (sf.isOperandRef()) {
				s1char = false; //argument is string
			}
			int s1 = sf.pop();
			int s2 = sf.pop();

			IntegerExpression result = null;
			//if (conditionValue) {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._indexOf(sym_v2);
					} else {
						if (s1char) {
							result = sym_v1._indexOf(new IntegerConstant(s1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s1);
							String val = e2.asString();
							result = sym_v1._indexOf(new StringConstant(val));
						}
					}
					//pc._addDet(Comparator.EQ, result, -1);
				} else {
					ElementInfo e1 = th.getElementInfo(s2);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._indexOf(sym_v2);
					} else {
						if (s1char) {
							result = new StringConstant(val)._indexOf(new IntegerConstant(s1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s1);
							String val2 = e2.asString();
							result = new StringConstant(val)._indexOf(new StringConstant(val2));
						}
					}
					//pc.spc._addDet(comp, val, sym_v2);
				}
			/*} else {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._indexOf(sym_v2);
					} else {
						ElementInfo e2 = th.getElementInfo(s1);
						String val = e2.asString();
						result = sym_v1._indexOf(new StringConstant(val));
					}
					//pc._addDet(Comparator.GE, result, 0);
				} else {
					ElementInfo e1 = th.getElementInfo(s2);
					String val = e1.asString();
					throw new RuntimeException("Not supported yet");
					//pc.spc._addDet(comp, val, sym_v2);
				}
			}*/
			sf.push(0, false);
			sf.setOperandAttr(result);
			/*if (!pc.simplify()) {// not satisfiable
				System.out.println("Not sat");
				ss.setIgnored(true);
			} else {
				System.out.println("Is sat");
				((PCChoiceGenerator) cg).setCurrentPC(pc);
			}*/


			//assert result != null;
			//th.push(conditionValue ? 1 : 0, true);


		}
	}

	public void handleLastIndexOf(InvokeInstruction invInst, ThreadInfo th) {
		int numStackSlots = invInst.getArgSize();
		if (numStackSlots == 2) {
			handleLastIndexOf1(invInst, th);
		} else {
			handleLastIndexOf2(invInst, th);
		}
	}

	public void handleLastIndexOf1(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		/* Added by Gideon */
		//StringExpression argument = (StringExpression) sf.getOperandAttr(0);
		//boolean castException = false;
		StringExpression sym_v1 = null;
		StringExpression sym_v2 = null;
		sym_v1 = (StringExpression) sf.getOperandAttr(1);
		/*	*/
		sym_v2 = (StringExpression) sf.getOperandAttr(0);
		if (sym_v1 == null && sym_v2 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {
			/*int x1 = th.pop();
			int x2 = th.pop();
			System.out.printf("[SymbolicStringHandler] [handleIndexOf1] %d %d\n", x1, x2);
			th.push(0, false);
			IntegerExpression sym_v2 = sym_v1._indexOf();
			sf.setOperandAttr(sym_v2);*/
			// System.out.println("conditionValue: " + conditionValue);


			boolean s1char = true; //argument is char
			if (sf.isOperandRef()) {
				s1char = false; //argument is string
			}
			int s1 = sf.pop();
			int s2 = sf.pop();

			IntegerExpression result = null;
			//if (conditionValue) {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._lastIndexOf(sym_v2);
					} else {
						if (s1char) {
							result = sym_v1._lastIndexOf(new IntegerConstant(s1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s1);
							String val = e2.asString();
							result = sym_v1._lastIndexOf(new StringConstant(val));
						}
					}
					//pc._addDet(Comparator.EQ, result, -1);
				} else {
					ElementInfo e1 = th.getElementInfo(s2);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._lastIndexOf(sym_v2);
					} else {
						if (s1char) {
							result = new StringConstant(val)._lastIndexOf(new IntegerConstant(s1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s1);
							String val2 = e2.asString();
							result = new StringConstant(val)._lastIndexOf(new StringConstant(val2));
						}
					}
					//pc.spc._addDet(comp, val, sym_v2);
				}
			/*} else {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._indexOf(sym_v2);
					} else {
						ElementInfo e2 = th.getElementInfo(s1);
						String val = e2.asString();
						result = sym_v1._indexOf(new StringConstant(val));
					}
					//pc._addDet(Comparator.GE, result, 0);
				} else {
					ElementInfo e1 = th.getElementInfo(s2);
					String val = e1.asString();
					throw new RuntimeException("Not supported yet");
					//pc.spc._addDet(comp, val, sym_v2);
				}
			}*/
			sf.push(0, false);
			sf.setOperandAttr(result);
			/*if (!pc.simplify()) {// not satisfiable
				System.out.println("Not sat");
				ss.setIgnored(true);
			} else {
				System.out.println("Is sat");
				((PCChoiceGenerator) cg).setCurrentPC(pc);
			}*/


			//assert result != null;
			//th.push(conditionValue ? 1 : 0, true);


		}
	}

	public void handleLastIndexOf2(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();

		StringExpression sym_v1 = null;
		StringExpression sym_v2 = null;
		IntegerExpression intExp = null;
		sym_v1 = (StringExpression) sf.getOperandAttr(2);
		intExp = (IntegerExpression) sf.getOperandAttr(0);
		sym_v2 = (StringExpression) sf.getOperandAttr(1);

		if (sym_v1 == null && sym_v2 == null && intExp == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {
			int i1 = sf.pop();
			boolean s2char = true;
			if (th.getModifiableTopFrame().isOperandRef()) {
				//System.out.println("[handleIndexOf2] string detected");
				s2char = false;
			}
			else {
				//System.out.println("[handleIndexOf2] char detected");
			}
			int s2 = sf.pop();
			int s1 = sf.pop();

			IntegerExpression result = null;
			if (intExp != null) {
				//System.out.println("[handleIndexOf2] int exp: " + intExp.getClass());
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._lastIndexOf(sym_v2, intExp);
					} else {
						if (s2char) {
							result = sym_v1._lastIndexOf(new IntegerConstant(s2), intExp);
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val = e2.asString();
							result = sym_v1._lastIndexOf(new StringConstant(val), intExp);
						}
					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._lastIndexOf(sym_v2, intExp);
					} else {
						if (s2char) {
							result = new StringConstant(val)._lastIndexOf(new IntegerConstant(s2), intExp);
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val2 = e2.asString();
							result = new StringConstant(val)._lastIndexOf(new StringConstant(val2), intExp);
						}
					}
				}
			}
			else {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._lastIndexOf(sym_v2, new IntegerConstant(i1));
					} else {
						if (s2char) {
							result = sym_v1._lastIndexOf(new IntegerConstant(s2), new IntegerConstant(i1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val = e2.asString();
							result = sym_v1._lastIndexOf(new StringConstant(val), new IntegerConstant(i1));
							//System.out.println("[handleIndexOf2] Special push");
							//Special push?
							//th.push(s1, true);
						}
					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._lastIndexOf(sym_v2, new IntegerConstant(i1));
					} else {
						if (s2char) {
							result = new StringConstant(val)._lastIndexOf(new IntegerConstant(s2), new IntegerConstant(i1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val2 = e2.asString();
							result = new StringConstant(val)._lastIndexOf(new StringConstant(val2), new IntegerConstant(i1));
						}
					}
				}
			}
			/* Not quite sure yet why this works */
			//int objRef = th.getVM().getDynamicArea().newString("", th);
			//th.push(objRef, true);
			sf.push(0, false);
			assert result != null;
			sf.setOperandAttr(result);

		}
	}


	/* two possibilities int, int or int, String in parameters */
	/* currently symbolic values in parameters are ignored */
	public void handleIndexOf2(InvokeInstruction invInst, ThreadInfo th) {
		//This was the Fjitsu way
		/*
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(2);
		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {
			th.pop();
			th.pop();
			th.pop();
			th.push(0, false);
			//IntegerExpression sym_v2 = sym_v1._indexOf();
			sf.setOperandAttr(new IntegerConstant(1));
		}
		 */

		//My way
		StackFrame sf = th.getModifiableTopFrame();

		StringExpression sym_v1 = null;
		StringExpression sym_v2 = null;
		IntegerExpression intExp = null;
		/*System.out.print("[handleIndexOf2] arguments: ");
		if (sf.getOperandAttr(0) == null) {System.out.print("null");} else {System.out.print(sf.getOperandAttr(0).toString());}
		System.out.print(" ");
		if (sf.getOperandAttr(1) == null) {System.out.print("null");} else {System.out.print(sf.getOperandAttr(1).toString());}
		System.out.print(" ");
		if (sf.getOperandAttr(2) == null) {System.out.print("null");} else {System.out.print(sf.getOperandAttr(2).toString());}
		System.out.println();*/
		sym_v1 = (StringExpression) sf.getOperandAttr(2);
		intExp = (IntegerExpression) sf.getOperandAttr(0);
		sym_v2 = (StringExpression) sf.getOperandAttr(1);

		if (sym_v1 == null && sym_v2 == null && intExp == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLength");
		} else {


			int i1 = sf.pop();
			boolean s2char = true;
			if (sf.isOperandRef()) {
				//System.out.println("[handleIndexOf2] string detected");
				s2char = false;
			}
			else {
				//System.out.println("[handleIndexOf2] char detected");
			}
			int s2 = sf.pop();
			int s1 = sf.pop();

			IntegerExpression result = null;
			if (intExp != null) {
				//System.out.println("[handleIndexOf2] int exp: " + intExp.getClass());
				if (intExp instanceof SymbolicIndexOf2Integer) {
					SymbolicIndexOf2Integer temp = (SymbolicIndexOf2Integer) intExp;
					//System.out.println("[handleIndexOf2] further on: " + temp.getMinIndex().getClass());
				}
				else if (intExp instanceof SymbolicIndexOfChar2Integer) {
					SymbolicIndexOfChar2Integer temp = (SymbolicIndexOfChar2Integer) intExp;
					//System.out.println("[handleIndexOf2] further on: " + temp.getMinDist().getClass());
				}
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._indexOf(sym_v2, intExp);
					} else {
						if (s2char) {
							result = sym_v1._indexOf(new IntegerConstant(s2), intExp);
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val = e2.asString();
							result = sym_v1._indexOf(new StringConstant(val), intExp);
						}
					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._indexOf(sym_v2, intExp);
					} else {
						if (s2char) {
							result = new StringConstant(val)._indexOf(new IntegerConstant(s2), intExp);
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val2 = e2.asString();
							result = new StringConstant(val)._indexOf(new StringConstant(val2), intExp);
						}
					}
				}
			}
			else {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						result = sym_v1._indexOf(sym_v2, new IntegerConstant(i1));
					} else {
						if (s2char) {
							result = sym_v1._indexOf(new IntegerConstant(s2), new IntegerConstant(i1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val = e2.asString();
							result = sym_v1._indexOf(new StringConstant(val), new IntegerConstant(i1));
							//System.out.println("[handleIndexOf2] Special push");
							//Special push?
							//th.push(s1, true);
						}
					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();

					if (sym_v2 != null) { // both are symbolic values
						result = new StringConstant(val)._indexOf(sym_v2, new IntegerConstant(i1));
					} else {
						if (s2char) {
							result = new StringConstant(val)._indexOf(new IntegerConstant(s2), new IntegerConstant(i1));
						}
						else {
							ElementInfo e2 = th.getElementInfo(s2);
							String val2 = e2.asString();
							result = new StringConstant(val)._indexOf(new StringConstant(val2), new IntegerConstant(i1));
						}
					}
				}
			}
			/* Not quite sure yet why this works */
			//int objRef = th.getVM().getDynamicArea().newString("", th);
			//th.push(objRef, true);
			sf.push(0, false);
			assert result != null;
			sf.setOperandAttr(result);

		}
	}

	public void handlebooleanValue(InvokeInstruction invInst, SystemState ss, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: handlebooleanValue");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.pop();
				sf.push(0, false);
				sf.setOperandAttr(sym_v2);
			} else {
				System.err.println("ERROR: operand type not tackled - booleanValue");
			}

		}

	}

	public void handleintValue(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: handleintValue");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.pop();
				sf.push(0, false);
				sf.setOperandAttr(sym_v2);
			} else {
				th.printStackTrace();
				System.err.println("ERROR: operand type not tackled - intValue");
			}
		}
	}

	public void handlelongValue(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeLongValue");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.pop();
				sf.pushLong((long) 0);
				sf.setLongOperandAttr(sym_v2);
			} else {
				System.err.println("ERROR: operand type not tackled - longValue");
			}

		}

	}

	public void handlefloatValue(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeFloatValue");
		} else {
			if (sym_v3 instanceof RealExpression) {
				RealExpression sym_v2 = (RealExpression) sym_v3;
				sf.pop();
				sf.push(0, false);
				sf.setOperandAttr(sym_v2);
			} else {
				System.err.println("ERROR: operand type not tackled - floatValue");
			}

		}

	}

	public void handledoubleValue(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand: hanldeDoubleValue");
		} else {
			if (sym_v3 instanceof RealExpression) {
				RealExpression sym_v2 = (RealExpression) sym_v3;
				sf.pop();
				sf.pushLong((long) 0);
				sf.setLongOperandAttr(sym_v2);
			} else {
				System.err.println("ERROR: operand type not tackled - doubleValue");
			}

		}

	}

	/*
	 * StringBuilder or StringBuffer or BigDecimal initiation with symbolic
	 * primitives
	 */

	public Instruction handleInit(InvokeInstruction invInst,  ThreadInfo th) {

		String cname = invInst.getInvokedMethodClassName();
		if (cname.equals("java.lang.StringBuilder") || cname.equals("java.lang.StringBuffer")) {
			StackFrame sf = th.getModifiableTopFrame();
			StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
			SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);
			if (sym_v1 == null) {
				System.err.println("ERROR: symbolic StringBuilder method must have one symbolic operand in Init");
			} else {
				sf.pop(); /* string object */
				sf.pop(); /* one stringBuilder Object */
				sym_v2.putstr(sym_v1);
				sf.setOperandAttr(sym_v2);
				return invInst.getNext();
			}
		} else {
			// Corina TODO: we should allow symbolic string analysis to kick in only when desired
			System.err.println("Warning Symbolic String Analysis: Initialization type not handled in symbc/bytecode/SymbolicStringHandler init");
			return null;
		}

		return null;
	}

	/***************************** Symbolic Big Decimal Routines end ****************/


	private void handleBooleanStringInstructions(InvokeInstruction invInst,  ThreadInfo th, StringComparator comp) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);

		if ((sym_v1 == null) & (sym_v2 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleStartsWith");
		} else {
			ChoiceGenerator<?> cg;
			boolean conditionValue;

			cg = th.getVM().getChoiceGenerator();
			assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
			conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

			// System.out.println("conditionValue: " + conditionValue);

			int s1 = sf.pop();
			int s2 = sf.pop();
			PathCondition pc;

			// pc is updated with the pc stored in the choice generator above
			// get the path condition from the
			// previous choice generator of the same type

			ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
			while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
				prev_cg = prev_cg.getPreviousChoiceGenerator();
			}

			if (prev_cg == null) {
				pc = new PathCondition();
			} else {
				pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();
			}

			assert pc != null;

			if (conditionValue) {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						pc.spc._addDet(comp, sym_v1, sym_v2);
					} else {
						ElementInfo e2 = th.getElementInfo(s2);
						String val = e2.asString();
						pc.spc._addDet(comp, sym_v1, val);
					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();
					pc.spc._addDet(comp, val, sym_v2);
				}
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					// pc.solve();
					((PCChoiceGenerator) cg).setCurrentPC(pc);
					// System.out.println(((PCChoiceGenerator) cg).getCurrentPC());
				}
			} else {
				if (sym_v1 != null) {
					if (sym_v2 != null) { // both are symbolic values
						pc.spc._addDet(comp.not(), sym_v1, sym_v2);
					} else {
						ElementInfo e2 = th.getElementInfo(s2);
						String val = e2.asString();
						pc.spc._addDet(comp.not(), sym_v1, val);

					}
				} else {
					ElementInfo e1 = th.getElementInfo(s1);
					String val = e1.asString();
					pc.spc._addDet(comp.not(), val, sym_v2);
				}
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					((PCChoiceGenerator) cg).setCurrentPC(pc);
				}
			}

			sf.push(conditionValue ? 1 : 0, true);

		}

	}

	public void handleEqualsIgnoreCase(InvokeInstruction invInst,  ThreadInfo th) {
		System.err.println("ERROR: symbolic string method not Implemented - EqualsIgnoreCase");
	}

	public void handleEndsWith(InvokeInstruction invInst,  ThreadInfo th) {
		//System.err.println("ERROR: symbolic string method not Implemented - EndsWith");
		handleBooleanStringInstructions(invInst,  th, StringComparator.ENDSWITH);
	}

	public void handleContains (InvokeInstruction invInst,  ThreadInfo th) {
		handleBooleanStringInstructions(invInst,  th, StringComparator.CONTAINS);
	}


	public void handleStartsWith(InvokeInstruction invInst,  ThreadInfo th) {
		//System.err.println("ERROR: symbolic string method not Implemented - StartsWith");
		handleBooleanStringInstructions(invInst, th, StringComparator.STARTSWITH);
	}

	//Only supports character for character
	public Instruction handleReplace(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);
		StringExpression sym_v3 = (StringExpression) sf.getOperandAttr(2);

		if ((sym_v1 == null) & (sym_v2 == null) & (sym_v3 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleReplace");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();
			int s3 = sf.pop();
			//System.out.println("[handleReplace] " + s1 + " " + s2 + " " + s3);
			StringExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete
				//ElementInfo e1 = th.getElementInfo(s1);
				String val = String.valueOf((char) s1);
				if (sym_v2 == null) { // sym_v3 has to be symbolic
					//ElementInfo e2 = th.getElementInfo(s2);
					//String val1 = e2.asString();
					result = sym_v3._replace(val, String.valueOf((char)s2));
				} else {
					if (sym_v3 == null) { // only sym_v2 is symbolic
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replace(val, sym_v2);
					} else {
						result = sym_v3._replace(val, sym_v2);
					}
				}
			} else { // sym_v1 is symbolic
				if (sym_v2 == null) {
					if (sym_v3 == null) {
						//ElementInfo e2 = th.getElementInfo(s2);
						String val1 = String.valueOf((char) s2);
						//ElementInfo e3 = th.getElementInfo(s3);
						String val2 = String.valueOf((char) s3);
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replace(sym_v1, val1);
					} else {
						//ElementInfo e2 = th.getElementInfo(s2);
						String val1 = String.valueOf((char) s2);
						result = sym_v3._replace(sym_v1, val1);
					}
				} else {
					if (sym_v3 == null) {
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replace(sym_v1, sym_v2);
					} else {
						result = sym_v3._replace(sym_v1, sym_v2);
					}
				}
			}
			ElementInfo objRef = th.getHeap().newString("", th); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef.getObjectRef(), true);
			sf.setOperandAttr(result);
		}
		return null;
	}

	public Instruction handleSubString(InvokeInstruction invInst, ThreadInfo th) {
		int numStackSlots = invInst.getArgSize();
		if (numStackSlots == 2) {
			return handleSubString1(invInst, th);
		} else {
			return handleSubString2(invInst, th);
		}
	}

	public Instruction handleSubString1(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);

		if ((sym_v1 == null) & (sym_v2 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleSubString1");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();

			StringExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete
				int val = s1;
				result = sym_v2._subString(val);
			} else {
				if (sym_v2 == null) {
					ElementInfo e1 = th.getElementInfo(s2);
					String val2 = e1.asString();
					sym_v2 = new StringConstant(val2);
					result = sym_v2._subString(sym_v1);
				} else {
					result = sym_v2._subString(sym_v1);
				}
			}
			ElementInfo objRef = th.getHeap().newString("", th); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef.getObjectRef(), true);
			sf.setOperandAttr(result);
		}
		return null;
	}

	public Instruction handleSubString2(InvokeInstruction invInst, ThreadInfo th) {
		//System.out.println("[SymbolicStringHandler] doing");
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		IntegerExpression sym_v2 = (IntegerExpression) sf.getOperandAttr(1);
		StringExpression sym_v3 = (StringExpression) sf.getOperandAttr(2);

		if ((sym_v1 == null) & (sym_v2 == null) & (sym_v3 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleSubString2");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();
			int s3 = sf.pop();
			//System.out.printf("[SymbolicStringHandler] popped %d %d %d\n", s1, s2, s3);
			StringExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete
				int val = s1;
				if (sym_v2 == null) { // sym_v3 has to be symbolic
					int val1 = s2;
					result = sym_v3._subString(val, val1);
					//System.out.println("[SymbolicStringHandler] special push");
					/* Only if both arguments are concrete, something else needs
					 * to be pushed?
					 */
					sf.push(s3, true); /* symbolic string element */
				} else {
					if (sym_v3 == null) { // only sym_v2 is symbolic
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._subString(val, sym_v2);
					} else {
						result = sym_v3._subString(val, sym_v2);
					}
				}
			} else { // sym_v1 is symbolic
				if (sym_v2 == null) {
					if (sym_v3 == null) {
						int val1 = s2;
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._subString(sym_v1, val1);
					} else {
						int val1 = s2;
						result = sym_v3._subString(sym_v1, val1);
					}
				} else {
					if (sym_v3 == null) {
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._subString(sym_v1, sym_v2);
					} else {
						result = sym_v3._subString(sym_v1, sym_v2);
					}
				}
			}
			ElementInfo objRef = th.getHeap().newString("", th);
			//System.out.println("[SymbolicStringHandler] " + sf.toString());
			sf.push(objRef.getObjectRef(), true);
			//System.out.println("[SymbolicStringHandler] " + sf.toString());
			sf.setOperandAttr(result);
		}

		return null;
	}

	public Instruction handleReplaceFirst(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);
		StringExpression sym_v3 = (StringExpression) sf.getOperandAttr(2);

		if ((sym_v1 == null) & (sym_v2 == null) & (sym_v3 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HanldeReplaceFirst");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();
			int s3 = sf.pop();

			StringExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete
				ElementInfo e1 = th.getElementInfo(s1);
				String val = e1.asString();
				if (sym_v2 == null) { // sym_v3 has to be symbolic
					ElementInfo e2 = th.getElementInfo(s2);
					String val1 = e2.asString();
					result = sym_v3._replaceFirst(val, val1);

				} else {
					if (sym_v3 == null) { // only sym_v2 is symbolic
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replaceFirst(val, sym_v2);
					} else {
						result = sym_v3._replaceFirst(val, sym_v2);
					}
				}
			} else { // sym_v1 is symbolic
				if (sym_v2 == null) {
					if (sym_v3 == null) {
						ElementInfo e2 = th.getElementInfo(s2);
						String val1 = e2.asString();
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replaceFirst(sym_v1, val1);
					} else {
						ElementInfo e2 = th.getElementInfo(s2);
						String val1 = e2.asString();
						result = sym_v3._replaceFirst(sym_v1, val1);
					}
				} else {
					if (sym_v3 == null) {
						ElementInfo e3 = th.getElementInfo(s3);
						String val2 = e3.asString();
						sym_v3 = new StringConstant(val2);
						result = sym_v3._replaceFirst(sym_v1, sym_v2);
					} else {
						result = sym_v3._replaceFirst(sym_v1, sym_v2);
					}
				}
			}
			ElementInfo objRef = th.getHeap().newString("", th); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef.getObjectRef(), true);
			sf.setOperandAttr(result);
		}
		return null;
	}

	public void handleTrim(InvokeInstruction invInst, ThreadInfo th) {
		// System.err.println("ERROR: symbolic string method not Implemented - Trim");
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		int s1 = sf.pop();

		if (sym_v1 == null) {
			ElementInfo e1 = th.getElementInfo(s1);
			String val1 = e1.asString();
			sym_v1 = new StringConstant(val1);
		}
		StringExpression result = sym_v1._trim();

		ElementInfo  objRef = th.getHeap().newString("", th); /*
																																 * dummy String
																																 * Object
																																 */
		sf.push(objRef.getObjectRef(), true);
		sf.setOperandAttr(result);
	}

	public Instruction handleValueOf(InvokeInstruction invInst,  ThreadInfo th) {
		MethodInfo mi = invInst.getInvokedMethod(th);
		String cname = invInst.getInvokedMethodClassName();
		String[] argTypes = mi.getArgumentTypeNames();
		if (cname.equals("java.lang.String")) {
			// System.out.println(argTypes[0]);
			if (argTypes[0].equals("int")) {
				return handleIntValueOf(invInst,  th);
			} else if (argTypes[0].equals("float")) {
				return handleFloatValueOf(invInst, th);
			} else if (argTypes[0].equals("long")) {
				return handleLongValueOf(invInst, th);
			} else if (argTypes[0].equals("double")) {
				return handleDoubleValueOf(invInst, th);
			} else if (argTypes[0].equals("char")) {
				return handleCharValueOf(invInst, th);
			} else if (argTypes[0].equals("chararray")) {
				return handleCharArrayValueOf(invInst, th);
			} else if (argTypes[0].equals("boolean")) {
				return handleBooleanValueOf(invInst, th);
			} else if (argTypes[0].equals("java.lang.Object")) {
				return handleObjectValueOf(invInst, th);
			} else {
				System.err.println("ERROR: Input parameter type not handled in Symbolic String ValueOf");
			}
		} else { // value of non-string types
			if (cname.equals("java.lang.Integer")) {
				if (!(argTypes[0].equals("int"))) { // converting String to Integer
					ChoiceGenerator<?> cg;
					if (!th.isFirstStepInsn()) { // first time around
						cg = new PCChoiceGenerator(2);
						th.getVM().setNextChoiceGenerator(cg);
						return invInst;
					} else {
						handleParseIntValueOf(invInst, th);
					}
				} else { // converting int to Integer
					handleParseIntValueOf(invInst,  th);
				}
			} else if (cname.equals("java.lang.Float")) {
				if (!(argTypes[0].equals("float"))) { // converting String to Float
					ChoiceGenerator<?> cg;
					if (!th.isFirstStepInsn()) { // first time around
						cg = new PCChoiceGenerator(2);
						th.getVM().setNextChoiceGenerator(cg);
						return invInst;
					} else {
						handleParseFloatValueOf(invInst, th);
					}
				} else { // converting int to Integer
					handleParseFloatValueOf(invInst, th);
				}
			} else if (cname.equals("java.lang.Long")) {
				if (!(argTypes[0].equals("long"))) { // converting String to Long
					ChoiceGenerator<?> cg;
					if (!th.isFirstStepInsn()) { // first time around
						cg = new PCChoiceGenerator(2);
						th.getVM().setNextChoiceGenerator(cg);
						return invInst;
					} else {
						handleParseLongValueOf(invInst, th);
					}
				} else { // converting int to Integer
					handleParseLongValueOf(invInst, th);
				}
			} else if (cname.equals("java.lang.Double")) {
				if (!(argTypes[0].equals("double"))) { // converting String to Double
					ChoiceGenerator<?> cg;
					if (!th.isFirstStepInsn()) { // first time around
						cg = new PCChoiceGenerator(2);
						th.getVM().getSystemState().setNextChoiceGenerator(cg);
						return invInst;
					} else {
						handleParseDoubleValueOf(invInst, th);
					}
				} else { // converting int to Integer
					handleParseLongValueOf(invInst, th);
				}
			} else if (cname.equals("java.lang.Boolean")) {
				if (!(argTypes[0].equals("boolean"))) { // converting String to Boolean
					ChoiceGenerator<?> cg;
					if (!th.isFirstStepInsn()) { // first time around
						cg = new PCChoiceGenerator(2);
						th.getVM().setNextChoiceGenerator(cg);
						return invInst;
					} else {
						handleParseBooleanValueOf(invInst, th);
					}
				} else { // converting int to Integer
					handleParseBooleanValueOf(invInst, th);
				}
			} else {
				System.err.println("ERROR: Type not handled in Symbolic Type ValueOf: " + cname);
			}
		}
		return null;
	}

	public void handleParseLongValueOf(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.popLong();
				int objRef = getNewObjRef(invInst, th); /* dummy Long Object */
				sf.push(objRef, true);
				sf.setOperandAttr(sym_v2);
			} else {
				IntegerExpression result = null;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

				sf.pop();
				PathCondition pc;

				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISLONG, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						result = ((StringExpression) sym_v3)._IvalueOf();
						sf = th.getModifiableTopFrame();
						int objRef = getNewObjRef(invInst, th); /* dummy Long Object */
						sf.push(objRef, true);
						sf.setOperandAttr(result);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTLONG, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Long Format Type Exception");
						th.getVM().getSystemState().setIgnored(true);
						sf.push(0, true);
					}
				}
			}
		}
	}

	public void handleParseBooleanValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.pop();
				int objRef = getNewObjRef(invInst, th); /* dummy Boolean Object */
				sf.push(objRef, true);
				sf.setOperandAttr(sym_v2);
			} else {
				IntegerExpression result = null;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

				sf.pop();
				PathCondition pc;

				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISBOOLEAN, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						result = ((StringExpression) sym_v3)._IvalueOf();
						sf = th.getModifiableTopFrame();
						int objRef = getNewObjRef(invInst, th); /* dummy Boolean Object */
						sf.push(objRef, true);
						sf.setOperandAttr(result);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTBOOLEAN, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Boolean Format Type Exception"); // TODO: to review; there should be no backtracking here
						th.getVM().getSystemState().setIgnored(true);
						sf.push(0, true);
					}
				}
			}
		}
	}

	public void handleParseIntValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				IntegerExpression sym_v2 = (IntegerExpression) sym_v3;
				sf.pop();
				int objRef = getNewObjRef(invInst, th); /* dummy Integer Object */
				sf.push(objRef, true);
				sf.setOperandAttr(sym_v2);
			} else {
				IntegerExpression result = null;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

				sf.pop();
				PathCondition pc;

				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISINTEGER, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						result = ((StringExpression) sym_v3)._IvalueOf();
						sf = th.getModifiableTopFrame();
						int objRef = getNewObjRef(invInst, th); /* dummy Integer Object */
						sf.push(objRef, true);
						sf.setOperandAttr(result);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTINTEGER, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Integer Format Type Exception");
						th.getVM().getSystemState().setIgnored(true);
						sf.push(0, true);
					}
				}
			}
		}
	}

	public void handleParseInt(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			IntegerExpression result = null;
			ChoiceGenerator<?> cg;
			boolean conditionValue;
			cg = th.getVM().getChoiceGenerator();

			assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
			conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

			sf.pop();
			PathCondition pc;
			ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
			while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
				prev_cg = prev_cg.getPreviousChoiceGenerator();
			}

			if (prev_cg == null)
				pc = new PathCondition();
			else
				pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

			assert pc != null;

			if (conditionValue) {
				pc.spc._addDet(StringComparator.ISINTEGER, (StringExpression) sym_v3);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					((PCChoiceGenerator) cg).setCurrentPC(pc);
					result = ((StringExpression) sym_v3)._IvalueOf();
					sf.push(0, false); /* Result is don't care and an int */
					sf = th.getModifiableTopFrame();
					sf.setOperandAttr(result);
				}
			} else {
				pc.spc._addDet(StringComparator.NOTINTEGER, (StringExpression) sym_v3);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					System.err.println("ERROR: Integer Format Type Exception");
					th.getVM().getSystemState().setIgnored(true);
					sf.push(0, true);
				}
			}
		}

	}

	public void handleParseFloat(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			RealExpression result = null;
			ChoiceGenerator<?> cg;
			boolean conditionValue;
			cg = th.getVM().getChoiceGenerator();

			assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
			conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

			sf.pop();
			PathCondition pc;
			ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
			while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
				prev_cg = prev_cg.getPreviousChoiceGenerator();
			}

			if (prev_cg == null)
				pc = new PathCondition();
			else
				pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

			assert pc != null;
			if (conditionValue) {
				pc.spc._addDet(StringComparator.ISFLOAT, (StringExpression) sym_v3);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					((PCChoiceGenerator) cg).setCurrentPC(pc);
					result = ((StringExpression) sym_v3)._RvalueOf();
					sf.push(0, false); /* Result is don't care and a float */
					sf = th.getModifiableTopFrame();
					sf.setOperandAttr(result);
				}
			} else {
				pc.spc._addDet(StringComparator.NOTFLOAT, (StringExpression) sym_v3);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					System.err.println("ERROR: Possible Float Format Type Exception - Path Terminated");
					System.out.println("********************************************************");
					th.getVM().getSystemState().setIgnored(true);
				}
			}
		}

	}

	public void handleParseFloatValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof RealExpression) {
				RealExpression sym_v2 = (RealExpression) sym_v3;
				sf.pop();
				int objRef = getNewObjRef(invInst, th); /* dummy Float Object */
				sf.push(objRef, true);
				sf.setOperandAttr(sym_v2);
			} else {
				RealExpression result = null;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

				sf.pop();
				PathCondition pc;
				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;
				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISFLOAT, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						result = ((StringExpression) sym_v3)._RvalueOf();
						int objRef = getNewObjRef(invInst, th); /* dummy Float Object */
						sf.push(objRef, true);
						sf = th.getModifiableTopFrame();
						sf.setOperandAttr(result);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTFLOAT, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Possible Float Format Type Exception - Path Terminated");
						System.out.println("********************************************************");
						th.getVM().getSystemState().setIgnored(true);
					}
				}
			}
		}

	}

	public void handleParseDoubleValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof RealExpression) {
				RealExpression sym_v2 = (RealExpression) sym_v3;
				sf.popLong();
				int objRef = getNewObjRef(invInst, th); /* dummy Double Object */
				sf.push(objRef, true);
				sf.setOperandAttr(sym_v2);
			} else {
				RealExpression result = null;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;

				sf.pop();
				PathCondition pc;
				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISDOUBLE, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						result = ((StringExpression) sym_v3)._RvalueOf();
						int objRef = getNewObjRef(invInst, th); /* dummy Double Object */
						sf.push(objRef, true);
						sf = th.getModifiableTopFrame();
						sf.setOperandAttr(result);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTDOUBLE, (StringExpression) sym_v3);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Double Format Type Exception");
						th.getVM().getSystemState().setIgnored(true);
						sf.push(0, true); // TODO: this is very strange code; to review
					}
				}
			}
		}

	}

	public void handleParseDouble(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof RealExpression) {
				return;
			} else {
				StringExpression sym_v1 = (StringExpression) sym_v3;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;
				sf.pop();
				PathCondition pc;

				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISDOUBLE, sym_v1);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						RealExpression sym_v2 = new SpecialRealExpression(sym_v1);
						sf.pushLong((long) 0); /* Result is don't care and 0 */
						//sf = th.getModifiableTopFrame(); ??
						sf.setLongOperandAttr(sym_v2);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTDOUBLE, sym_v1);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Double Format Type Exception");
						th.getVM().getSystemState().setIgnored(true);
					}
				}
			}
		}
	}

	public void handleParseLong(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v3 = (Expression) sf.getOperandAttr(0);

		if (sym_v3 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			if (sym_v3 instanceof IntegerExpression) {
				return;
			} else {
				StringExpression sym_v1 = (StringExpression) sym_v3;
				ChoiceGenerator<?> cg;
				boolean conditionValue;
				cg = th.getVM().getChoiceGenerator();

				assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
				conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;
				sf.pop();
				PathCondition pc;

				ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
				while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
					prev_cg = prev_cg.getPreviousChoiceGenerator();
				}

				if (prev_cg == null)
					pc = new PathCondition();
				else
					pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

				assert pc != null;

				if (conditionValue) {
					pc.spc._addDet(StringComparator.ISLONG, sym_v1);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						((PCChoiceGenerator) cg).setCurrentPC(pc);
						IntegerExpression sym_v2 = new SpecialIntegerExpression(sym_v1);
						sf.pushLong((long) 0); /* result is don't care */
						//sf = th.getModifiableTopFrame(); ??
						sf.setLongOperandAttr(sym_v2);
					}
				} else {
					pc.spc._addDet(StringComparator.NOTLONG, sym_v1);
					if (!pc.simplify()) {// not satisfiable
						th.getVM().getSystemState().setIgnored(true);
					} else {
						System.err.println("ERROR: Long Format Type Exception");
						th.getVM().getSystemState().setIgnored(true);
					}
				}
			}
		}
	}

	public void handleParseBoolean(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic method must have symbolic string operand");
		} else {
			ChoiceGenerator<?> cg;
			boolean conditionValue;
			cg = th.getVM().getChoiceGenerator();

			assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
			conditionValue = (Integer) cg.getNextChoice() == 0 ? false : true;
			sf.pop();
			PathCondition pc;

			ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGenerator();
			while (!((prev_cg == null) || (prev_cg instanceof PCChoiceGenerator))) {
				prev_cg = prev_cg.getPreviousChoiceGenerator();
			}

			if (prev_cg == null)
				pc = new PathCondition();
			else
				pc = ((PCChoiceGenerator) prev_cg).getCurrentPC();

			assert pc != null;

			if (conditionValue) {
				pc.spc._addDet(StringComparator.ISBOOLEAN, sym_v1);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					((PCChoiceGenerator) cg).setCurrentPC(pc);
					IntegerExpression sym_v2 = new SpecialIntegerExpression(sym_v1);
					sf.push(0, false); /* result is don't care and 0 */
					sf = th.getModifiableTopFrame();
					sf.setOperandAttr(sym_v2);
				}
			} else {
				pc.spc._addDet(StringComparator.NOTBOOLEAN, sym_v1);
				if (!pc.simplify()) {// not satisfiable
					th.getVM().getSystemState().setIgnored(true);
				} else {
					System.err.println("ERROR: Boolean Format Type Exception");
					th.getVM().getSystemState().setIgnored(true);
				}
			}
		}
	}

	public int getNewObjRef(InvokeInstruction invInst, ThreadInfo th) {
		
		//DynamicArea da = th.getVM().getDynamicArea();
		MethodInfo mi = invInst.getInvokedMethod();
		ClassInfo ci = ClassLoaderInfo.getCurrentResolvedClassInfo(mi.getReturnTypeName());
		ElementInfo objRef = th.getHeap().newObject(ci, th);
		return objRef.getObjectRef();
	}

	// works for BigDecimal
	public Instruction getBigDecimalValue(InvokeInstruction invInst, ThreadInfo th) {
		MethodInfo mi = invInst.getInvokedMethod();
		ClassInfo ci = mi.getClassInfo();
		MethodInfo miInit = ci.getMethod("toString()V", false);
		if (miInit == null) {
			return null;
		}
		//Instruction initPC = miInit.execute(th);
		//return initPC;
		throw new RuntimeException("not handled; to review");
	}

	// works for String, StringBuilder, StringBuffer
	public Instruction init1NewStringObjRef(InvokeInstruction invInst, ThreadInfo th) {
		MethodInfo mi = invInst.getInvokedMethod();
		ClassInfo ci = mi.getClassInfo();
		MethodInfo miInit = ci.getMethod("<init>()V", false);
		if (miInit == null) {
			return null;
		}
		//Instruction initPC = miInit.execute(th); // TODO: to review
		//return initPC;
		throw new RuntimeException("not handled; to review");
	}

	public Instruction handleIntValueOf(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: handleIntValueOf");
		} else {
			sf.pop();
			StringExpression sym_v2 = StringExpression._valueOf(sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * string
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		}
		return null;
	}

	public Instruction handleFloatValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		RealExpression sym_v1 = (RealExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: handleFloatValueOf");
		} else {
			sf.pop();
			StringExpression sym_v2 = StringExpression._valueOf(sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * string
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		}
		return null;
	}

	public Instruction handleLongValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: handleLongValueOf");
		} else {
			sf.popLong();
			StringExpression sym_v2 = StringExpression._valueOf(sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * string
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		}
		return null;
	}

	public Instruction handleDoubleValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		RealExpression sym_v1 = (RealExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: handleDoubleValueOf");
		} else {
			sf.popLong();
			StringExpression sym_v2 = StringExpression._valueOf(sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * string
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		}
		return null;
	}

	public Instruction handleBooleanValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);

		if (sym_v1 == null) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: handleBooleanValueOf");
		} else {
			sf.pop();
			StringExpression sym_v2 = StringExpression._valueOf(sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * string
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		}
		return null;
	}

	public Instruction handleCharValueOf(InvokeInstruction invInst, ThreadInfo th) {
		System.err.println("ERROR: symbolic string method not Implemented - CharValueOf");
		return null;
	}

	public Instruction handleCharArrayValueOf(InvokeInstruction invInst, ThreadInfo th) {
		System.err.println("ERROR: symbolic string method not Implemented - CharArrayValueof");
		return null;
	}

	public Instruction handleObjectValueOf(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v1 = (Expression) sf.getOperandAttr(0);
		if (sym_v1 instanceof SymbolicStringBuilder) {
			sf.pop();
			SymbolicStringBuilder sym_v3 = (SymbolicStringBuilder) sym_v1;
			StringExpression sym_v2 = StringExpression._valueOf((StringExpression) sym_v3.getstr());
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		} else if (sym_v1 instanceof StringExpression) {
			sf.pop();
			StringExpression sym_v2 = StringExpression._valueOf((StringExpression) sym_v1);
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v2);
		} else {
			System.err.println("ERROR: symbolic string method not Implemented - ObjectValueof");
		}
		return null;
	}

	public Instruction handleConcat(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		StringExpression sym_v2 = (StringExpression) sf.getOperandAttr(1);

		if ((sym_v1 == null) & (sym_v2 == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleConcat");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();

			StringExpression result = null;
			if (sym_v1 == null) { // operand 0 is concrete
				ElementInfo e1 = th.getElementInfo(s1);
				String val = e1.asString();
				result = sym_v2._concat(val);
			} else if (sym_v2 == null) { // operand 1 is concrete
				ElementInfo e2 = th.getElementInfo(s2);
				String val = e2.asString();
				sym_v2 = new StringConstant(val);
				result = sym_v2._concat(sym_v1);
			} else { // both operands are symbolic
				result = sym_v2._concat(sym_v1);
			}
			int objRef = th.getHeap().newString("", th).getObjectRef(); /*
																																	 * dummy
																																	 * String
																																	 * Object
																																	 */
			sf.push(objRef, true);
			sf.setOperandAttr(result);
		}
		return null;
	}

	public void handleObjectEquals(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Expression sym_v1 = (Expression) sf.getOperandAttr(0);
		Expression sym_v2 = (Expression) sf.getOperandAttr(1);

		if (sym_v1 != null) {
			// System.out.println("*" + sym_v1.toString());
			if (!(sym_v1 instanceof StringExpression)) {
				System.err.println("ERROR: expressiontype not handled: ObjectEquals");
				return;
			}
		}

		if (sym_v2 != null) {
			// System.out.println("***" + sym_v2.toString());
			if (!(sym_v2 instanceof StringExpression)) {

				System.err.println("ERROR: expressiontype not handled: ObjectEquals");
				return;
			}
		}

		handleEquals(invInst, th);
	}

	public void handleEquals(InvokeInstruction invInst,  ThreadInfo th) {
		handleBooleanStringInstructions(invInst,  th, StringComparator.EQUALS);
		
	}

	public Instruction handleAppend(InvokeInstruction invInst, ThreadInfo th) {
		Instruction handled = null;
		
		MethodInfo mi = invInst.getInvokedMethod(th);
		String[] argTypes = mi.getArgumentTypeNames();
		// System.out.println(argTypes[0]);
		
		boolean isCharSequence = false;
		//check what is the concrete type of the charsequence
		if(argTypes[0].equals("java.lang.CharSequence")) {
			isCharSequence = true;
			StackFrame sf = th.getModifiableTopFrame();
			int firstParamIndex = mi.isStatic() ? 0 : 1;
			Object firstParam = sf.getArgumentAttrs(mi)[firstParamIndex]; 
			if(firstParam instanceof StringExpression || firstParam == null /*possibly an string constant*/) {
				argTypes[0] = "java.lang.String";
			} else if (firstParam instanceof SymbolicStringBuilder) {
				//TODO and if it is a StringBuffer?
				argTypes[0] = "java.lang.StringBuilder"; 
			} else {
				throw new RuntimeException("Unhandled CharSequence at Symbolic String Append; concrete type is:" + firstParam.getClass());
			}
		}
		if (isCharSequence && argTypes.length == 3) { //append(charSequence,int,int)
			if(argTypes[0].equals("java.lang.String")) {
				handled = handleStringAppend3(invInst, th);
			} else { //stringbuilder
				handled = handleStringBuilderAppend3(invInst, th);
			}
		} else if (argTypes[0].equals("java.lang.String")) {
			handleStringAppend(invInst, th);
		} else if ((argTypes[0].equals("java.lang.StringBuilder")) || (argTypes[0].equals("java.lang.StringBuffer"))) {
			handleStringBuilderAppend(invInst, th);
		} else if (argTypes[0].equals("int")) {
			handleIntAppend(invInst, th);
		} else if (argTypes[0].equals("char")) {
			handleCharAppend(invInst, th);
		} else if (argTypes[0].equals("byte")) {
			handleByteAppend(invInst, th);
		} else if (argTypes[0].equals("short")) {
			handleShortAppend(invInst, th);
		} else if (argTypes[0].equals("float")) {
			handleFloatAppend(invInst, th);
		} else if (argTypes[0].equals("long")) {
			handleLongAppend(invInst, th);
		} else if (argTypes[0].equals("double")) {
			handleDoubleAppend(invInst, th);
		} else if (argTypes[0].equals("boolean")) {
			handleBooleanAppend(invInst, th);
		} else if (argTypes[0].equals("java.lang.Object")) {
			handleObjectAppend(invInst, th);
		} else {
			System.err.println("ERROR: Input parameter type not handled in Symbolic String Append");
		}

		return handled;
	}

	public void handleStringAppend(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		// int objRef = sf.getThis();
		// ElementInfo ei = th.getElementInfo(objRef);

		StringExpression sym_v1 = (StringExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleStringAppend");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();

			if (sym_v1 == null) { // operand 0 is concrete
				ElementInfo e1 = th.getElementInfo(s1);
				String val = e1.asString();
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				// setVariableAttribute(ei, invInst, th, sf, s2, sym_v2); //set the
				// value of the attribute of local StringBuilder element as sym_v2
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}
	
	public Instruction handleStringAppend3(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		
		IntegerExpression sym_end = (IntegerExpression) sf.getOperandAttr(0);
		IntegerExpression sym_start = (IntegerExpression) sf.getOperandAttr(1);
		StringExpression sym_string = (StringExpression) sf.getOperandAttr(2);
		SymbolicStringBuilder sym_builder = (SymbolicStringBuilder) sf.getOperandAttr(3);

		if (sym_builder == null) {
			sym_builder = new SymbolicStringBuilder();
		}
		
		//check if all parameters are concrete
		boolean concreteSubstring = (sym_end == null & sym_start == null & sym_string == null);
		
		if (concreteSubstring & sym_builder.getstr() == null) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: HandleStringAppend3");
		} else {
			int endRef = sf.pop();
			int startRef = sf.pop();
			int stringRef = sf.pop();
			int builderRef = sf.pop();
	
			//prepare the substring
			StringExpression substring;
			if(concreteSubstring) {
				try {
					ElementInfo eiString = th.getElementInfo(stringRef);
					String concreteString = eiString.asString();
					String slice = concreteString.substring(startRef, endRef);
					substring = new StringConstant(slice);
				} catch (IndexOutOfBoundsException e) {
					return th.createAndThrowException("java.lang.IndexOutOfBoundsException",e.getMessage());
				}
			} else {
				if(sym_string == null) { 
					ElementInfo eString = th.getElementInfo(stringRef);
					String concreteString = eString.asString();
					sym_string = new StringConstant(concreteString);
				}
				substring = createSymbolicSubstring(sym_string, sym_start, sym_end, startRef, endRef);
			}
			
			//append to the symbolic string
			if(sym_builder.getstr() == null) { //stringbuilder is concrete 
				ElementInfo eiBuilder = th.getElementInfo(builderRef);
				String builderContents = getStringEquiv(eiBuilder);
				sym_builder.putstr(new StringConstant(builderContents));
			}
			
			sym_builder._append(substring);
			sf.push(builderRef,true); 
		}
		
		sf.setOperandAttr(sym_builder);
		
		return null;
	}
	
	//helper
	private StringExpression createSymbolicSubstring(StringExpression sym_str,
			IntegerExpression sym_start, IntegerExpression sym_end,
			int startRef, int endRef) {
		
		StringExpression result;
		
		//'end' is the first parameter (something with stack representation, maybe?) 
		if(sym_start == null && sym_end == null) { 
			result = sym_str._subString(endRef, startRef);
		} else if (sym_start == null) {
			result = sym_str._subString(sym_end, startRef);
		} else { //sym_end == null
			result = sym_str._subString(endRef, sym_start);
		}
		
		return result;
	}
	
	public Instruction handleStringBuilderAppend3(InvokeInstruction invInst, ThreadInfo th) {
		throw new RuntimeException("implement this");
	}

	public void setVariableAttribute(ElementInfo ei, InvokeInstruction invInst, ThreadInfo th, StackFrame sf, int idx,
			Object sym_v2) {
		int count = sf.getLocalVariableCount();
		for (int i = 0; i < count; i++) {
			int idx1 = sf.getLocalVariable(i);
			if (idx1 == idx) {
				sf.setLocalAttr(i, sym_v2);
				return;
			}
		}
		// If variable is a static field and not local variable
		ClassInfo ci = sf.getClassInfo();
		FieldInfo[] fields = ci.getDeclaredStaticFields();
		int fieldid = -1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isReference()) {
				fieldid = ci.getStaticElementInfo().getReferenceField(fields[i]);
			}
			if (fieldid == idx) {
				ci.getStaticElementInfo().setFieldAttr(fields[i], sym_v2);
				return;
			}
		}

		// If variable is an instance field and not local variable
		FieldInfo[] fields1 = ci.getDeclaredInstanceFields();
		fieldid = -1;
		for (int i = 0; i < fields1.length; i++) {
			if (fields1[i].isReference()) {
				fieldid = ei.getReferenceField(fields1[i]);
			}
			if (fieldid == idx) {
				ei.setFieldAttr(fields1[i], sym_v2);
				return;
			}
		}
		// if method does not return anything then
		MethodInfo mi = invInst.getInvokedMethod();
		byte b = mi.getReturnTypeCode();
		if (b == Types.T_VOID)
			System.out.println("WARNING: Could not set variable attribute");

	}

	public void handleCharAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleCharAppend");
		} else {
			char s1 = (char) sf.pop();
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Character.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleByteAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleByteAppend");
		} else {
			byte s1 = (byte) sf.pop();
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Byte.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleShortAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleShortAppend");
		} else {
			short s1 = (short) sf.pop();
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Short.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleIntAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: hanldeIntAppend");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Integer.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleFloatAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		RealExpression sym_v1 = (RealExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: hanldeFloatAppend");
		} else {
			float s1 = Types.intToFloat(sf.pop());
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Float.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleBooleanAppend(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: hanldeBooleanAppend");
		} else {
			boolean s1 = Types.intToBoolean(sf.pop());
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Boolean.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1); /*
																 * String s1 =
																 * AbstractionUtilityMethods.unknownString();
																 * String s2 =
																 * AbstractionUtilityMethods.unknownString();
																 * String s4 =
																 * AbstractionUtilityMethods.unknownString();
																 * String s5 =
																 * AbstractionUtilityMethods.unknownString();
																 */

				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleLongAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(2);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleLongAppend");
		} else {
			long s1 = sf.popLong();
			int s2 = sf.pop();
			if (sym_v1 == null) { // operand 0 is concrete
				String val = Long.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleDoubleAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();

		RealExpression sym_v1 = (RealExpression) sf.getLongOperandAttr();
		double s1 = Types.longToDouble(sf.popLong());
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr();
		int s2 = sf.pop();

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand");
		} else {

			if (sym_v1 == null) { // operand 0 is concrete
				String val = Double.toString(s1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	/*
	 * String s1 = AbstractionUtilityMethods.unknownString(); String s2 =
	 * AbstractionUtilityMethods.unknownString(); String s4 =
	 * AbstractionUtilityMethods.unknownString(); String s5 =
	 * AbstractionUtilityMethods.unknownString();
	 */

	public void handleObjectAppend(InvokeInstruction invInst, ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();

		Expression sym_v1 = (Expression) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);
		// System.out.println(invInst.getSourceLocation());
		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if ((sym_v1 == null) && (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: handleObjectAppend");
		} else {
			int s1 = sf.pop();
			ElementInfo e2 = th.getElementInfo(s1);
			int s2 = sf.pop();
			if (sym_v1 == null || (sym_v1 instanceof SymbolicStringBuilder 
					&& ((SymbolicStringBuilder) sym_v1).getstr() == null)) { // operand 0 is concrete
				String val = getStringEquiv(e2);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				if (sym_v1 instanceof SymbolicStringBuilder)
					sym_v2._append((SymbolicStringBuilder) sym_v1);
				else if (sym_v1 instanceof StringExpression)
					sym_v2._append((StringExpression) sym_v1);
				else {
					System.err.println("Object not handled in ObjectAppend");
				}
				// setVariableAttribute(ei, invInst, th, sf, s2, sym_v2); //set the
				// value of the attribute of local StringBuilder element as sym_v2
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				if (sym_v1 instanceof SymbolicStringBuilder)
					sym_v2._append((SymbolicStringBuilder) sym_v1);
				else if (sym_v1 instanceof StringExpression)
					sym_v2._append((StringExpression) sym_v1);
				else {
					System.err.println("Object not handled in ObjectAppend");
				}

				sf.push(s2, true); /* string Builder element can continue */
			}
			sf.setOperandAttr(sym_v2);
		}
	}

	public void handleStringBuilderAppend(InvokeInstruction invInst, ThreadInfo th) {

		StackFrame sf = th.getModifiableTopFrame();
		SymbolicStringBuilder sym_v1 = (SymbolicStringBuilder) sf.getOperandAttr(0);
		SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sf.getOperandAttr(1);

		if (sym_v2 == null)
			sym_v2 = new SymbolicStringBuilder();
		if (sym_v1 == null)
			sym_v1 = new SymbolicStringBuilder();

		if ((sym_v1.getstr() == null) & (sym_v2.getstr() == null)) {
			System.err.println("ERROR: symbolic string method must have one symbolic operand: hanldeStringBuilderAppend");
		} else {
			int s1 = sf.pop();
			int s2 = sf.pop();

			if (sym_v1.getstr() == null) { // operand 0 is concrete
				ElementInfo e1 = th.getElementInfo(s1);
				String val = getStringEquiv(e1);
				sym_v2._append(val);
				sf.push(s2, true); /* symbolic string Builder element */
			} else if (sym_v2.getstr() == null) { // operand 1 is concrete; get string
				// from String builder object
				ElementInfo e1 = th.getElementInfo(s2);
				String val = getStringEquiv(e1);
				sym_v2.putstr(new StringConstant(val));
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* symbolic string Builder element */
			} else { // both operands are symbolic
				sym_v2._append(sym_v1);
				sf.push(s2, true); /* string Builder element can continue */
			}

			sf.setOperandAttr(sym_v2);
		}
	}

	public String getStringEquiv(ElementInfo ei) {
		String objectType = ei.getType();
		if (objectType.equals("Ljava/lang/StringBuilder;")) {
			int idx = ei.getReferenceField("value");
			int length = ei.getIntField("count");
			ElementInfo e1 = VM.getVM().getHeap().get(idx);
			char[] str = e1.asCharArray();
			String val = new String(str, 0, length);
			return val;
		} else if (objectType.equals("Ljava/lang/StringBuffer;")) {
			int idx = ei.getReferenceField("value");
			int length = ei.getIntField("count");
			ElementInfo e1 = VM.getVM().getHeap().get(idx);
			char[] str = e1.asCharArray();
			String val = new String(str, 0, length);
			return val;
		} else if (objectType.equals("Ljava/lang/Integer;")) {
			int val = ei.getIntField("value");
			return Integer.toString(val);
		} else if (objectType.equals("Ljava/lang/Float;")) {
			float val = ei.getFloatField("value");
			return Float.toString(val);
		} else if (objectType.equals("Ljava/lang/Long;")) {
			long val = ei.getLongField("value");
			return Long.toString(val);
		} else if (objectType.equals("Ljava/lang/Double;")) {
			double val = ei.getDoubleField("value");
			return Double.toString(val);
		} else if (objectType.equals("Ljava/lang/Boolean;")) {
			boolean val = ei.getBooleanField("value");
			return Boolean.toString(val);
		} else {
			System.err.println("ERROR: Object Type Not Handled in getStringVal");
			return null;
		}
	}

	public Instruction handletoString(InvokeInstruction invInst,  ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		Object sym_obj_v2 = sf.getOperandAttr(0);
		if (sym_obj_v2 instanceof StringExpression) {
			return null;
		}
		StringExpression sym_v1 = null;
		if (sym_obj_v2 instanceof SymbolicStringBuilder) {
			SymbolicStringBuilder sym_v2 = (SymbolicStringBuilder) sym_obj_v2;
			sym_v1 = sym_v2.getstr();
		} else {
			System.err.println("ERROR: symbolic type not Handled: toString");
		}

		if ((sym_v1 == null)) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: toString");
		} else {
			sf.pop();
			ElementInfo ei = th.getHeap().newString("", th);
			int objRef = ei.getObjectRef();
			sf.push(objRef, true);
			sf.setOperandAttr(sym_v1);
		}
		return null;
	}

	public void handleprintln(InvokeInstruction invInst, ThreadInfo th, boolean doPrintln) {
		StackFrame sf = th.getModifiableTopFrame();
		MethodInfo mi = invInst.getInvokedMethod(th);
		String[] argTypes = mi.getArgumentTypeNames();
		Expression sym_v1 = null;
		boolean flag = false;
		if ((argTypes[0].equals("long")) || (argTypes[0].equals("double"))) {
			sym_v1 = (Expression) sf.getLongOperandAttr();
			flag = true;
		} else {
			sym_v1 = (Expression) sf.getOperandAttr(0);
		}

		if ((sym_v1 == null)) {
			System.err.println("ERROR: symbolic string method must have symbolic operand: println");
		} else {
			if (flag)
				sf.popLong();
			else
				sf.pop(); // clear out operand stack
			sf.pop();
			String result = sym_v1.toString();
			if (doPrintln) {
				System.out.println("Symbolic Exp [ " + result + "]");
			} else {
				System.out.print("Symbolic Exp [ " + result + " ]");
			}
			th.getHeap().newString("", th); //Corina this code is so broken
			//th.push(objRef, true);
			//sf.setOperandAttr(sym_v1);
		}
	}
}
