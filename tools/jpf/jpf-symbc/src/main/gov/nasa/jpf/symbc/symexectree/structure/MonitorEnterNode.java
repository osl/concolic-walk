/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.jvm.bytecode.MONITORENTER;
import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class MonitorEnterNode extends MonitorNode {

	public MonitorEnterNode(InstrContext instructionContext) {
		this(instructionContext, null);
	}
	
	public MonitorEnterNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
		
		if(!(instructionContext.getInstr() instanceof MONITORENTER))
			throw new UnexpectedInstructionTypeException(instructionContext.getInstr().getClass(), MONITORENTER.class);
	}

}
