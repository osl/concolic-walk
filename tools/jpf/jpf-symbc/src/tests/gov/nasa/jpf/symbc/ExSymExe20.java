package gov.nasa.jpf.symbc;


public class ExSymExe20 {
	static int field;
	static int field2;
	
  public static void main (String[] args) {
	  int x = 3; /* we want to specify in an annotation that this param should be symbolic */

	  ExSymExe20 inst = new ExSymExe20();
	  field = 9;
	  inst.test(x, field, field2);
	  //test(x,x);
  }
  /* we want to let the user specify that this method should be symbolic */

  /*
   * test IF_ICMPLT, IADD & ISUB  bytecodes
   */
  public void test (int x, int z, int r) {
	  System.out.println("Testing ExSymExe20");
	  int y = 3;
	  r = x + z;
	  x = z - y ;
	  z = r;
	  if (z >= x)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x >= r)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");

	  //assert false;
  }
}
