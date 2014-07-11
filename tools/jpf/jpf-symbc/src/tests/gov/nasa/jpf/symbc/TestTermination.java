package gov.nasa.jpf.symbc;

import org.junit.Test;

public class TestTermination extends InvokeTest{
	static void test(int i, int j, int k) {
		//while (i <= 100 && j <= k) {
		if(i <=100 && j <=k) {
			int oldi = i;
			int oldj = j;
			int oldk = k;

			i = j;
			j = i + 1;
			k = k-1;
			if(oldi > i && oldk-oldj <= k-j)
				assert false;
			else
				System.out.println("here");
		}
	}
	
  private static final String SYM_METHOD = "+symbolic.method=gov.nasa.jpf.symbc.TestTermination.test(sym#sym#sym)";
  private static final String[] JPF_ARGS = {INSN_FACTORY, SYM_METHOD};

  public static void main(String[] args) {
    runTestsOfThisClass(args);
  }

  @Test
  public void mainTest() {
    if (verifyNoPropertyViolation(JPF_ARGS)) {
      TestTermination test = new TestTermination();
      test.test (0,0,0);
    }
  }

}
