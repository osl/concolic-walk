/**
 * 
 */
package gov.nasa.jpf.symbc;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestMethodInvocation extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestMethodInvocation.testMethodInvocation(sym#sym)";
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.SymbolicListener";
	private static final String[] JPF_ARGS = {INSN_FACTORY, LISTENER, SYM_METHOD};
	
	public static void main(String[] args) {
		TestMethodInvocation testInvocation = new TestMethodInvocation();
		testInvocation.mainTest();		
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestMethodInvocation test = new TestMethodInvocation();
			test.testMethodInvocation(1, 2);
		}
	}
	
	public void testMethodInvocation(int x, int y) {
		int simpleResult = virtualCallSimpleComputation(x, y);
		int newRes = staticCallComputation(simpleResult);
	}
	
	private int virtualCallSimpleComputation(int a, int b) {
		return a + b;
	}
	
	private static int staticCallComputation(int input) {
		int res = 0;
		if(input <= 10) {
			res = input;
		} else {
			res = input + 3;
		}
		return res;		
	}
	
}
