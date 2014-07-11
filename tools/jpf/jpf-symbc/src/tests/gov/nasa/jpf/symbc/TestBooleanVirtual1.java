package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestBooleanVirtual1 extends BooleanTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestBooleanVirtual1.testBooleanInvokeVirtual(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestBooleanVirtual1 test = new TestBooleanVirtual1();

      test.testBooleanInvokeVirtual(true, false);
    }
  }

  //forces calls to INVOKEVIRTUAL
  void testBooleanInvokeVirtual(boolean x, boolean y) {
    testBoolean(x, y);
  }
}
