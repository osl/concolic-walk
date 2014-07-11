/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;


/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class SimpleSys {
	
	public static void main(String[] args) {
		SimpleSys s = new SimpleSys();
		s.compAB(2, 2);
	}

	public int compAB(int a, int b) {
		if(a > b) {
			if(a == b) {
				return number() + 42;
			} else
				return 42;
		} else
			return number();
		
		/*int d = 0;
		for(int c = 0; c < a; c++) {
			d += 10;
		}
		if(b > 3) {
			int g = 10;
			if(callee(g) > 2) {
				g++;
			}
		} else {
			d -= 10;
		}*/
	}
	
	public int number() {
		return 24;
	}
}
