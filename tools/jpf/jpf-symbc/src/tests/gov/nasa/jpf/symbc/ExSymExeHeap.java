package gov.nasa.jpf.symbc;


public class ExSymExeHeap {

	static class Node  {
		//@Symbolic("true")
		int elem;

		//Symbolic("true")
		Node next;


		  /* we want to let the user specify that this method should be symbolic */
		  Node swapNode() {

		  if(next!=null)
			  if(elem > next.elem) {
				  elem = 0;
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
	  Node m = new Node();
	  //Debug.makeFieldsSymbolic("input_n", n);
	  n =  (Node) Debug.makeSymbolicRef("input_n", n);
	  //Debug.makeFieldsSymbolic("input_m", m);
	  if (n !=null)
		  m = n.swapNode();
	  Debug.printHeapPC("HeapPC: ");
	  Debug.printPC("PC: ");
	  Debug.printSymbolicRef(m,"return:");
	  System.out.println("*****************");
  }

}

