package uberlazy;

import gov.nasa.jpf.symbc.Symbolic;

public class TestGeneral{
	
	@Symbolic("true")
	Node n;
	
	public void test(int val, int concVal) {
		if(n != null && n instanceof intNode) {
			intNode in = (intNode) n;
			if(in.elem == val) {
				System.out.println("Reached target");
			}
		}
	}
	
	public static void main(String[] args) {
		TestGeneral tg = new TestGeneral();
		int val = 0; // input
		int otherVal = 1;
		tg.test(val, otherVal);
	}
}