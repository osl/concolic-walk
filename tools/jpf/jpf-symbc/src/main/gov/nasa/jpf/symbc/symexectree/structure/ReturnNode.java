/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.symbc.symexectree.InstrContext;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class ReturnNode extends Node {

	public ReturnNode(InstrContext instructionContext) {
		super(instructionContext);
	}
	
	public ReturnNode(InstrContext instructionContext, SymbolicExecutionTree tree) {
		super(instructionContext, tree);
	}

}
