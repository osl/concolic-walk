package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestDoubleStatic1 extends DoubleTest{
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestDoubleStatic1.testDoubleInvokeStatic(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      testDoubleInvokeStatic(11.0, 21.0);
    }
  }

  //forces calls to INVOKESTATIC
  public static void testDoubleInvokeStatic(double x, double y) {
    testDouble(x, y);
  }
}
