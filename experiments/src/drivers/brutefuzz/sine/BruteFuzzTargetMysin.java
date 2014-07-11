public class BruteFuzzTargetMysin {
  private static concolic.MathSin sin = new concolic.MathSin();

  public static double static_mysin(double x) {
    return sin.mysin(x);
  }
}
