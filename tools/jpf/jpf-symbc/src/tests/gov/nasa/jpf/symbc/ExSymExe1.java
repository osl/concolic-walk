package gov.nasa.jpf.symbc;

public class ExSymExe1 {

  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe1 inst = new ExSymExe1();
	  inst.test(x, y);
  }

  /*
   * test IINC & IFLE bytecodes (Note: javac compiles ">" to IFLE)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe1");
	  x = z++ ;
	  if (z > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

