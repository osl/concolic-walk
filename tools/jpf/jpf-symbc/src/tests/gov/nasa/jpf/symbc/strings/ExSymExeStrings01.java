package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings01 {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  test (a,b);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, String b) {
	  int i = 0;
	  if (a.startsWith("a")) {
		  i++;
	  }
	  if (b.startsWith ("b")) {
		  i++;
	  }
	  if (a.length() + 1 < b.length()) {
		  System.out.println("aaa");
	  }
	  if (i == 2) {
		  throw new RuntimeException("");
	  }
  }

}

