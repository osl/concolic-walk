/**
 * 
 */
package gov.nasa.jpf.symbc.lazy;

import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.InvokeTest;

import org.junit.Test;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class TestLazy extends InvokeTest {
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.lazy.TestLazy.testMethod()";
		
	private static final String CLASSPATH_UPDATED = "+classpath=${jpf-symbc}/build/tests";
	private static final String LISTENER = "+listener = gov.nasa.jpf.symbc.heap.HeapSymbolicListener";
	private static final String DEBUG = "+symbolic.debug = true";
	private static final String LAZY = "+symbolic.lazy = on";

	private static final String[] JPF_ARGS = {INSN_FACTORY, 
											  LISTENER, 
											  CLASSPATH_UPDATED, 
											  SYM_METHOD,
											  DEBUG,
											  LAZY
											  };

	
	@Test
	public void testInstanceFieldPropagation () {
		if (verifyNoPropertyViolation(JPF_ARGS)) {
			TestLazy test = new TestLazy();
			test.testMethod();
		}
	}

	static class X {
		boolean pass;
	}
	
	X myX;
	
	public void testMethod() {
		myX = (X)Debug.makeSymbolicRef("SYMB", myX);
		if (myX != null){
			System.out.println("T: accessed global myX");
			if (!myX.pass){
				System.out.println("Gotcha!");
			}
		}
		Debug.printHeapPC("MSG");
	}
}
