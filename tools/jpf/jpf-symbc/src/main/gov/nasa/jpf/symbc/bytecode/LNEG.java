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
 * Negate long
 * ..., value => ..., result
 */
public class LNEG extends gov.nasa.jpf.jvm.bytecode.LNEG {

  @Override
  public Instruction execute (ThreadInfo th) {
    StackFrame sf = th.getModifiableTopFrame();

    IntegerExpression sym_v1 = (IntegerExpression) sf.getLongOperandAttr();
    long v1 = sf.popLong();
    
  //System.out.println("Execute LNEG: "+Helper.get(index));
    
    if(sym_v1==null)
        sf.pushLong(-v1); // we'll still do the concrete execution
    else
        sf.pushLong(0);
    
    IntegerExpression result = null;
    if(sym_v1!=null) {
        result = sym_v1._neg();
    }
    sf.setLongOperandAttr(result);
    
    //System.out.println("Execute LNEG: "+result);

    return getNext(th);
  }

}
