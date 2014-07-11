package gov.nasa.jpf.symbc;

public class TestLazy {
	public static void main(String[] args) {
		(new TestLazy()).f(0, 0);
	}

	public void f(int a, int b) {
		Integer i = null;
		if (a < 5) {
			i = Integer.valueOf(4);
			i.floatValue();
		}
	}
}
