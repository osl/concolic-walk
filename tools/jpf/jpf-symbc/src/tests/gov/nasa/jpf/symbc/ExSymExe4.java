package gov.nasa.jpf.symbc;

public class ExSymExe4 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe4 inst = new ExSymExe4();
	  inst.test(x, y);
  }

  /*
   * test IADD & IFLT bytecodes (Note: javac compiles ">=" to IFLT)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe4");
	  int y = 3;
	  x = z + y ;
	  if (z >= 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x >= 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

