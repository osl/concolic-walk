package sequences;


import gov.nasa.jpf.vm.Verify;
import gov.nasa.jpf.symbc.Debug;


/**
 *
 * @author Mithun Acharya
 *
 * Arguments for concrete execution:
 * IncDecDriverSequences
 *
 * Arguments for symbolic execution:
 * +vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory
 * +vm.classpath=.
 * +vm.storage.class=
 * +search.multiple_errors=true
 * +symbolic.method=inc(sym);dec(sym)
 * +jpf.report.console.finished=
 * +jpf.listener=gov.nasa.jpf.symbc.sequences.SymbolicSequenceListener
 * IncDecDriverSequences
 *
 *
 */
public class IncDecDriverSequences {

	private static void testDriver(int length){
		IncDec incDec = new IncDec();
		System.out.println("before loop");
		for (int i =0; i < length; i++){
			Verify.beginAtomic();
			switch(Verify.random(1)){
		  		case 0:
		  			System.out.println("i" + i + "case0");
		  			incDec.inc(1);
		  			break;
		  		case 1:
		  			System.out.println("i" + i + "case1");
		  			incDec.dec(1);
		  			break;
			}
			Verify.endAtomic();
			//Verify.ignoreIf(Debug.matchAbstractState(incDec));
		}
	}

	public static void main(String[] args) {
		testDriver(2);
		Debug.printPC("Path Condition: ");
		System.out.println();
	}
}
