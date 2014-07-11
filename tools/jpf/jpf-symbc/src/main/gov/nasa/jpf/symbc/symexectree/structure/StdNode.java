/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class StdNode extends Node {

	public StdNode(InstrContext instructionContext) {
		super(instructionContext);
	}
	
	public StdNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
	}

}
