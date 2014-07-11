/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import gov.nasa.jpf.symbc.symexectree.structure.Node;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public interface SymbolicExecutionTreeVisitor {
	void visit(SymbolicExecutionTree tree);
	void visit(Node node);
	void visit(Transition transition);
}
