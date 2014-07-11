
import cute.Cute;

public class JCuteTargetBenchmark01 {
  public static void main(String[] args) {
    double a0 = cute.Cute.input.Double();
    double a1 = cute.Cute.input.Double();
    coral.tests.JPFBenchmark.benchmark01(a0, a1);
  }
}

