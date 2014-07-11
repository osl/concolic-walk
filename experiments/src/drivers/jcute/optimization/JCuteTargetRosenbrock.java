import cute.Cute;

public class JCuteTargetRosenbrock {
  public static void main(String[] args){ 
    double x1 = cute.Cute.input.Double();
    double x2 = cute.Cute.input.Double();
    Optimization.rosenbrock(x1,x2);
  }
}
