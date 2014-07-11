package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings60 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  String c = "ccc";
	  String d = "ddd";
	  test (a);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static boolean test (String a) {
	  if (!a.startsWith("hello")) {
		  return false;
	  }
	  int i = a.lastIndexOf('/');
	  if (i < 0) {
		  return false;
	  }
	  else {
		  throw new RuntimeException("found it");
	  }
  }

}

