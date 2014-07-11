package uberlazy;

public class dblNode extends Node {
	
	double otherElem;
	Node next;
	
	public dblNode() {
		
	}
	
	public void print() {
		System.out.println("I am a double Node");
	}
	
	public void testAll() {
		if(otherElem > 2) {
			System.out.println("the value is greater than 2");
		} else {
			System.out.println("the value is less than or equal to 2");
		}
	}
	
}