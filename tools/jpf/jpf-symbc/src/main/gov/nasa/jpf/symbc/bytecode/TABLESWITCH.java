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

import gov.nasa.jpf.JPFException;
import gov.nasa.jpf.jvm.bytecode.InstructionVisitor;

import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.symbc.numeric.PathCondition;

import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;


/**
 * Access jump table by index and jump
 *   ..., index  ...
 */
public class TABLESWITCH extends SwitchInstruction implements gov.nasa.jpf.vm.TableSwitchInstruction{

	int min, max;

	  public TABLESWITCH(int defaultTarget, int min, int max){
	    super(defaultTarget, (max - min +1));
	    this.min = min;
	    this.max = max;
	  }

	  @Override
	  public Instruction execute (ThreadInfo ti) {  
		StackFrame sf = ti.getModifiableTopFrame();
		IntegerExpression sym_v = (IntegerExpression) sf.getOperandAttr();
			
		System.out.println("sym v "+ sym_v); 
		if(sym_v==null) return super.execute(ti);
		
		// the condition is symbolic
		ChoiceGenerator<?> cg;

		if (!ti.isFirstStepInsn()) { // first time around
			cg = new PCChoiceGenerator(targets.length+1);
			((PCChoiceGenerator)cg).setOffset(this.position);
			((PCChoiceGenerator)cg).setMethodName(this.getMethodInfo().getFullName());
			ti.getVM().getSystemState().setNextChoiceGenerator(cg);
			return this;
		} else {  // this is what really returns results
			cg = ti.getVM().getSystemState().getChoiceGenerator();
			assert (cg instanceof PCChoiceGenerator) : "expected PCChoiceGenerator, got: " + cg;
		}
		sym_v = (IntegerExpression) sf.getOperandAttr();
		sf.pop();
		PathCondition pc;
		//pc is updated with the pc stored in the choice generator above
		//get the path condition from the
		//previous choice generator of the same type

		//TODO: could be optimized to not do this for each choice
		ChoiceGenerator<?> prev_cg = cg.getPreviousChoiceGeneratorOfType(PCChoiceGenerator.class); 
	
		if (prev_cg == null)
			pc = new PathCondition();
		else
			pc = ((PCChoiceGenerator)prev_cg).getCurrentPC();

		assert pc != null;
		//System.out.println("Execute Switch: PC"+pc);
		int idx = (Integer)cg.getNextChoice();
		//System.out.println("Execute Switch: "+ idx);

		
		if (idx == targets.length){ // default branch
			lastIdx = -1;
		
			for(int i = 0; i< targets.length; i++)
				pc._addDet(Comparator.NE, sym_v._minus(min), i);
			// this could be replaced safely with only one constraint:
			// pc._addDet(Comparator.GT, sym_v._minus(min), targets.length);
			
			if(!pc.simplify())  {// not satisfiable
				ti.getVM().getSystemState().setIgnored(true);
			} else {
				//pc.solve();
				((PCChoiceGenerator) cg).setCurrentPC(pc);
				//System.out.println(((PCChoiceGenerator) cg).getCurrentPC());
			}
			return mi.getInstructionAt(target);
		} else {
			lastIdx = idx;
			pc._addDet(Comparator.EQ, sym_v._minus(min), idx);
			if(!pc.simplify())  {// not satisfiable
				ti.getVM().getSystemState().setIgnored(true);
			} else {
				//pc.solve();
				((PCChoiceGenerator) cg).setCurrentPC(pc);
				//System.out.println(((PCChoiceGenerator) cg).getCurrentPC());
			}
			return mi.getInstructionAt(targets[idx]);
		}

		
	  }

	  @Override
	  protected Instruction executeConditional (ThreadInfo ti){
		  StackFrame sf = ti.getModifiableTopFrame();
		    int value = sf.pop();
		    int i = value-min;
		    int pc;

		    if (i>=0 && i<targets.length){
		      lastIdx = i;
		      pc = targets[i];
		    } else {
		      lastIdx = -1;
		      pc = target;
		    }

		    // <2do> this is BAD - we should compute the target insns just once
		    return mi.getInstructionAt(pc);
		  }
	  
	@Override
	public void setTarget(int value, int target) {

		int i = value-min;

	    if (i>=0 && i<targets.length){
	      targets[i] = target;
	    } else {
	      throw new JPFException("illegal tableswitch target: " + value);
	    }
	}


	@Override
	public int getByteCode() {
		// TODO Auto-generated method stub
	    return 0xAA;
	}
	  


}
