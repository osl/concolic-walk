package gov.nasa.jpf.symbc;

public class ExSymExeFNEG {
	
  public static void main (String[] args) {
	  float x = 3;
	  ExSymExeFNEG inst = new ExSymExeFNEG();
	  inst.test(x);
  }

  
  public void test (float x) {
	  System.out.println("Testing FNEG");
	  float y = - x;
	  if (y > 0)
		  System.out.println("branch -x > 0");
	  else
		  System.out.println("branch -x <= 0");
  }
}

