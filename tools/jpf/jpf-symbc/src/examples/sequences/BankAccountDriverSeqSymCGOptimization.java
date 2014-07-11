package sequences;


import gov.nasa.jpf.vm.Verify;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;


public class BankAccountDriverSeqSymCGOptimization {

	public static void testDriver(int length){
		BankAccount b = new BankAccount(0);
		for (int i=0; i<length; i++){
			boolean res = flag(true);
			if(res) {
				b.deposit(10);
				if(!res) {
					b.deposit(5);
				}
				
			} else if(res) {
				b.withdraw(1);
				if(!res) {
					b.withdraw(1);
				}
			}
		}
	}
	
	public static boolean flag(boolean x) {
		return x;
	}

	public static void main(String[] args){
		testDriver(11);
		//Debug.printPC("Path Condition: ");
		//System.out.println();
	}
}
