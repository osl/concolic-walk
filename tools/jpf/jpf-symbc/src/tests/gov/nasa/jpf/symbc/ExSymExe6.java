package gov.nasa.jpf.symbc;

public class ExSymExe6 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe6 inst = new ExSymExe6();
	  inst.test(x, y);
  }

  /*
   * test IFEQ (and ISUB) bytecodes (Note: javac compiles "!=" to IFEQ)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe6");
	  int y = 0;
	  x = z - y ;
	  if (z != 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x != 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

