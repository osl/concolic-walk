/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class IfNode extends Node {

	public IfNode(InstrContext instructionContext) {
		super(instructionContext);
	}

	public IfNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
	}
	
}
