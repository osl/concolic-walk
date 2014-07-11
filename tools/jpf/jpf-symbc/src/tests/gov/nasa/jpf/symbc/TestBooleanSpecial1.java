package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestBooleanSpecial1 extends BooleanTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestBooleanSpecial1.testBooleanInvokeSpecial(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestBooleanSpecial1 test = new TestBooleanSpecial1();
      test.testBooleanInvokeSpecial(true, false);
    }
  }

  // "private" forces calls to use INVOKESPECIAL
  private void testBooleanInvokeSpecial(boolean x, boolean y) {
    testBoolean(x, y);
  }
}
