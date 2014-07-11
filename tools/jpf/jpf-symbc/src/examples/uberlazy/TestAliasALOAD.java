package uberlazy;

import gov.nasa.jpf.symbc.Symbolic;

public class TestAliasALOAD {
	
	@Symbolic("true")
	Node m;
	
	public void run (Node n) {
		if(m != null) {
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
	}
	
	
	public static void main (String[] args) {
		TestAliasALOAD aload = new TestAliasALOAD();
		Node n = null;
		aload.run(n);
	}
	
}