package gov.nasa.jpf.symbc;

import gov.nasa.jpf.symbc.Symbolic;

public class ExSymExe31 {
	
	@Symbolic ("true")
	static int global;
	@Symbolic ("true")
	static int global2;
	@Symbolic ("true")
	boolean glob = true;
	
  public static void main (String[] args) {
	  System.out.println("Testing ExSymExe31:test");
	  ExSymExe31 inst = new ExSymExe31();
	  global = 9;
	  inst.global2 = 0;
	  inst.test2(4);
//	  inst.test(global, inst.global2);
//	  inst.subTest(global,inst.glob);
  }
  
  /*
   * test @Symbolic annotation and support for treating fields symbolically
   */
  //tested below
  //@Preconditions ("glob_SYM_FIELD + 2 != x_SYM + 8 + global2_SYM_FIELD ")
  //@Preconditions ("glob_SYM_FIELD - 2 != x_SYM + 8 + global2_SYM_FIELD && glob_SYM_FIELD != 7")
  //@Preconditions ("glob_SYM_FIELD - 2 > x_SYM - 8 + global2_SYM_FIELD && global2_SYM_FIELD + 2 != x_SYM")
  @Preconditions ("global_SYM_STATIC_FIELD - 2 > x_SYM - 8 + global2_SYM_STATIC_FIELD && global2_SYM_STATIC_FIELD + 2 != x_SYM")
  //untested below
  //@Preconditions ("x_SYM + 3 != glob_SYM_FIELD + 1 + global2_SYM_FIELD")
  //@Preconditions ("x_SYM + 3 != glob_SYM_FIELD + 1 + global2_SYM_FIELD && global2_SYM_FIELD + 2 != x_SYM")
  
  private void test2(int x) {
	  global2 = x;
	  if (x > global)
	 // if (x < global2)
		  System.out.println("branch B1");
	  else
		  System.out.println("branch B2");
  }
  
  public void test (int x, int y) {
	  int z = global2;
	  int loc = x + y;
	  if (loc == z)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (loc == global)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
  
  public void subTest(int paramA, boolean done){
	  boolean found = !done;
	  if (paramA > global2){
		  System.out.println("branch BAR1");
		  global++;
	  }else{
		  System.out.println("branch BAR2");
		  global--;
	  }
	  if (found == glob)
		  System.out.println("branch FAR1");
	  else
		  System.out.println("branch FAR2");
  }
}
