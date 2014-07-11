package uberlazy;


public class TestALOAD {
	
	public void run(Node n) { 
		if(n != null) {
			System.out.println("the object n is not null");
			if(n.elem > 10) {
				System.out.println("the value of the elem is greater than 10");
			} else {
				System.out.println("the value of elem is less than 10");
			}
		} else {
			System.out.println("n is null");
		}
	}
	
	
	public static void main(String[] args) {
		TestALOAD taload = new TestALOAD();
		Node n = null; 
		taload.run(n);
	}
}