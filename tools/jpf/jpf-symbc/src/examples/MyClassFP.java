
import gov.nasa.jpf.symbc.Debug;

public class MyClassFP {
    public double myMethodFP(double x, double y) {
        double z = x + y;
        if (z > 0.0) {
            z = 1.0;
        } else {
            z = z - x;
        }
        z = 2.0 * z;
        return z;
    }

    // The test driver
    public static void main(String[] args) {
        MyClassFP mc = new MyClassFP();
        double x = mc.myMethodFP(1.0, 22.0);
        Debug.printPC("\nMyClassFP.myMethodFP Path Condition: ");
    }
}