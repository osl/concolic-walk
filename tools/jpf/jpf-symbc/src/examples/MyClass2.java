
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.vm.Verify;

public class MyClass2 {
	// The method you need tests for
	private int myMethod2(int x, int y) {
		int jpfIfCounter = 0;
		int z = x + y;
		if (z > 0) {
			jpfIfCounter++;
			z = 1;
		}
		if (x < 5) {
			jpfIfCounter++;
			Verify.ignoreIf(jpfIfCounter > 1);
			z = -z;
		}
		Verify.ignoreIf(jpfIfCounter == 0);
		return z;
	}

	// The test driver
	public static void main(String[] args) {
		MyClass2 mc = new MyClass2();
		int x = mc.myMethod2(1, 2);
		Debug.printPC("\nMyClass2.myMethod2 Path Condition: ");
	}
}