/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import java.io.PrintStream;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class SymbolicExecutionTreeRuntimeException extends RuntimeException {
	public SymbolicExecutionTreeRuntimeException (String details) {
		super(details);
	}

	public SymbolicExecutionTreeRuntimeException (Throwable cause) {
		super(cause);
	}

	public SymbolicExecutionTreeRuntimeException (String details, Throwable cause){
		super(details, cause);
	}
	
	public void printStackTrace (PrintStream out) {
		out.println("---------------------- Symbolic Execution Tree JPF error stack trace ---------------------");
		super.printStackTrace(out);
	}
}
