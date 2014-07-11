import cute.Cute;

public class JCuteTargetPowTest {
  public static void main(String[] args){ 
    int x = cute.Cute.input.Integer();
    int y = cute.Cute.input.Integer();
    concolic.PowExample.test(x, y);
  }
}
