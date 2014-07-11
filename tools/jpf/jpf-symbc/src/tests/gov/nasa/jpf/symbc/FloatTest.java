package gov.nasa.jpf.symbc;

public class FloatTest extends InvokeTest {
  // x > 1.1f

  protected static String PC1 = "# = 1\nx_1_SYMREAL > CONST_1.100000023841858";
  //
  // (x <= 1.1f)
  protected static String PC2 = "x_1_SYMREAL < CONST_1.100000023841858";
  protected static String PC10 = "x_1_SYMREAL > CONST_1.100000023841858";
  protected static String PC3 = "CONST_1.100000023841858 == x_1_SYMREAL";
  //
  // [(x > 1.1f) && ((z := y) > 30.0f)] || [(x < 1.1f) && ((z := x+y) > 30.0f)] || [(x == 1.1f) && ((z := x+y) > 30.0f)]
  protected static String PC4 = "(x_1_SYMREAL + y_2_SYMREAL) > CONST_30.0";
  protected static String PC5 = "y_2_SYMREAL > CONST_30.0";
  //
  // [((z := x+y) < 30.0f) && (x == 1.1f)] || [(x < 1.1f) && ((z := x+y) < 30.0f)] ||
  // [(x < 1.1f) && ((z := x+y) == 30.0f)] || [(x == 1.1f) && ((z := x+y) == 30.0f)] ||
  // [(x > 1.1f) && ((z := y) < 30.0f)] || [(x > 1.1f) && ((z := y) == 30.0f)]
  protected static String PC6 = "CONST_30.0 == (x_1_SYMREAL + y_2_SYMREAL)";
  protected static String PC7 = "(x_1_SYMREAL + y_2_SYMREAL) < CONST_30.0";
  protected static String PC8 = "y_2_SYMREAL < CONST_30.0";
  protected static String PC9 = "CONST_30.0 == y_2_SYMREAL";

  protected static void testFloat(float x, float y) {
    String pc = "";
    float z = x + y;

    if (x > 1.1f) {
      assert pcMatches(PC1) : makePCAssertString("TestFloatSpecial1.testFloat1 if x > 1.1f", PC1, TestUtils.getPathCondition());
      z = y;
    } else {
      assert (pcMatches(PC2) || pcMatches(PC3)) : makePCAssertString("TestFloatSpecial1.testFloat1 x <= 1.1f",
              "either\n" + PC2 + "\nor\n" + PC3, TestUtils.getPathCondition());
    }
    pc = trimPC(TestUtils.getPathCondition());
    if (z > 30.0f) {
      assert (pcMatches(joinPC(PC4, pc)) || pcMatches(joinPC(PC5, pc))) : makePCAssertString(
              "TestFloatSpecial1.testFloat1 z <= 30.0f", "one of\n" + joinPC(PC4, pc) + "\nor\n"
              + joinPC(PC5, pc), TestUtils.getPathCondition());
      z = 91.0f;
    } else {
      assert (pcMatches(PC2) || pcMatches(PC3) || pcMatches(PC10)) : makePCAssertString(
              "TestFloatSpecial1.testFloat1 z <= 30.0f", "one of\n" +PC2 + "\nor\n" + PC3 + "\nor\n" + PC10
              ,
              TestUtils.getPathCondition());
    }
  }
}
