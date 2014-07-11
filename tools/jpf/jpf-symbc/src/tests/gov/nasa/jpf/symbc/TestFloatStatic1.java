package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestFloatStatic1 extends FloatTest{
	private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestFloatStatic1.testFloatInvokeStatic(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      testFloatInvokeStatic(11.0f, 21.0f);
    }
  }

  //forces calls to INVOKESTATIC
  public static void testFloatInvokeStatic(float x, float y) {
    testFloat(x, y);
  }
}
