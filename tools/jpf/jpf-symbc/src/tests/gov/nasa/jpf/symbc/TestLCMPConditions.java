/**
 * 
 */
package gov.nasa.jpf.symbc;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestLCMPConditions extends InvokeTest {

	  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestLCMPConditions.twoConditions(sym);gov.nasa.jpf.symbc.TestLCMPConditions.twoConditions2(sym)";
	  private static final String DEBUG = "+symbolic.debug=true";	  
	  private static final String DP = "+symbolic.dp=coral";	 
	  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD, DEBUG, DP};

	  public static void main(String[] args) {
	    runTestsOfThisClass(args);
	  }

	  @Test
	  public void mainTest() {
	    if (verifyNoPropertyViolation(JPF_ARGS)) {
	    	//twoConditions(20000000000L);
	    	twoConditions2(20000000L);
	    	//threeConditions(20000000000L, 20000000000L);
	    }
	  }
	  
	  public static void twoConditions(long a) {
		 if(a >= 50L) {
			 if(a >= 51L) {
				 long c = a + 2;
			 }
		 }
	  }
	  
	  public static void twoConditions2(long a) {
			 if(a <= 51L) {
				 if(a <= 50L) {
					 long c = a + 2;
				 }
			 }
		  }
	  
	  public static long threeConditions(long a, long b) {
		  long c = 10L;
		  if(a > b) {
			  c = 500L;
		  }
		  else if(b > a) {
			  c = 7000L;
		  }
		  else if(b == a) {
			  c = 1000L;
		  }
		  return c;		  
	  }
}
