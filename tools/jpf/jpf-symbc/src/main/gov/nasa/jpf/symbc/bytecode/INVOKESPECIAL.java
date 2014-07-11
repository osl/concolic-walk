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

import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;

// need to fix names



/**
 * Invoke instance method; special handling for superclass, private,
 * and instance initialization method invocations
 * ..., objectref, [arg1, [arg2 ...]] => ...
 */
public class INVOKESPECIAL extends gov.nasa.jpf.jvm.bytecode.INVOKESPECIAL {
	public INVOKESPECIAL(String clsName, String methodName, String methodSignature) {
	    super(clsName, methodName, methodSignature);
	  }
	@Override
	public Instruction execute(ThreadInfo th) {
		MethodInfo mi = getInvokedMethod(th);

	    if (mi == null){
	      return th.createAndThrowException("java.lang.NoSuchMethodException!*", "Calling " + cname + '.' + mname);
	    }
	    
		BytecodeUtils.InstructionOrSuper nextInstr = BytecodeUtils.execute(this, th);
        if (nextInstr.callSuper) {
            return super.execute(th);
        } else {
            return nextInstr.inst;
        }
    }
}
