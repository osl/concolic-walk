package sequences;


import gov.nasa.jpf.vm.Verify;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;

/**
 *
 * @author Mithun Acharya
 *
 * launch configuration
 * +vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory
 * +vm.classpath=.
 * +vm.storage.class=
 * +search.multiple_errors=true
 * +symbolic.method=push(sym);pop(sym)
 * +jpf.report.console.finished=
 * +jpf.listener=gov.nasa.jpf.symbc.abstraction.SymbolicAbstractionListener
 * StackDriverAbstraction
 *
 */
public class StackDriverAbstraction {
	public static void testDriver(int length){
		Stack s = new Stack();
		System.out.println("before loop");
		for(int i=0; i<length; i++){
			Verify.beginAtomic();
			switch(Verify.random(1)){
			case 0:
				System.out.println(i + "0");
				s.push(0);
				break;
			case 1:
				System.out.println(i + "1");
				s.pop(0);
				break;
			}
			Verify.endAtomic();
		}
	}
	public static void main(String[] args){
		testDriver(2);
		Debug.printPC("Path Condition: ");
		System.out.println();
	}
}