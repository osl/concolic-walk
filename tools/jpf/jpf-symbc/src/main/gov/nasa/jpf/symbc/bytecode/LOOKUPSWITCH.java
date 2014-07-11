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

import gov.nasa.jpf.jvm.bytecode.InstructionVisitor;


/**
 * Access jump table by key match and jump
 * ..., key => ...
 */
public class LOOKUPSWITCH extends SwitchInstruction implements gov.nasa.jpf.vm.LookupSwitchInstruction {

	public LOOKUPSWITCH (int defaultTarget, int numberOfTargets) {
	    super(defaultTarget, numberOfTargets);
	  }

	  public void setTarget (int index, int match, int target){
	    targets[index] = target;
	    matches[index] = match;
	  }


	  public int getLength() {
	    return 10 + 2*(matches.length); // <2do> NOT RIGHT: padding!!
	  }

	  public int getByteCode () {
	    return 0xAB;
	  }

	  public void accept(InstructionVisitor insVisitor) {
		  insVisitor.visit(this);
	  }

}
