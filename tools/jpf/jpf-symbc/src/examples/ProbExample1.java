import gov.nasa.jpf.symbc.Debug;


public class ProbExample1 {

	public static void calcProb() {
		
	}
	
	public static int test1(int x, int y) {		
		if (x > 0) {
			calcProb();
			if (x < y) {
				calcProb();
				return 5;
			}
			else {
				calcProb();
				return y;
			}
		}
		else if (x < 0){
			if (x < y) {
				calcProb();
				return 7;
			}
			else {
				calcProb();
				return x;
			}
		}
		y = y + 1;
		if (y > 0) {
			calcProb();
		}
		return 0;
		
	}
	
	public static int test(int x, int y, int z) {		
		if (x > z) {
		   //calcProb();	
		}
		if (z > 0) {
			//calcProb();
		}
		System.out.println("now");
		if (y > 0) {
			System.out.println("now1");
			
			//calcProb();
		}
		return 0;
		
	}
	
	public static void main(String[] args) {
		test(1,1,1);
	}

}
