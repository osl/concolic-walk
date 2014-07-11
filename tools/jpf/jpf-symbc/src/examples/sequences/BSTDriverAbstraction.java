package sequences;




import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;
import gov.nasa.jpf.vm.Verify;

/**
 *
 * @author Mithun Acharya
 *
 * launch configuration
 * +vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory
 * +vm.classpath=.
 * +vm.storage.class=
 * +search.multiple_errors=true
 * +symbolic.method=add(sym);remove(sym);find(sym)
 * +jpf.report.console.finished=
 * +jpf.listener=gov.nasa.jpf.symbc.abstraction.SymbolicAbstractionListener
 * BSTDriverAbstraction
 *
 */
public class BSTDriverAbstraction {

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
		}
	}

	public static void main(String[] args){
		testDriver(2);
		Debug.printPC("Path Condition: ");
		System.out.println();
	}
}
