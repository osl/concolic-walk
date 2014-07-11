package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestFloatSpecial1 extends FloatTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestFloatSpecial1.testFloatInvokeSpecial(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestFloatSpecial1 test = new TestFloatSpecial1();
      test.testFloatInvokeSpecial(11.0f, 21.0f);
    }
  }

  // "private" forces calls to use INVOKESPECIAL
  private void testFloatInvokeSpecial(float x, float y) {
    testFloat(x, y);
  }
}
