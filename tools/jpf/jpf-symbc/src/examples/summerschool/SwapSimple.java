package summerschool;

import gov.nasa.jpf.symbc.Debug;

public class SwapSimple {

	static void test(int x, int y) {
		System.out.println("Initial values:");
		System.out.println("x: "+Debug.getSymbolicIntegerValue(x));
		System.out.println("y: "+Debug.getSymbolicIntegerValue(y));
		
		if (x > y) { 
			x = x + y; 
			y = x - y;
			x = x - y;
			assert false;
		}
		
		if (x > y) 
			assert false;
		
		System.out.println("Final values:");
		System.out.println("x: "+Debug.getSymbolicIntegerValue(x));
		System.out.println("y: "+Debug.getSymbolicIntegerValue(y));
		
		
	}
	public static void main(String[] args) {	
		test(0,0);
	}
}
