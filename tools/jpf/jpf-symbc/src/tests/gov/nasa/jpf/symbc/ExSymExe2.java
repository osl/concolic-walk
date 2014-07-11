package gov.nasa.jpf.symbc;

public class ExSymExe2 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe2 inst = new ExSymExe2();
	  inst.test(x, y);
  }

  /*
   * test IINC & IFLE bytecodes (Note: javac compiles ">" to IFLE)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe2");
	  z++;
	  x = ++z;
	  if (x > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (z > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

