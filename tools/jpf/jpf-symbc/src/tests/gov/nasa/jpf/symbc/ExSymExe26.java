package gov.nasa.jpf.symbc;

public class ExSymExe26 {
	
  public static void main (String[] args) {
	  int a = 3;
	  ExSymExe26 inst = new ExSymExe26();
	  int b = 8;
	  inst.test(a, b, a);
  }

  /*
   * test symbolic = concrete
   * (con#sym#sym)
   */
  public void test (int x, int y, int z) {
	  System.out.println("Testing ExSymExe26");
	  y = x;
	  z++;
	  if (z > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (y > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}

