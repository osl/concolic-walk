package gov.nasa.jpf.symbc;


public class ExSymExeTestClassFields {
	//@Symbolic("true")
	static int field;
    int field2;

  public static void main (String[] args) {
	  (new ExSymExeTestClassFields()).test();

  }
  public void test() {
	  	  if(field ==0 && field2 ==0)
			  System.out.println("br 0");
	  	  else
			  System.out.println("br 1");

  }

}

