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
public class TestConcurrentVisualization extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.TestConcurrentVisualization$ConcCompute.run()";
	
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests;${jpf-symbc}/../SARTSBenchmarks/bin;${jpf-symbc}/../scjNoRelativeTime/bin;${jpf-symbc}/../JOP/bin";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	//private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.SymbolicListener";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";
	private static final String DEBUG = "+symbolic.debug=true";
	
	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT,
											  DEBUG
											  };
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			Thread t1 = new Thread(new ConcCompute());
			Thread t2 = new Thread(new Racer());
			
			t1.start();
			t2.start();
		}
	}
	private static boolean cond = false;
	
	static class ConcCompute implements Runnable {

		@Override
		public void run() {
			if(TestConcurrentVisualization.cond) {
				int b = 3 + 2;
			} else {
				int b = 3 + 2;
				b = 3 + 2;
				b = 3 + 2;
			}
		}
	}
	
	static class Racer implements Runnable {

		@Override
		public void run() {
			TestConcurrentVisualization.cond = true;			
		}
		
	}
}
