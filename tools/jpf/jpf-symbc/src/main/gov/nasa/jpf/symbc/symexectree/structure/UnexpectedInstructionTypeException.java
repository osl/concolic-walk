/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.structure;

import gov.nasa.jpf.vm.Instruction;

import java.io.PrintStream;


/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class UnexpectedInstructionTypeException extends RuntimeException {
	
	public UnexpectedInstructionTypeException (Class<? extends Instruction> class1, Class<? extends Instruction> class2) {
		this("Got instruction with type [" + class1.getCanonicalName() + "] but expected instruction type: [" + class2.getCanonicalName() + "]");
	}
	
	public UnexpectedInstructionTypeException (String details) {
		super(details);
	}

	public UnexpectedInstructionTypeException (Throwable cause) {
		super(cause);
	}

	public UnexpectedInstructionTypeException (String details, Throwable cause){
		super(details, cause);
	}
	
	public void printStackTrace (PrintStream out) {
		out.println("---------------------- Symbolic Execution Tree JPF error stack trace ---------------------");
		super.printStackTrace(out);
	}
}
