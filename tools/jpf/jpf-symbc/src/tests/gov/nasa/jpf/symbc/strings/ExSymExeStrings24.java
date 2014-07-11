package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings24 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  String c = "ccc";
	  String d = "ddd";
	  test (a,b, c);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b, String c) {
	  if (a.equals(b)) {
		  System.out.println("aaa");
	  }
	  if (a.startsWith ("aa")) {
		  System.out.println("bbb");
	  }
	  if (b.startsWith(c)) {
		  System.out.println("ccc");
	  }

  }

}

