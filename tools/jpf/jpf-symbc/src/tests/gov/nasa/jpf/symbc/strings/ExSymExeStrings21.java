package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings21 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  String c = "ccc";
	  test (a,b, c);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b, String c) {
	  int i = 0;
	  if (a.equals(b)) {
		  System.out.println("aaa");
		  i++;
	  }
	  if (b.equals(c)) {
		  System.out.println("bbb");
		  i++;
	  }
	  if (i == 0) {
		  throw new RuntimeException();
	  }

  }

}

