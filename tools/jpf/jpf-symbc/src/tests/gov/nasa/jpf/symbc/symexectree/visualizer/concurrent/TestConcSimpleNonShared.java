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
public class TestConcSimpleNonShared extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.concurrent.TestConcSimpleNonShared$Computation.run()";
	
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests;${jpf-symbc}/../SARTSBenchmarks/bin;${jpf-symbc}/../scjNoRelativeTime/bin;${jpf-symbc}/../JOP/bin";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	//private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.singlethreadanalysis.SingleThreadListener";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";
	//private static final String DEBUG = "+symbolic.debug = true";
	private static final String SHAREDPOLICY = "+vm.por.shared.class = gov.nasa.jpf.vm.GlobalTrackingPolicy";

	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT,
										//	  DEBUG,
										//	  SHAREDPOLICY
											  };

	
	public static void main(String[] args) {
		Computation comp = new Computation();
		Thread t1 = new Thread(comp);
		t1.start();
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestConcSimpleNonShared.main(null);
		}
	}
	
	static class Computation implements Runnable {
		private boolean cond = false;
		
		@Override
		public void run() {
			int a = 0;
			if(cond) {
				a = 2;
			} else {
				a = 4;
			}
		}
	}
}
