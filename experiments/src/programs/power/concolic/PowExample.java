package concolic;

public class PowExample {

	public static void test(int x, int y) {
		int path = 0;
		if (x > 0) {
		    // Changed from
		    // if (y == Math.pow(x, 2.0)) {
		    // to avoid double--int conversion (pdinges)
			if (y == x*x) {
				System.out.println("Solved S0");
				path = 1;
			}
			else {
				System.out.println("Solved S1");
				path = 2;
			}
			if (y > 8) {
			//if (x > 1 && y > 3) {
				if (path == 1)
					System.out.println("Solved S0;S3");
				if (path == 2)
					System.out.println("Solved S1;S3");
			}
			else {
				if (path == 1)
					System.out.println("Solved S0;S4");
				if (path == 2)
					System.out.println("Solved S1;S4");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test(2,10);
	}

}
