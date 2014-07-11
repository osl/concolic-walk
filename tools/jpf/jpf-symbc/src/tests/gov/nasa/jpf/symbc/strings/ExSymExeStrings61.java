package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.symbc.Debug;


public class ExSymExeStrings61 {
	static int field;

  public static void main (String[] args) {
	  String a="http://google.com/EasyChair";
	  String b = "bbb";
	  String c = "ccc";
	  String d = "ddd";
	  boolean result = test (a);
	  System.out.println(result);
	  Debug.printPC("This is the PC at the end:");
	  //a=a.concat(b);
	  
  }
  
  public static boolean test (String str){
	// (1) check that str contains "/" followed by anything not
	    // containing "/" and containing "EasyChair"
	    int lastSlash = str.lastIndexOf('/');
	    if (lastSlash < 0) {
	      return false;
	    }
	    
	    
	    String rest = str.substring(lastSlash + 1);
	    if (!rest.contains("EasyChair")) {
	      return false;
	    }
	    
	    
	 // (2) Check that str starts with "http://"
	    if (!str.startsWith("http://")) {
	      return false;
	    }
	    
	 // (3) Take the string between "http://" and the last "/".
	    // if it starts with "www." strip the "www." off
	    String t =
	        str.substring("http://".length(), lastSlash);
	    if (t.startsWith("www.")) {
	      t = t.substring("www.".length());
	    }
	    
	 // (4) Check that after stripping we have either "live.com"
	    // or "google.com"
	    if (!(t.equals("live.com")) && !(t.equals("google.com"))) {
	      return false;
	    }
	    //throw new RuntimeException("found it");
	    return true;
	    
  }

}

