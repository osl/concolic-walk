package gov.nasa.jpf.symbc;


public class ExSymExeArrays {
	static int[] a; 	
  public static void main (String[] args) {
	  a=new int[1];
	  int x = -3; 

	  ExSymExeArrays inst = new ExSymExeArrays();
	
	  inst.test(x);
  }

  public void test (int x) {
	  a[0]=x;
	  if(a[0]>=0)
		  System.out.println("branch1 >=0");
	  else
		  System.out.println("branch2 <0");
//	  if(x>=0)
//		  System.out.println("branch3 >=0");
//	  else
//		  System.out.println("branch4 <0");
	  
	  
  }
}

