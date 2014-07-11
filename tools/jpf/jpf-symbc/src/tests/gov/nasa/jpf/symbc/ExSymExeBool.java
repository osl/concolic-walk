package gov.nasa.jpf.symbc;

public class ExSymExeBool {
	
  public static void main (String[] args) {
	  int x = 3;
	  boolean y = true;
	  ExSymExeBool inst = new ExSymExeBool();
	  inst.test(y,x);
  }

  /*
   * test IINC & IFLE bytecodes (Note: javac compiles ">" to IFLE)
   */
  public void test (boolean x, int z) {
	  System.out.println("Testing ExSymExeBool");
	  z++ ;
	  if (x)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
  }
}

