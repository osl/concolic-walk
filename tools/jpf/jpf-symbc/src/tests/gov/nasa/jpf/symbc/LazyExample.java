/**
 * 
 */
package gov.nasa.jpf.symbc;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class LazyExample {

	public static void main(String[] args) {
		LazyExample ex = new LazyExample();
		ex.testMethod();
	}
	
	X myX;
	
	public void testMethod() {
		myX = (X) Debug.makeSymbolicRef("SYMB", myX);
		if (myX != null){
			System.out.println("T: accessed global myX");
			if (!myX.pass){
				System.out.println("Gotcha!");
			}
		}
		Debug.printHeapPC("MSG");
	}
}
