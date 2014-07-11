package sequences;


import gov.nasa.jpf.vm.Verify;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;

/**
 *
 * @author Mithun Acharya
 *
 */
public class BankAccountDriverSequences {

	public static void testDriver(int length){
		BankAccount b = new BankAccount(0);
		for (int i=0; i<length; i++){
			Verify.beginAtomic();

			switch (Verify.random(1)){
			case 0:
				b.deposit(10);
				break;
			case 1:
				b.withdraw(1);
				break;
			}
			Verify.endAtomic();
		}
	}

	public static void main(String[] args){
		testDriver(3);
		Debug.printPC("Path Condition: ");
		System.out.println();
	}
}
