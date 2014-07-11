package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings09 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  test (a,b);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b) {
	  if (a.startsWith("abc")) {
		  System.out.println("bbb");
	  }
	  if (b.startsWith ("b")) {
		  System.out.println("ccc");
	  }
	  if (a.equals(b)) {
		  System.out.println("aaa");
	  }

  }

}

