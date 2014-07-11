package gov.nasa.jpf.symbc;

public class IntTest extends InvokeTest {
// x > 1

  protected static String I1_PC1 = "# = 1\nx_1_SYMINT > CONST_1";
  //
  // (x <= 1)
  protected static String I1_PC2 = "x_1_SYMINT <= CONST_1";
  //
  // [(x > 1) && ((z := y) > 30)] || [(x < 1) && ((z := x+y) > 30)] || [(x == 1) && ((z := x+y) > 30)]
  protected static String I1_PC3 = "(y_2_SYMINT + x_1_SYMINT) > CONST_30";
  protected static String I1_PC4 = "y_2_SYMINT > CONST_30";
  //
  // [((z := x+y) < 30) && (x == 1)] || [(x < 1) && ((z := x+y) < 30)] ||
  // [(x < 1) && ((z := x+y) == 30)] || [(x == 1) && ((z := x+y) == 30)] ||
  // [(x > 1) && ((z := y) < 30)] || [(x > 1) && ((z := y) == 30)]
  protected static String I1_PC5 = "(y_2_SYMINT + x_1_SYMINT) <= CONST_30";
  protected static String I1_PC6 = "y_2_SYMINT <= CONST_30";

  protected static void testInt(int x, int y) {
    String pc = "";
    int z = x + y;

    if (x > 1) {
      assert pcMatches(I1_PC1) : makePCAssertString("TestIntSpecial1.testInt1 if x > 1", I1_PC1, TestUtils.getPathCondition());
      z = y;
    } else {
      assert pcMatches(I1_PC2) : makePCAssertString("TestIntSpecial1.testInt1 x <= 1",
              I1_PC2, TestUtils.getPathCondition());
    }
    pc = trimPC(TestUtils.getPathCondition());
    if (z > 30) {
      assert (pcMatches(joinPC(I1_PC3, pc)) || pcMatches(joinPC(I1_PC4, pc))) : makePCAssertString(
              "TestIntSpecial1.testInt1 z <= 30", "one of\n" + joinPC(I1_PC3, pc) + "\nor\n"
              + joinPC(I1_PC4, pc), TestUtils.getPathCondition());
      z = 91;
    } else {
      assert (pcMatches(joinPC(I1_PC5, pc)) || pcMatches(joinPC(I1_PC6, pc))) : makePCAssertString(
              "TestIntSpecial1.testInt1 z <= 30", "one of\n"
              + joinPC(I1_PC5, pc) + "\nor\n" + joinPC(I1_PC6, pc),
              TestUtils.getPathCondition());
    }
  }
}
