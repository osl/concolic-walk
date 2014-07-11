/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer.concurrent;

import gov.nasa.jpf.symbc.InvokeTest;
import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestConcGetstatic extends InvokeTest {
private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.concurrent.TestConcGetstatic$ConcCompute.run()";
	
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests;${jpf-symbc}/../SARTSBenchmarks/bin;${jpf-symbc}/../scjNoRelativeTime/bin;${jpf-symbc}/../JOP/bin";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	//private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.singlethreadanalysis.SingleThreadListener";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";
	private static final String DEBUG = "+symbolic.debug = true";

	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT,
											  DEBUG
											  };

	
	public static void main(String[] args) {
		TestClass testClass = new TestClass();
		Thread t1 = new Thread(new ConcCompute());
		ConcCompute.testClass = testClass;
		//Thread t2 = new Thread(new Racer());
		testClass.field = 20;
		t1.start();
		//t2.start();
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestConcGetstatic.main(null);
		}
	}
	public static boolean cond = false;
	
	
	static class ConcCompute implements Runnable {
		
		public static TestClass testClass;
		
		public ConcCompute() {

		}
		
		@Override
		public void run() {
			if(testClass.field > 20) {
				System.out.println("Cond is true");
				int ta = 3 + 2;
			}else {
				System.out.println("Cond is false");
				int b = 4 + 2;
				b = 4 + 2;
			}
		}
	}
	
	static class TestClass {
		public int field;
	}
	
	static class Racer implements Runnable {
		
		@Override
		public void run() {
			cond = true;
		}		
	}
}
