package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestFloatVirtual1 extends FloatTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestFloatVirtual1.testFloatInvokeVirtual(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestFloatVirtual1 test = new TestFloatVirtual1();

      test.testFloatInvokeVirtual(11.0f, 21.0f);
    }
  }

  //forces calls to INVOKEVIRTUAL
  void testFloatInvokeVirtual(float x, float y) {
    testFloat(x, y);
  }
}
