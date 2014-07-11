package uberlazy;

public class colorIntNode extends intNode {
	
	String color;
	
	public colorIntNode (String color) {
		super();
		this.color = color;
	}
	
	public void print() {
		System.out.println("I am a color Node");
	}
	
}