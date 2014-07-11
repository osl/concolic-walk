package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings66 {
	static int field;

  public static void main (String[] args) {
	  String a = "ddd";
	  //test (a);
	  test("a");
	  //Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a){
	  if ("abc".substring(1).equals("bcd")) {
		  System.out.println("boo");
	  }
  }
  
}

