public class JpfTargetMysin {
  public static void static_mysin(double arg) {
    concolic.MathSin sin = new concolic.MathSin();
    sin.mysin(arg);
  }

  public static void main(String[] args){ 
    static_mysin(0);
  }
}
