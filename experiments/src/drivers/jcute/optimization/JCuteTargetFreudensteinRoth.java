import cute.Cute;

public class JCuteTargetFreudensteinRoth {
  public static void main(String[] args){ 
    double x1 = cute.Cute.input.Double();
    double x2 = cute.Cute.input.Double();
    Optimization.freudensteinRoth(x1,x2);
  }
}
