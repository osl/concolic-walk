package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestDoubleVirtual1 extends DoubleTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestDoubleVirtual1.testDoubleInvokeVirtual(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestDoubleVirtual1 test = new TestDoubleVirtual1();
      test.testDoubleInvokeVirtual(11.0, 21.0);
    }
  }

  //forces calls to INVOKEVIRTUAL
  void testDoubleInvokeVirtual(double x, double y) {
    testDouble(x, y);
  }
}
