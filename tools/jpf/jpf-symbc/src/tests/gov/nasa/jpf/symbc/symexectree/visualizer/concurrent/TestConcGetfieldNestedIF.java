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
public class TestConcGetfieldNestedIF extends InvokeTest {
private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.concurrent.TestConcGetfieldNestedIF$ConcCompute.run()";
	
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
		ConcCompute conc = new ConcCompute();
		
		Thread t1 = new Thread(conc);
		Thread t2 = new Thread(new Racer(conc));
		t1.start();
		t2.start();
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestConcGetfieldNestedIF.main(null);
		}
	}
	
	static class ConcCompute implements Runnable {
		public boolean cond = false;
		private boolean cond2 = false; // Even though it is not the case, cond2 WILL be considered shared!
		
		@Override
		public void run() {
			if(cond) {
				System.out.println("Cond is true");
				if(cond2) {
					int a = 2;
				}
				int ta = 3 + 2;
			}else {
				System.out.println("Cond is false");
				cond2 = true;
				if(cond2) {
					int d = 2;
				}
				int b = 4 + 2;
				b = 4 + 2;
			}
		}
	}
	
	static class Racer implements Runnable {

		private ConcCompute conc;
		
		public Racer(ConcCompute conc) {
			this.conc = conc;
		}
		
		@Override
		public void run() {
			this.conc.cond = true;
		}
		
	}
}
