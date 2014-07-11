package gov.nasa.jpf.symbc;

import org.junit.Test;


public class TestSwitch extends InvokeTest{
	public enum Y {Y1, Y2};
	
	void testSwitch1() {
		Y y = Y.Y1;
//		int x = 1;
//
//		switch (x) {
//		case 1: System.out.println(1); break;
//		case 2: System.out.println(2); break;
//		default: System.out.println("default: "); break;
//		}
		switch (y) {
//		case Y1: System.out.println(1); break;
		case Y2: System.out.println(2); break;
		default: System.out.println("default: "); break;
		}
	}

  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestSwitch.testSwitch1()";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestSwitch test = new TestSwitch();
      test.testSwitch1();
    }
  }

}
