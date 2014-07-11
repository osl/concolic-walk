/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.jvm.bytecode.LockInstruction;
import gov.nasa.jpf.symbc.symexectree.InstrContext;
import gov.nasa.jpf.vm.Instruction;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public abstract class MonitorNode extends Node {

	protected int lastLocRef;

	public MonitorNode(InstrContext instructionContext) {
		this(instructionContext, null);
	}
	
	public MonitorNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
		
		/*
		 * TODO: typechecking should be done for all nodes...
		 */
		Instruction instr = instructionContext.getInstr();
		if(!(instr instanceof LockInstruction))
			throw new UnexpectedInstructionTypeException(instr.getClass(), LockInstruction.class);
		
		LockInstruction mInstr = (LockInstruction)instructionContext.getInstr();
		
		this.lastLocRef = mInstr.getLastLockRef();
	}
	
	public int getLastLockRef() {
		return this.lastLocRef;
	}
}
