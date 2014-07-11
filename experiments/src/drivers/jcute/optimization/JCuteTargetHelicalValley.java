import cute.Cute;

public class JCuteTargetHelicalValley {
  public static void main(String[] args){ 
    double x1 = cute.Cute.input.Double();
    double x2 = cute.Cute.input.Double();
    double x3 = cute.Cute.input.Double();
    Optimization.helicalValley(x1,x2,x3);
  }
}
