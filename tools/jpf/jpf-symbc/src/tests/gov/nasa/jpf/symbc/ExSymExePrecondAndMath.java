package gov.nasa.jpf.symbc;

public class ExSymExePrecondAndMath {

  public static void main (String[] args) {
	  double x = 5.0;
	  (new ExSymExePrecondAndMath()).test(0,x);
  }

  @Preconditions("y>5")
  public  int test (int y, double x) {

	 if (x+y > 0) {
		 System.out.println("ret = "+0);
		 return 0;
	 }
	 else {
		 System.out.println("ret = "+1);
		 return 1;
	 }
  }


}

