/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.symbc.symexectree.SymbolicExecutionTreeRuntimeException;

import java.io.PrintStream;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class VisualizerException extends SymbolicExecutionTreeRuntimeException {

	public VisualizerException (String details) {
		super(details);
	}

	public VisualizerException (Throwable cause) {
		super(cause);
	}

	public VisualizerException (String details, Throwable cause){
		super(details, cause);
	}
	
	public void printStackTrace (PrintStream out) {
		super.printStackTrace(out);
	}
}
