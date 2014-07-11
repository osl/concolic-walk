package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings65 {
	static int field;

  public static void main (String[] args) {
	  String a = "ddd";
	  //test (a);
	  test("a", 10);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, int d){
	  String b = String.valueOf(d);
	  if (d < 5 && b.substring(1).equals("10")) {
		  System.out.println("here");
		  throw new RuntimeException();
	  }
  }
  
}

