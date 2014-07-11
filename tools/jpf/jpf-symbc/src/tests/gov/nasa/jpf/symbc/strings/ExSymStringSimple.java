package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;

public class ExSymStringSimple {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = Debug.makeSymbolicString("symtest");
		System.out.println("concrete and symbolic values of test ");
		System.out.println(test);

	}

}
