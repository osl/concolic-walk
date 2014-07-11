package gov.nasa.jpf.symbc.strings;


public class ExSymExeStrings {
	static int field;

  public static void main (String[] args) {
	  String a="aaa";
	  String b = "bbb";
	  //a=a.concat(b);
	  a = a + b + a;
	  System.out.println("a "+a);
	  if (a == null)
		  System.out.println("branch aaa!=bbb");
	  else
		  System.out.println("branch aaa==bbb");

  }

}

