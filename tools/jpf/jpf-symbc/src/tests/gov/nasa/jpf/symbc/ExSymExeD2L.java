package gov.nasa.jpf.symbc;


public class ExSymExeD2L {
	
  public static void main (String[] args) {
	  double x = 3.0;

	  ExSymExeD2L inst = new ExSymExeD2L();
	  inst.test(x);
  }

  public void test (double x) {
	  
	  long res = (long) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
		  
	  
  }
}

