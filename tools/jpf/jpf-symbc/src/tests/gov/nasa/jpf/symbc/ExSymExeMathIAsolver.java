package gov.nasa.jpf.symbc;

public class ExSymExeMathIAsolver {

  public static void main (String[] args) {
	 //ExSymExeMathIAsolver inst = new ExSymExeMathIAsolver();
	 test(0,0);
	 test1(0,0);
  }

  //@Preconditions("x>0.0&&y>0.0")
  public static void test (double x, double y) {
	  if(x == 0.0 && Math.floor(y) == 0.0 )//pow(x, 2.0)+ Math.pow(y,2.0)== 25 && Math.acos(y/3)==Math.pow(x-4,2))
			System.out.println("here ...solvable 1");
	  else
		  System.out.println("solvable 2");
  }

  @Preconditions("x>0.0&&y>0.0")
  public static void test1 (double x, double y) {
	  //if(x>y)
	  if(Math.pow(x, 2.0)+ Math.pow(y,2.0)== 25)
		  System.out.println("solvable 1");
	  else
		  System.out.println("solvable 2");
  }
}

