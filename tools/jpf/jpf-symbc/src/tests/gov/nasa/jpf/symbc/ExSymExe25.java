package gov.nasa.jpf.symbc;

public class ExSymExe25 {
	
  public static void main (String[] args) {
	  int a = 3;
	  ExSymExe25 inst = new ExSymExe25();
	  int b = 8;
	  inst.test(a, b, a);
  }

  /*
   * test IFLE & IADD bytecodes and mixture of symbolic and concrete parameters
   * (con#sym#sym)
   */
  public void test (int x, int y, int z) {
	  System.out.println("Testing ExSymExe25");
	  y = z + 1;
	  z = z + x;
	  x = x + 3;
	  if (z > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}

