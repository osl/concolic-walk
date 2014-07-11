package gov.nasa.jpf.symbc;


public class ExSymExeLCMP {
	
  public static void main (String[] args) {
	  long x = 3;

	  ExSymExeLCMP inst = new ExSymExeLCMP();
	  inst.test(x,5);
  }

  public void test (long x, long y) {
	  
	  long res = x;
	  if (res +1 > res+2)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
		  
	  
  }
}

