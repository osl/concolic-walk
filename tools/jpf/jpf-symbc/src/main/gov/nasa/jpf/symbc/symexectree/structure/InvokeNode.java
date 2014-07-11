/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class InvokeNode extends Node {

	public InvokeNode(InstrContext instructionContext) {
		super(instructionContext);
	}
	
	public InvokeNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
	}

}
