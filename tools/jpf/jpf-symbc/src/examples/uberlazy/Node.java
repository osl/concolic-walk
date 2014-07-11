package uberlazy;

public class Node {
	
	short elem;
	Node next;
	static int testElem;
	
	public Node () {
		
	}
	public boolean isNextObject (Object node) {
		return this.next == node;
	}
	
	public void print() { 
		System.out.println("I am a just a Node");
	}
	
	public void onePrint() {
		System.out.println("all types of nodes use this onePrint method");
	}
	
	public void testVal() {
		if(elem > 9) {
			System.out.println("the value is greater than 9");
		} else {
			System.out.println("the value is less than or equal to 9");
		}
	}
	
	public void testAll() {
		if(this.elem > 0) {
			System.out.println("the value is greater than 0");
		} else {
			System.out.println("the value is less than or equal to 0");
		}
	}
}