package gov.nasa.jpf.symbc;


public class ExSymExe9 {
	static int field;
	
  public static void main (String[] args) {
	  int x = 3; /* we want to specify in an annotation that this param should be symbolic */

	  ExSymExe9 inst = new ExSymExe9();
	  field = 9;
	  inst.test(x, field);
	  //test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test ISUB & IFGE bytecodes
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe9");
	  int y = 3;
	  z = x - y - 4;
	  if (z < 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (y < 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}

