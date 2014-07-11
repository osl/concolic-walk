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


import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;

/**
 * Boolean OR long
 * ..., value1, value2 => ..., result
 */
public class LOR extends gov.nasa.jpf.jvm.bytecode.LOR {

  @Override
  public Instruction execute (ThreadInfo th) {
	    StackFrame sf = th.getModifiableTopFrame();
	  
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(1);
		IntegerExpression sym_v2 = (IntegerExpression) sf.getOperandAttr(3);
	    
	    if(sym_v1==null && sym_v2==null)
	        return super.execute(th);// we'll still do the concrete execution
	    else {
	    	long v1 = sf.popLong();
	    	long v2 = sf.popLong();
	    	sf.pushLong(0); // for symbolic expressions, the concrete value does not matter

	    	IntegerExpression result = null;
	    	if(sym_v1!=null) {
	    		if (sym_v2!=null)
	    			result = sym_v1._or(sym_v2);
	    		else // v2 is concrete
	    			result = sym_v1._or(v2);
	    	}
	    	else if (sym_v2!=null) {
	    		result = sym_v2._or(v1);

	    	}
	    	sf.setLongOperandAttr(result);

	    	//System.out.println("Execute LADD: "+sf.getLongOperandAttr());

	    	return getNext(th);
	    }
  }

}
