package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestIntSpecial1 extends IntTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestIntSpecial1.testIntInvokeSpecial(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestIntSpecial1 test = new TestIntSpecial1();
      test.testIntInvokeSpecial(11, 21);
    }
  }

  // "private" forces calls to use INVOKESPECIAL
  private void testIntInvokeSpecial(int x, int y) {
    testInt(x, y);
  }
}
