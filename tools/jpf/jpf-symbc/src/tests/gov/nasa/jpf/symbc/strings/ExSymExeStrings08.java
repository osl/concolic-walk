package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings08 {
	static int field;

  public static void main (String[] args) {
	  String a="aab";
	  String b = "bbb";
	  int c = 1;
	  test (a,b,c);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b, int c) {
	  if (a.charAt(1) == c) {
		  System.out.println("a");
	  }
	  
  }

}

