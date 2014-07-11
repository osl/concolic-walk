import cute.Cute;

public class JCuteTargetBeale {
  public static void main(String[] args){ 
    double x1 = cute.Cute.input.Double();
    double x2 = cute.Cute.input.Double();
    Optimization.beale(x1,x2);
  }
}
