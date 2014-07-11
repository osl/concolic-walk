package gov.nasa.jpf.symbc;

public class ExLazyList {


	public static void main(String[] args) {
		Node n = new Node();
		//n.swapNode();
		test(n);
		
	}

	public static void test(Node node){
		if(node!=null)
			node.swapNode();
	}
	
	public static class Node {

		@Symbolic("true")
		public int elem;
		
		@Symbolic("true")
		public Node next;

		public Node swapNode() {
			//System.out.println(Debug.getSymbolicIntegerValue(elem));
			Node result=this;
			System.out.println("start");
			if (next != null) {
				if (elem > next.elem) {
					Node t = next;
					next = t.next;
					t.next = this;
					result = t;
				}
			}
			Debug.printSymbolicRef(result, "node = ");
			return result;
		}
	}
}
