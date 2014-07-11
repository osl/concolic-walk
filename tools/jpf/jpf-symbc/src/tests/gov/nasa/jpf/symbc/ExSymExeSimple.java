package gov.nasa.jpf.symbc;


public class ExSymExeSimple {
	static class Node {

		  public void test (int a, int b) {

			  if(a > b)
				  System.out.println(">");
			  else if (a == b)
				  System.out.println("eq");
			  else
				  System.out.println("<");
		  }
	}
  public static void main (String[] args) {

	  ExSymExeSimple inst = new ExSymExeSimple();
	  Node n = new Node();
	  n.test(0,0);
	  //Debug.printPC("PC: ");
	  System.out.println("*****************");
  }

}

