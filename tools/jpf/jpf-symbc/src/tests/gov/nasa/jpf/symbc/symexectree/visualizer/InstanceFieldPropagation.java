/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.X;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
class InstanceFieldPropagation extends Thread {
	X myX; // initially not set

	public void run() {
		//myX = (X) Debug.makeSymbolicRef("SYMB", myX);
		if(myX != null){
			//System.out.println("T: accessed global myX");
			if (!myX.pass){  // (2) won't fail unless main is between (0) and (1)
				//throw new AssertionError("gotcha");
				System.out.println("Gotcha!");
			}
		}
		Debug.printSymbolicRef(myX, "MyX");
	}    
}
