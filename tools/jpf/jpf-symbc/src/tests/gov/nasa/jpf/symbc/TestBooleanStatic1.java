package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestBooleanStatic1 extends BooleanTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestBooleanStatic1.testBooleanInvokeStatic(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      testBooleanInvokeStatic(true, false);
    }
  }

  //forces calls to INVOKESTATIC
  public static void testBooleanInvokeStatic(boolean x, boolean y) {
    testBoolean(x, y);
  }
}
