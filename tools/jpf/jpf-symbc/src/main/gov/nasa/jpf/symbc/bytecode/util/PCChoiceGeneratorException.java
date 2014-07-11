/**
 * 
 */
package gov.nasa.jpf.symbc.bytecode.util;


import java.io.PrintStream;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class PCChoiceGeneratorException extends RuntimeException {
		public PCChoiceGeneratorException (String details) {
			super(details);
		}

		public PCChoiceGeneratorException (Throwable cause) {
			super(cause);
		}

		public PCChoiceGeneratorException (String details, Throwable cause){
			super(details, cause);
		}
		
		public void printStackTrace (PrintStream out) {
			out.println("---------------------- Symbc JPF error stack trace ---------------------");
			super.printStackTrace(out);
		}
}
