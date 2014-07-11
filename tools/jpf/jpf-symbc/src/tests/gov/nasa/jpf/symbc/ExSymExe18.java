package gov.nasa.jpf.symbc;


public class ExSymExe18 {
	static int field;
	static int field2;
	
  public static void main (String[] args) {
	  int x = 3; /* we want to specify in an annotation that this param should be symbolic */

	  ExSymExe18 inst = new ExSymExe18();
	  field = 9;
	  inst.test(x, field, field2);
	  //test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test IF_ICMPLE & IMUL  bytecodes
   */
  public void test (int x, int z, int r) {
	  System.out.println("Testing ExSymExe18");
	  int y = 3;
	  x = x * r ;
	  z = z * x;
	  r = y * x;
	  if (z > x)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x > r)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}