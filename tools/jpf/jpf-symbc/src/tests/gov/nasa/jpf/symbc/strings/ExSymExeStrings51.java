package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings51 {
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
	  int i = 0;
	  if (a.length() > 3) {
		  i = i + 1;
		  System.out.println("aaa");
	  }
	  if (b.length() < 2) {
		  i = i + 1;
		  System.out.println("bbb");
	  }
	  if (b.contains(a)) {
		  i = i + 1;
		  System.out.println("boo");
	  }
	  if (i == 3) {
		  throw new RuntimeException ("This should not happen");
	  }
  }

}

