import gov.nasa.jpf.symbc.Debug;


public class MyClass1 {
	// The method you need tests for
	public int myMethod(int x, int y) {
		int z = x + y;
		if (z > 0) {
			z = 1;
		} else {
			z = z - x;
		}
		z = x * z;
		return z;
	}
	
	// The test driver
	public static void main(String[] args) {
		MyClass1 mc = new MyClass1();
		int x = mc.myMethod(1, 2);
		Debug.printPC("\nMyClass1.myMethod Path Condition: ");
	}
}