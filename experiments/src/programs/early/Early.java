public class Early {

  public static void commitEarly(int a, int b) {
    int c = a * b;
    if (b > 2 && b == c) {
      System.out.println("Solved early commitment");
    }
  }

  public static void main(String[] args) {
    int a = 1, b = 3;
    commitEarly(a, b);
  }
}
