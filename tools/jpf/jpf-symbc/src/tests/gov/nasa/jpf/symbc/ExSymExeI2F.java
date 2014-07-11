package gov.nasa.jpf.symbc;


public class ExSymExeI2F {
	
  public static void main (String[] args) {
	  int x = 3;

	  ExSymExeI2F inst = new ExSymExeI2F();
	  inst.test(x);
  }

  public void test (int x) {
	  float res = (float) ++ x;
	  if (res > 0)
		  System.out.println("x >0");
	  else
		  System.out.println("x <=0");
  }
}

