/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import java.io.PrintStream;

import gov.nasa.jpf.symbc.symexectree.SymbolicExecutionTreeRuntimeException;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class SymExecTreeVisualizerException extends SymbolicExecutionTreeRuntimeException {

	public SymExecTreeVisualizerException (String details) {
		super(details);
	}

	public SymExecTreeVisualizerException (Throwable cause) {
		super(cause);
	}

	public SymExecTreeVisualizerException (String details, Throwable cause){
		super(details, cause);
	}
	
	public void printStackTrace (PrintStream out) {
		super.printStackTrace(out);
	}
}
