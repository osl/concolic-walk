/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.symbc.InvokeTest;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestFloatComparison extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.TestFloatComparison.visualizeFloatCmp(sym#sym)";
	
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	//private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.SymbolicListener";
	//private static final String DEBUG = "+symbolic.debug = true";
	private static final String MINFLOAT = "+symbolic.min_double = 0.0";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";

	private static final String SOLVER = "+symbolic.dp=coral";
	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT,
											  SOLVER,
											  MINFLOAT,
											  LISTENER
											  };

	
	public static void main(String[] args) {
		TestPrintLoopSys testInvocation = new TestPrintLoopSys();
		testInvocation.mainTest();		
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			visualizeFloatCmp(20.0f, 30.0f);
		}
	}
	
	public static void visualizeFloatCmp(float a, float b) {
		if(a >= b) {
			if(a >= b) {
				if(b == a) {
					float s = a + b;
				}
				float c = a + b;
			}
		}

	}
}
