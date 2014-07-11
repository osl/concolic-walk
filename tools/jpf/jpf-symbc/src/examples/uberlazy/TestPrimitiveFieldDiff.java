package uberlazy;

/**
 * @author Neha Rungta
 *  A test driver for checking the values in the equivalence
 *  classes when variables of primitive type differ in their
 *  values. 
 *  **/


import gov.nasa.jpf.symbc.Symbolic;


public class TestPrimitiveFieldDiff {
	
	@Symbolic("true")
	Node n;
	@Symbolic("true")
	Node m;
	
	public void run() {
		if(m != null) {
			// when a primitive field reference is "used"	
			// and it differs in the value/type then the partition
			// function separates the ones that are different
			if(m.elem > 10) {
				if(n != null) {
					if(n.elem < 10) {
						System.out.println("m.elem is > 10 and n.elem < 10");
					}
				}
			} else {
				System.out.println("m.elem <= 10");
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		TestPrimitiveFieldDiff tt = new TestPrimitiveFieldDiff();
		tt.run();
	}
	
}