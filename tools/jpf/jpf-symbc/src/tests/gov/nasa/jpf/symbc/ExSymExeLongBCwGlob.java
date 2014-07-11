package gov.nasa.jpf.symbc;

public class ExSymExeLongBCwGlob {
	
	@Symbolic("true")
	static long staticGlobalLong = 10909;
	@Symbolic("true")
	long globalLong = 898989;
	@Symbolic("true")
	static int staticGlobalInt = 0;
	@Symbolic("true")
	int globalInt = 4;
	
  public static void main (String[] args) {
	  long x = 3;
	  long y = 5;
	  ExSymExeLongBCwGlob inst = new ExSymExeLongBCwGlob();
	  inst.test(x, y);
  }

  /*
   * test invokespeical, LCMP bytecode
   * using globals
   */
  
  private void test (long x, long z) { //invokespecial
	  
	  System.out.println("Testing ExSymExeLongBCwGlob");
	  
	  long a = x;
	  long b = z;
	  long c = staticGlobalLong; 

	  if ( globalLong > x)
		  System.out.println("branch globalLong > x");
	  else
		  System.out.println("branch globalLong <= x");
	  if (c < z)
		  System.out.println("branch c < z");
	  else
		  System.out.println("branch c >= z");
		  
  }
}