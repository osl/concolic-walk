package gov.nasa.jpf.symbc;

public class ExSymExe8 {
	
  public static void main (String[] args) {
	  int x = 3;
	  int y = 5;
	  ExSymExe8 inst = new ExSymExe8();
	  inst.test(x,y);
  }

  /*
   * test ISUB & IFGE bytecodes (Note: javac compiles "<" to IFGE)
   */
  public void test (int x, int z) {
	  System.out.println("Testing ExSymExe8");
	  int y = 3;
	  int p = 2;
	  x = z - y ;
	  z = z - p;
	  if (z < 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (x < 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
}

