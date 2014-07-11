package sequences;


import gov.nasa.jpf.vm.Verify;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;

/**
 *
 * @author Mithun Acharya
 *
 *  * Arguments for concrete execution:
 * BSTDriverSequences
 *
 * Arguments for symbolic execution:
 * +vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory
 * +vm.classpath=.
 * +vm.storage.class=
 * +search.multiple_errors=true
 * +symbolic.method=add(sym);remove(sym);find(sym)
 * +jpf.report.console.finished=
 * +jpf.listener=gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener
 * BSTDriverSequences
 *
 */
public class BSTDriverSequences {

	public static void testDriver(int length){
		BST t = new BST();
		for (int i=0; i<length; i++){
			Verify.beginAtomic();
			switch (Verify.random(2)){
			case 0:
				System.out.println("adding...");
				t.add(0);
				break;
			case 1:
				System.out.println("removing...");
				t.remove(0);
				break;
			case 2:
				System.out.println("finding...");
				t.find(0);
				break;
			}
			Verify.endAtomic();
			Verify.ignoreIf(Debug.matchAbstractState(t));
		}
	}

	public static void main(String[] args){
		testDriver(3); // with 2 you do not get complete coverage
		Debug.printPC("Path Condition: ");
		System.out.println();
	}
}
