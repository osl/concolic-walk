package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings05 {
	static int field;

  public static void main (String[] args) {
	  String a="aab";
	  String b = "bbb";
	  test (a,b);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b) {
	  if (a.startsWith("aa")) {
		  
	  }
	  if (a.indexOf("b") > 0) {
		  System.out.println("b");
	  }
	  
  }

}

