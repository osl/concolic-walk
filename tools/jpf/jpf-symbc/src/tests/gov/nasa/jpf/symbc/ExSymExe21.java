package gov.nasa.jpf.symbc;


public class ExSymExe21 {
	static int field;
	static int field2;
	
  public static void main (String[] args) {
	  int x = 3; /* we want to specify in an annotation that this param should be symbolic */

	  ExSymExe21 inst = new ExSymExe21();
	  field = 9;
	  inst.test(x, field, field2);
	  //test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test IF_ICMPLT, IADD & ISUB  bytecodes
   */
  public void test (int x, int z, int r) {
	  System.out.println("Testing ExSymExe21");
	  int y = 3;
	  r = x + z;
	  z = x - y - 4;
	  if (r >= 99)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x >= z)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;

  }
}
