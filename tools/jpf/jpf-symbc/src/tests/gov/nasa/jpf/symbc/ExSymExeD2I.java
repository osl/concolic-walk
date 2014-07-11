package gov.nasa.jpf.symbc;


public class ExSymExeD2I {
	
  public static void main (String[] args) {
	  double x = 3.0;

	  ExSymExeD2I inst = new ExSymExeD2I();
	  inst.test(x);
  }

  public void test (double x) {
	  
	  int res = (int) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
		  
	  
  }
}

