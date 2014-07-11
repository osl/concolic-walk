/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.symbc.InvokeTest;
import gov.nasa.jpf.vm.Verify;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestCGOptimization extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.TestCGOptimization$TestSystem.testIF_ICMPEQ(sym#sym)";
	
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests;";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint/";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";
	
	private static final String SOLVER = "+symbolic.dp=choco";
	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT,
											  SOLVER
											  };

	
	public static void main(String[] args) {
		TestPrintSimpleSys testInvocation = new TestPrintSimpleSys();
		testInvocation.mainTest();		
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestSystem test = new TestSystem();
			test.driver(1);
		}
	}
	
	private class TestSystem {	
		
		public void testIFEQ(int cond) {
			if(cond != 0) {
				int b = 90 + 28 + 34 +34 +34+34+34;
				if(cond == 0) {
					int a = 2;
					a = 2+ 2;
					a = 3;
				}
				
			}
			int c = 2;
			c += 2;
		}
		
		public void driver(int length) {
			for(int i = 0; i < length; i++) {
				testIF_ICMPEQ(1,2);
			}
		}
		
		private int counter = 0;
		public void testIF_ICMPEQ(int cond, int cond2) {
			if(cond != 230) {
				int b = 90 + 28 + 34 +34 +34+34+34;
				if(cond == 230) {
					int a = 2;
					a = 2+ 2;
					a = 3;
				}
				
				if(cond2 > 200) {
					b = 2;
				} else {
					b = 100;
				}
				counter += b;
			}
			int c = 2;
			c += 2;
			counter += c;
		}
		
	}
}
