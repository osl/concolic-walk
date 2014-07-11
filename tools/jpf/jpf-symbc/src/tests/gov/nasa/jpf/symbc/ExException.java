package gov.nasa.jpf.symbc;

public class ExException {
	int zero() { return 0;}
	static int test(int secret) {
		ExException o = null;
		   if ( secret > 0 ) o = new ExException();
		   int i = o.zero();
		   return i;
	}
	public static void main(String[] args) {
		   System.out.println(0);
		   test(0);
		   System.out.println(1);
	}
	
}
