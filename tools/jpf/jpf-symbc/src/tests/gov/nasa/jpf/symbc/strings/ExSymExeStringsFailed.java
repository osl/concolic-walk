package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;

public class ExSymExeStringsFailed {

	public static void main (String[] args) {
		  String a="aaa";
		  test (a);
		  Debug.printPC("This is the PC at the end:");
		  
	  }
	  
	  public static void test (String a) {
		  int i = 0;
		 
		  if(!a.contains("a")){ 
			  i++;
		  }
		  if (a.startsWith("a")) {
			  i++;
		  }
		  if (i == 2) {
			  throw new RuntimeException("");
		  }
	  }

}