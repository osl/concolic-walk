import rjc.RJCMain;

// Extend RJCMain to get access to the {@code controller} field.
public class Apollo extends RJCMain {
    public static void mainSymbolicWithFixedOutput(double in1_0, double in1_1, double in1_2,
						   double in2_0, double in2_1, double in2_2) {
	Apollo apolloPilot = new Apollo();
	double[] out1 = new double[1];
	double[] out2 = new double[2];
	apolloPilot.controller.MainSymbolic(in1_0, in1_1, in1_2, in2_0, in2_1, in2_2, out1, out2);
    }
}
