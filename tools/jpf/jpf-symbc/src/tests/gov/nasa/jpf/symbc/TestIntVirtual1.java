package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestIntVirtual1 extends IntTest {

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestIntVirtual1.testIntInvokeVirtual(sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestIntVirtual1 test = new TestIntVirtual1();
      test.testIntInvokeVirtual(11, 21);
    }
  }

  //forces calls to INVOKEVIRTUAL
  void testIntInvokeVirtual(int x, int y) {
    testInt(x, y);
  }
}
