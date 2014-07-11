package gov.nasa.jpf.symbc;


public class ExSymExeHeap3 {

	static class Node  {
		int elem;
		Node next;
		static int static_field1;
		static int static_field2;


		  /* we want to let the user specify that this method should be symbolic */
		  Node swapNode() {

		 if (static_field1 > static_field2)
			  static_field1 = 0;
		 else
			  static_field2 = 1;


		  if(next!=null)
			  if(elem > next.elem) {
				  Node t = next;
				  next = t.next;
				  t.next = this;
				  return t;
			  }
		  return this;
		  }
	}


  public static void main (String[] args) {

	  Node n = new Node();
	  Debug.makeFieldsSymbolic("input_n", n);
	  Node m = n.swapNode();
	  Debug.printHeapPC("HeapPC: ");
	  Debug.printPC("PC: ");
	  Debug.printSymbolicRef(m,"return:");
	  System.out.println("*****************");
  }

}

