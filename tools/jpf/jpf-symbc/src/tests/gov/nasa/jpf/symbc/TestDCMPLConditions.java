/**
 * 
 */
package gov.nasa.jpf.symbc;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestDCMPLConditions extends InvokeTest {

	  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestDCMPLConditions.twoConditions(sym#sym);gov.nasa.jpf.symbc.TestDoubleConditions.threeConditions(sym#sym)";
	  private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.SymbolicListener";
	  private static final String DEBUG = "+symbolic.debug=true";	  
	  private static final String DP = "+symbolic.dp=coral";	  
	  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD, LISTENER, DEBUG, DP};

	  public static void main(String[] args) {
	    runTestsOfThisClass(args);
	  }

	  @Test
	  public void mainTest() {
	    if (verifyNoPropertyViolation(JPF_ARGS)) {
	    	twoConditions(20.00d, 20.00d);
	    	threeConditions(20.00d, 20.00d);
	    }
	  }
	  
	  public static double twoConditions(double a, double b) {
		  if(a >= 5.00d) {
			  if(a >= 6.00d) {
				  return b;
			  }
		  }
		  return a;		  
	  }
	  
	  public static double threeConditions(double a, double b) {
		  double c = 10.0d;
		  if(a > b) {
			  c = 10.0d;
		  }
		  if(b > a) {
			  c = 10.0d;
		  }
		  if(b == a) {
			  c = 10.0d;
		  }
		  return c;		  
	  }
}
