package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings45 {
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
	  if (b.startsWith("bol")) {
		  System.out.println("aah!");
	  }
	  if (b.indexOf(a) == 2) {
		  System.out.println("boo");
	  }
  }

}

