package gov.nasa.jpf.symbc;

public class ExSymExe5 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe5 inst = new ExSymExe5();
	  inst.test(x, y);
  }

  /*
   * test IADD & IFLT bytecodes (Note: javac compiles ">=" to IFLT)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe5");
	  int y = 3;
	  z = x + y + 4;
	  if (z >= 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (y >= 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

