package gov.nasa.jpf.symbc;

public class BooleanTest extends InvokeTest {

  protected static String PC_x_1_y_0 = "x_1_SYMINT != CONST_0 && y_2_SYMINT == CONST_0";
  protected static String PC_x_1_y_1 = "x_1_SYMINT != CONST_0 && y_2_SYMINT != CONST_0";
  protected static String PC_x_0_y_0 = "x_1_SYMINT == CONST_0 && y_2_SYMINT == CONST_0";
  protected static String PC_x_0_y_1 = "x_1_SYMINT == CONST_0 && y_2_SYMINT != CONST_0";

  protected static void testBoolean(boolean x, boolean y) {
    // Note: "!y" compiles to IFEQ, so it creates a choice generator
    boolean z = !y;

    if (x) {
      assert pcMatches(PC_x_1_y_0) || pcMatches(PC_x_1_y_1) :
              makePCAssertString("TestBooleanSpecial1.testBoolean1 if (x == true)",
              "one of\n" + PC_x_1_y_0 + "\nor\n"
              + PC_x_1_y_1, TestUtils.getPathCondition());
      z = y;
    } else {
      assert pcMatches(PC_x_0_y_0) || pcMatches(PC_x_0_y_1) :
              makePCAssertString("TestBooleanSpecial1.testBoolean1 (x == false)",
              "one of\n" + PC_x_0_y_0 + "\nor\n"
              + PC_x_0_y_1, TestUtils.getPathCondition());
    }
    if (!z) {
      assert (pcMatches(PC_x_1_y_0) || pcMatches(PC_x_0_y_1)) :
              makePCAssertString("TestBooleanSpecial1.testBoolean1 (z == false)",
              "one of\n" + (PC_x_1_y_0 + " && "
              + PC_x_0_y_1) + "\n", TestUtils.getPathCondition());
      z = true;
    } else {
      assert (pcMatches(PC_x_1_y_1) || pcMatches(PC_x_0_y_0)) :
              makePCAssertString("TestBooleanSpecial1.testBoolean1 (z == true)",
              "one of\n" + (PC_x_1_y_1 + " && " + PC_x_0_y_0)
              + "\n", TestUtils.getPathCondition());
    }
  }
}
