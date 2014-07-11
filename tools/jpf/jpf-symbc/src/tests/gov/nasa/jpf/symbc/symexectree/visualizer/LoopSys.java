/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class LoopSys {
	
	public static void main(String[] args) {
		LoopSys s = new LoopSys();
		s.loopCompAB(2, 2);
	}

	public int loopCompAB(int a, int b) {
		int d = 0;
		for(int i = 0; i < 3; i++) {
			if(b > 10) {
				d++;
			}
		}
		return d;
	}
	
	public int number() {
		return 24;
	}
}
