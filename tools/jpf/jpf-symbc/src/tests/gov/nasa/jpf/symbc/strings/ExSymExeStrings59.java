package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings59 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  String c = "ccc";
	  String d = "ddd";
	  test (a, 1);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, int x) {
	  if (x >= 0) {
		  x = 0;
		  for (; x < 10; x ++) {
			  if (a.charAt(x) == 'a'
				  &&
				  a.charAt(x + 1) == 'b'
				  &&
				  a.charAt(x + 2) == 'c') {
				  System.out.println("bla");
			  }
			  
		  }
	  }
  }

}

