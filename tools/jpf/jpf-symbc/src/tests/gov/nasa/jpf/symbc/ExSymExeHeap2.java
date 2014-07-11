package gov.nasa.jpf.symbc;


public class ExSymExeHeap2 {
	static class PrimitiveNode {
		Node next;
	}
	static class Node extends PrimitiveNode {
		int elem;
		//Node next;
		//static int test1;
		//static PrimitiveNode sn;


		  public void test () {
			  if (next == null)
				  System.out.println ("next null");
			  else
				  System.out.println ("next not null");

		  }

		  /* we want to let the user specify that this method should be symbolic */
		  Node swapNode() {
		  //if (Node.sn == null) {
		  //if (Node.test1 >= 0) {
		  if(next!=null)
			  if(elem > next.elem) {
				  Node t = next;
				  next = t.next;
				  t.next = this;
			//	  if (Node.test1 >= 1)
				//	  System.out.println("!!!! 1 !!!!");
				  //else
					//  System.out.println("!!!! not 1 !!!!");
				  return t;
			  }
		  //}
		  return this;
		  }
	}

	public static void test2 () {
		Node n=null;
		PrimitiveNode pn=null;
		n = (Node) pn;
		pn = n;


	}
  public static void main (String[] args) {
	  //test2();
	  ExSymExeHeap inst = new ExSymExeHeap();
	  Node n = new Node();
	  Debug.makeFieldsSymbolic("input_n", n);

	  //n.test();
	  Node m = n.swapNode();
	  Debug.printHeapPC("HeapPC: ");
	  Debug.printPC("PC: ");
	  Debug.printSymbolicRef(m,"return:");
	  System.out.println("*****************");
  }

}

