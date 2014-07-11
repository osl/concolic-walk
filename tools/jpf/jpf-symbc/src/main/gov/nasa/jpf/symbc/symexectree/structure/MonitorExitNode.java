/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.jvm.bytecode.MONITOREXIT;
import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class MonitorExitNode extends MonitorNode {

	public MonitorExitNode(InstrContext instructionContext) {
		this(instructionContext, null);
	}
	
	public MonitorExitNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
		
		if(!(instructionContext.getInstr() instanceof MONITOREXIT))
			throw new UnexpectedInstructionTypeException(instructionContext.getInstr().getClass(), MONITOREXIT.class);
	}

}
