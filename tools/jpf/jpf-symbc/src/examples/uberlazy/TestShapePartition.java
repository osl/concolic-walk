package uberlazy;

import gov.nasa.jpf.symbc.Symbolic;

/**
 * @author Neha Rungta
 * This class tests building equivalence classes and the partition
 * function for differing shapes when a store operation occurs on
 * a data-structure of a non-primitive type. 
 **/


public class TestShapePartition {
	@Symbolic("true")
	Node m;
	@Symbolic("true")
	Node n;
	
	public void run() {
		if(m != null) {
			System.out.println("m is not null");
			@SuppressWarnings("unused")
			Node tmp = m.next;
			tmp = swapNode();
		}
	}
	
	private Node swapNode() {
		System.out.println("coming in swapNode");
		if(n != null) {
			System.out.println("\t n is not null");
			if(n.next !=null) {	
				System.out.println("\t \t n.next is not null");
				Node t = n.next;
				n.next = t.next;
				t.next = n;
				return t;
			}
		}
		return n;
	}
	
	public static void main(String[] args) {
		TestShapePartition tt = new TestShapePartition();
		tt.run();
	}
	
}