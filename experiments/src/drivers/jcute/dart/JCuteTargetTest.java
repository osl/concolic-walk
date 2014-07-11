
import cute.Cute;

public class JCuteTargetTest {
  public static void main(String[] args) {
    int a0 = cute.Cute.input.Integer();
    int a1 = cute.Cute.input.Integer();
    concolic.DART.test(a0, a1);
  }
}

