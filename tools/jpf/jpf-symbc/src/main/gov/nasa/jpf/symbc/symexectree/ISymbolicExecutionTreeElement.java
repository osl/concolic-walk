/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public interface ISymbolicExecutionTreeElement {
	void accept(SymbolicExecutionTreeVisitor visitor);
}
