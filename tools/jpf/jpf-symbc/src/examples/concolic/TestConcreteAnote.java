package concolic;

import gov.nasa.jpf.symbc.Concrete;

public class TestConcreteAnote {

	public static void runSymbolic(int x, double y) {
		if(x > 10) {
			//int y = runConcrete(x);
			if(y == runConcrete(y)) {
				//System.out.println("y is zero");
			}
			System.out.println("x > 10");
		} else {
			System.out.println("x <= 10");
		}
	}
	
	@Concrete("true")
	public static double runConcrete(double z) {
		System.out.println("running concrete");
		return 0.0;
	}
	
	public static void main(String[] args) {
		runSymbolic(1,0);
	}
}