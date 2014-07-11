package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestIntStatic1 extends IntTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestIntStatic1.testIntInvokeStatic(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      testIntInvokeStatic(11, 21);
    }
  }

  //forces calls to INVOKESTATIC
  public static void testIntInvokeStatic(int x, int y) {
    testInt(x, y);
  }
}
