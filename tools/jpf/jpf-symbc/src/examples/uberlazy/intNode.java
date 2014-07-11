package uberlazy;

public class intNode extends Node {
	int elem;
	Node next;
	
	public intNode() {
		
	}
	
	public void print() {
		System.out.println("I am an intNode");
	}
	
	public void testAll() {
		if(elem > 1) {
			System.out.println("the value is greater than 1");
		} else {
			System.out.println("the value is less than or equal to 1");
		}
	}
}