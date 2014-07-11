package gov.nasa.jpf.symbc;

public class ExSymExeLNEG {
	
  public static void main (String[] args) {
	  long x = 3876543455L;
	  ExSymExeLNEG inst = new ExSymExeLNEG();
	  inst.test(x);
  }

  
  public void test (long x) {
	  System.out.println("Testing LNEG");
	  long y = - x;
	  if (y > 0)
		  System.out.println("branch -x > 0");
	  else
		  System.out.println("branch -x <= 0");
  }
}

