package gov.nasa.jpf.symbc;

import gov.nasa.jpf.symbc.Symbolic;

/*
 * test method with no parameters and two symbolic methods called
 */
public class ExSymExe32 {
	
	@Symbolic ("false")
	static int global;
	@Symbolic ("false")
	int global2;
	@Symbolic ("false")
	boolean glob;
	
  public static void main (String[] args) {
	  System.out.println("Testing ExSymExe32");
	  int x = 2;  
	  ExSymExe32 inst = new ExSymExe32();
	  global = 9;
	  inst.global2 = 0;
	  int y = global*x;
	  inst.test(y,9);
	  inst.test1();
  }
  
  public void test (int x, int z) {
	  int y = 3;
	  x = x + z ;
	  z = z + y;
	  if (z > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }

  public void test1(){
	  int y = 3;
	  y = y + 1;
	  global = y;
	  if (y > 3)
		  System.out.println("BAR1");
	  else
		  System.out.println("BAR2");
  }
}
