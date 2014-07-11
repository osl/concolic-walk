import cute.Cute;

public class JCuteTargetPowell {
  public static void main(String[] args){ 
    double x1 = cute.Cute.input.Double();
    double x2 = cute.Cute.input.Double();
    Optimization.powell(x1,x2);
  }
}
