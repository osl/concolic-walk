package summerschool;

import gov.nasa.jpf.symbc.Debug;

public class Node {
	int elem;
	Node next;

	Node swapNode() {
		if(next!=null) 
			if(elem>next.elem) {
				Node t = next;
				next = t.next;
				t.next=this;
				return t;
			}
		return this;
	}
	
	public static void main(String[] args) {	
		Node n = new Node();
		if (n!=null) {
			Node result =n.swapNode();
			Debug.printPC("PC");
			Debug.printHeapPC("heap PC");
			Debug.printSymbolicRef(result, "result list");	
		}
	}
}
