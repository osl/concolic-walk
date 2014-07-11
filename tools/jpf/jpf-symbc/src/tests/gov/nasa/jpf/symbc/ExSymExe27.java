package gov.nasa.jpf.symbc;

public class ExSymExe27 {
	
  public static void main (String[] args) {
	  int a = 3;
	  ExSymExe27 inst = new ExSymExe27();
	  int b = 8;
	  inst.test(a, b, a);
  }

  /*
   * test concrete = symbolic
   * (con#sym#sym)
   */
  public void test (int x, int y, int z) {
	  System.out.println("Testing ExSymExe27");
	  x = z;
	  y = z + x;
	  if (x < y)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (z > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}

