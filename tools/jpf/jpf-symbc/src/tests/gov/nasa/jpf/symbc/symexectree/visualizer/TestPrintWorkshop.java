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
public class TestPrintWorkshop extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.symexectree.visualizer.SimpleSys.compAB(sym#sym)";
		
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests";
	
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.symexectree.visualizer.SymExecTreeVisualizerListener";
	private static final String OUTPUTPATH = "+symbolic.visualizer.basepath = ${jpf-symbc}/prettyprint";
	private static final String FORMAT = "+symbolic.visualizer.outputformat = pdf";
	
	
	
	//remember the nosolver option is like a cfg traversal!
	
	//private static final String SOLVER = "+symbolic.dp=no_solver";
	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  OUTPUTPATH,
											  FORMAT
											  };

	
	public static void main(String[] args) {
		TestPrintWorkshop testInvocation = new TestPrintWorkshop();
		testInvocation.mainTest();		
	}
	
	@Test
	public void mainTest() {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			SimpleSys test = new SimpleSys();
			test.compAB(2, 2);
		}
	}
}
