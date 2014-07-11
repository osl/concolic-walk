package gov.nasa.jpf.symbc;


public class ExSymExeTestAssignments {
	int field;
	
  public static void main (String[] args) {
	  int x = 3; 
	  test(x);
  }

  public static void test (int x) {
	  x=3;
	  if (x > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

  }
}

