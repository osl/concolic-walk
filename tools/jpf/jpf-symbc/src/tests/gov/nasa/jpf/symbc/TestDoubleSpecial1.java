package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestDoubleSpecial1 extends DoubleTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestDoubleSpecial1.testDoubleInvokeSpecial(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestDoubleSpecial1 test = new TestDoubleSpecial1();
      test.testDoubleInvokeSpecial(11.0, 21.0);
    }
  }

  // "private" forces calls to use INVOKESPECIAL
  private void testDoubleInvokeSpecial(double x, double y) {
    testDouble(x, y);
  }
}
