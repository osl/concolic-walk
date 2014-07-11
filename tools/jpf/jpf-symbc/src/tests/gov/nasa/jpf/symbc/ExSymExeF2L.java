package gov.nasa.jpf.symbc;


public class ExSymExeF2L {
	
  public static void main (String[] args) {
	  float x = 3;

	  ExSymExeF2L inst = new ExSymExeF2L();
	  inst.test(x);
  }

  public void test (float x) {
	  
	  long res = (long) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
		  
	  
  }
}

