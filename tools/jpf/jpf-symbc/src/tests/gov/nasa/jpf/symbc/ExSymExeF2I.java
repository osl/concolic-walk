package gov.nasa.jpf.symbc;


public class ExSymExeF2I {
	
  public static void main (String[] args) {
	  float x = 3;

	  ExSymExeF2I inst = new ExSymExeF2I();
	  inst.test(x);
  }

  public void test (float x) {
	  
	  int res = (int) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
		  
	  
  }
}

