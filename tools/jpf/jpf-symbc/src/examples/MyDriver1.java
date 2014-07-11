
import gov.nasa.jpf.symbc.Debug;

public class MyDriver1 {

    // The method whose parameters are marked as symbolic.
    private static void imposePreconditions(int x, int y) {
        MyClass1 mc = new MyClass1();

        // The preconditions
        if (-100 <= x  &&  x <= 100  &&  1 <= y  &&  y <= 3) {
            mc.myMethod(x, y);
            Debug.printPC("\nMyClass1.myMethod Path Condition: ");
        }
    }

    // The test driver
    public static void main(String[] args) {
        // Actual arguments are ignored when doing symbolic execution.
        imposePreconditions(1,2);
    }
}