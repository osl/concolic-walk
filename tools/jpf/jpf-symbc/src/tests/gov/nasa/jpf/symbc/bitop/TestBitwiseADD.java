package gov.nasa.jpf.symbc.bitop;

public class TestBitwiseADD {
	
	public void test(int x, int y) {
		int z = x + y;
		if(z < (x | 18)) {
			System.out.println("Branch one");
		} else {
			System.out.println("Branch two");
		}
	}
	
	public void test(int x) {
		if(x > 10) {
			System.out.println("Second function");
		}
	}
	
	public static void main(String[] args) {
		TestBitwiseADD add = new TestBitwiseADD();
		add.test(0, 0);
		add.test(1);
	}
	
}