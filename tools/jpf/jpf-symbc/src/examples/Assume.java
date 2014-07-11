import gov.nasa.jpf.symbc.Debug;



public class Assume {
	public int test(int x, int y) {
		Debug.assume(x>y);
		return x-y;
	}
	
	// The test driver
	public static void main(String[] args) {
		Assume testinst = new Assume();
		int x = testinst.test(1, 2);
		System.out.println("symbolic value of x: "+Debug.getSymbolicIntegerValue(x));
		Debug.printPC("\n Path Condition: ");
	}
}