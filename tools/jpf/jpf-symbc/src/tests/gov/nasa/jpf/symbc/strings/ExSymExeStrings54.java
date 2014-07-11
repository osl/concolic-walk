package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings54 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  String c = "ccc";
	  String d = "ddd";
	  test (a,b, 1);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b, int x) {
	  int y = 0;
	  int i = y + 9;
	  //if (a.substring(2,5).equals("abc")) {
	  if (a.indexOf("ab", 1) > -1) {
		  System.out.println("aa");
	  }
  }

}

