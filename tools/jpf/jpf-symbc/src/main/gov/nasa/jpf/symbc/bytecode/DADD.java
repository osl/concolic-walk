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



import gov.nasa.jpf.symbc.numeric.*;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.Types;

public class DADD extends gov.nasa.jpf.jvm.bytecode.DADD {

	@Override
	public Instruction execute(ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();

		RealExpression sym_v1 = (RealExpression) sf.getLongOperandAttr();
		double v1 = Types.longToDouble(sf.popLong());

		RealExpression sym_v2 = (RealExpression) sf.getLongOperandAttr();
		double v2 = Types.longToDouble(sf.popLong());

		double r = v1 + v2;

		if (sym_v1 == null && sym_v2 == null)
			sf.pushLong(Types.doubleToLong(r));
		else
			sf.pushLong(0);

		RealExpression result = null;
		if (sym_v1 != null) {
			if (sym_v2 != null)
				result = sym_v2._plus(sym_v1);
			else
				// v2 is concrete
				result = sym_v1._plus(v2);
		} else if (sym_v2 != null)
			result = sym_v2._plus(v1);

		sf.setLongOperandAttr(result);

		//System.out.println("Execute DADD: " + sf.getLongOperandAttr());

		return getNext(th);

	}

}
