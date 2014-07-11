package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings03 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  test (a,b);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b) {
	  String c = a.trim();
	  int i = 0;
	  if (c.equals("aa")) {
		  System.out.println("bbb");
		  i++;
	  }
	  if (i == 0) {
		  throw new RuntimeException ();
	  }
  }

}

