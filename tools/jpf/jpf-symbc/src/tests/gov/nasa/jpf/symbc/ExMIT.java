package gov.nasa.jpf.symbc;

public class ExMIT {
	public static void main (String[] args) {
		foo(0);
	}
	public static int foo(int i) {
	       if (2 * (i + 1)  == 10)
	           return 1;
	       return 0;
	}
	public static int boo(float i) {
	       if ((i + 1)  * 2 > 10)
	           return 1;
	       return 0;
	}
}
