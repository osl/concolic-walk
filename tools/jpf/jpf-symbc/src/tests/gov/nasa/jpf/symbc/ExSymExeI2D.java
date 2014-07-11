package gov.nasa.jpf.symbc;


public class ExSymExeI2D {
	
  public static void main (String[] args) {
	  int x = 3;

	  ExSymExeI2D inst = new ExSymExeI2D();
	  inst.test(x);
  }

  public void test (int x) {
	  double res = (double) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
  }
}

