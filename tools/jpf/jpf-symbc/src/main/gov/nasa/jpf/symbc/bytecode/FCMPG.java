//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//
package gov.nasa.jpf.symbc.bytecode;

import gov.nasa.jpf.symbc.bytecode.util.IFInstrSymbHelper;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.RealExpression;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.Types;

/**
 * Compare float ..., value1, value2 => ..., result
 */
public class FCMPG extends gov.nasa.jpf.jvm.bytecode.FCMPG {

    @Override
	public Instruction execute(ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();

		RealExpression sym_v1 = (RealExpression) sf.getOperandAttr(0);
		RealExpression sym_v2 = (RealExpression) sf.getOperandAttr(1);

		if (sym_v1 == null && sym_v2 == null) { // both conditions are concrete
			return super.execute( th);
		} else { // at least one condition is symbolic
			Instruction nxtInstr = IFInstrSymbHelper.getNextInstructionAndSetPCChoiceFloat(th, 
																					  this, 
																					  sym_v1,
																					  sym_v2,
																					  Comparator.LT, 
																					  Comparator.EQ,
																					  Comparator.GT);

			return nxtInstr;
		}
	}
}
