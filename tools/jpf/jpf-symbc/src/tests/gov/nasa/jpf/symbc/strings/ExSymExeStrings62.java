package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings62 {
	static int field;

  public static void main (String[] args) {
	  String a = "ddd";
	  //test (a);
	  fixedtest("a");
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static void test (String str){
	  str = str.replace ('a', 'b');
	  if (str.contains("a")) {
		  throw new RuntimeException ("Gats");
	  }
	    
  }
  
  public static void fixedtest (String str){
	  if (str.contains("a") && str.length() < 3) {
		 str = str.replace ('a', 'b');
		 for (int i = 0; i < str.length(); i++) {
			 if (str.charAt(i) == 'a') {
				 throw new RuntimeException ("Gats");
			 }
		 }
	  }
	    
  }
  
  public static void buggedtest (String str){
	  if (str.contains("a") && str.length() < 3) {
		 str.replace ('a', 'b');
		 for (int i = 0; i < str.length(); i++) {
			 if (str.charAt(i) == 'a') {
				 throw new RuntimeException ("Gats");
			 }
		 }
	  }
	    
  }

}

