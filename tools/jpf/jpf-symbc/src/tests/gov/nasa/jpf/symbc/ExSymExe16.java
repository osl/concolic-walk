package gov.nasa.jpf.symbc;


public class ExSymExe16 {
	static int field;
	
  public static void main (String[] args) {
	  int x = 3; /* we want to specify in an annotation that this param should be symbolic */

	  ExSymExe16 inst = new ExSymExe16();
	  field = 9;
	  inst.test(x, field);
	  //test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test IFNE (and ISUB) bytecodes 
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe16");
	  int y = 3;
	  x = z - y ;
	  if (z == 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x == 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}

