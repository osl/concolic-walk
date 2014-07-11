package concolic;

public class SimpleExample {

	public void test(int x, int y) {
			if (x >= 0 && x > y && y == Math.pow(x,2.0)) {
				System.out.println("S0");
			}
			else
				System.out.println("S2");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleExample pe = new SimpleExample();
		pe.test(2,10);
	}

}
