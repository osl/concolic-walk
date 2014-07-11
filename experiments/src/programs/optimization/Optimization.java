/**
 * Functions used to test optimization software. See J. J. More, B. S. Garbow,
 * and K. E. Hillstrom. Testing unconstrained optimization software. ACM Trans.
 * Math. Software, 7(1):17--41, 1981.
 * 
 * Ported from the C# implementation given in K. Lakhotia, N. Tillmann, M.
 * Harmann, and J. de Halleaux. FloPSy - Search-Based Floating Point Constraint
 * Solving for Symbolic Execution" (Table 2).
 */
public class Optimization {

  public final static void beale(double x1, double x2) {
    if ((1.5 - x1 * (1.0 - x2)) == 0.0) {
      System.out.println("Solved Beale constraint");
    }
  }

  public final static void freudensteinRoth(double x1, double x2) {
    if ((-13.0 + x1 + ((5.0 - x2) * x2 - 2.0) * x2) +
        (-29.0 + x1 + ((x2 + 1.0) * x2 - 14.0) * x2) == 0.0) {
      System.out.println("Solved Freudenstein and Roth constraint");
    }
  }

  // This is public only because JPF keeps generating test cases for it
  // and it is highly annoying to remove them every time we regenerate them.
  public final static double theta(double x1, double x2) {
    if(x1 > 0.0) {
      return Math.atan(x2 / x1) / (2 * Math.PI);
    } else if (x1 < 0.0) {
      return (Math.atan(x2 / x1) / (2 * Math.PI) + 0.5);
    }
    return 0.0;
  }

  public final static void helicalValley(double x1, double x2, double x3) {
    if (10.0 * (x3 - 10.0 * theta(x1, x2)) == 0
        && (10.0 * (Math.sqrt(x1 * x1 + x2 * x2) - 1)) == 0.0
        && x3 == 0.0) {
      System.out.println("Solved Helical Valley constraint");
    }
  }

  public final static void powell(double x1, double x2) {
    if ((Math.pow(10, 4) * x1 * x2 - 1.0) == 0.0
        && (Math.pow(Math.E, -x1) + Math.pow(Math.E, -x2) - 1.0001) == 0.0) {
      System.out.println("Solved Powell constraint");
    }
  }

  public final static void rosenbrock(double x1, double x2) {
    if (Math.pow((1.0 - x1), 2) + 100.0 * (Math.pow((x2 - x1 * x1), 2)) == 0.0) {
      System.out.println("Solved Rosenbrock consraint");
    }
  }

  public final static void wood(double x1, double x2, double x3, double x4) {
    if ((10.0 * (x2 - x1 * x1)) == 0.0 && (1.0 - x1) == 0.0
        && (Math.sqrt(90) * (x4 - x3 * x3)) == 0.0
        && (1.0 - x3) == 0.0
        && (Math.sqrt(10) * (x2 + x4 - 2.0)) == 0.0
        && (Math.pow(10, -0.5) * (x2 - x4)) == 0.0) {
      System.out.println("Solved Wood constraint");
    }
  }
}
