package gov.nasa.jpf.symbc;


public class ExGenSymExe {

	public static void main (String[] args) {
		Node n = new Node();
		Node m = new Node();
		n.next = m;
		n.next.next=n;
		Node na = n.swapNode();
		//Debug.printSymbolicRef(n);
		//Debug.printPC("\nPC");
		//Debug.printHeapPC("Heap PC");
	}

	static class Node {
		int elem;
		Node next;
        static Node static_next;
		Node swapNode() {
			static_next = new Node();
			static_next.next = new Node();
			static_next.next.next = new Node();
			System.out.println("static_next "+static_next);
			System.out.println("static_next.next "+static_next.next);
			System.out.println("static_next.next.next "+static_next.next.next);
			if (next!=null)
				if(elem > next.elem) {
					Node t = next;
					next = t.next;
					t.next = this;
					return t;
				}
			return this;
		}
	}
}