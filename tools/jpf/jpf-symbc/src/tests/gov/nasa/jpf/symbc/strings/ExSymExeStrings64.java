package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings64 {
	static int field;

  public static void main (String[] args) {
	  String a = "ddd";
	  //test (a);
	  test("a", 1);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String a, int d){
	  int i = 0;
	  int len = a.length();
	  char c = a.charAt(i);
	  if (c == '<') {
		  System.out.println("int" + d);
		  if (i + 14 < len &&
					(a.charAt(i + 8) == '\"')
					) {
			  
		  	int idx = 0 + 9;
			
			//i = idxEnd + 4;
		  }
	  }
	    
  }
  
}

