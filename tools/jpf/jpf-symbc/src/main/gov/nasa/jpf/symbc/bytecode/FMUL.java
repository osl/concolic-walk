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


import gov.nasa.jpf.symbc.numeric.RealExpression;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.Types;


/**
 * Multiply float
 * ..., value1, value2 => ..., result
 */
public class FMUL extends gov.nasa.jpf.jvm.bytecode.FMUL {

  @Override
  public Instruction execute (ThreadInfo th) {
	StackFrame sf = th.getModifiableTopFrame();

	RealExpression sym_v1 = (RealExpression) sf.getOperandAttr(); 
    float v1 = Types.intToFloat(sf.pop());
    RealExpression sym_v2 = (RealExpression) sf.getOperandAttr();
    float v2 = Types.intToFloat(sf.pop());
    
    float r = v1 * v2;
    
    if(sym_v1==null && sym_v2==null)
    	sf.push(Types.floatToInt(r), false); 
    else
    	sf.push(0, false); 
    
    RealExpression result = null;
	if(sym_v2!=null) {
		if (sym_v1!=null)
			result = sym_v2._mul(sym_v1);
		else // v1 is concrete
			result = sym_v2._mul(v1);
	}
	else if (sym_v1!=null)
		result = sym_v1._mul(v2);
	
	sf.setOperandAttr(result);
	
	//System.out.println("Execute FMUL: "+ result);

    return getNext(th);
  }

}
