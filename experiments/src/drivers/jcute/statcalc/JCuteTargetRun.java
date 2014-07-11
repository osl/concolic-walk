import cute.Cute;

public class JCuteTargetRun {
  public static void main(String[] args){ 
    int x = cute.Cute.input.Integer();
    concolic.TestStatCalc.run(x);
  }
}
