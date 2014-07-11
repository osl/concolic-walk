package gov.nasa.jpf.symbc;

public class ExSymExe7 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe7 inst = new ExSymExe7();
	  inst.test(x, y);
  }

  /*
   * test IFEQ (and ISUB) bytecodes (Note: javac compiles "!=" to IFEQ)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe7");
	  int y = 3;
	  z = x- y - 4;
	  if (z != 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (y != 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

