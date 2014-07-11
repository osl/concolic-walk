import cute.Cute;

public class JCuteTargetMysin {
  public static void main(String[] args){ 
    double arg = cute.Cute.input.Double();
    concolic.MathSin sin = new concolic.MathSin();
    sin.mysin(arg);
  }
}
