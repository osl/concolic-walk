package gov.nasa.jpf.symbc;

public class ExSymExe29 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe29 inst = new ExSymExe29();
	  inst.test(x, y, 9);
  }

  /*
   * test IF_ICMPEQ  bytecode  (Note: javac compiles "!=" to IF_ICMPEQ)
   */
  public void test (int x, int z, int r) {
	  System.out.println("Testing ExSymExe29");
	  if (z != x)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x != r)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}