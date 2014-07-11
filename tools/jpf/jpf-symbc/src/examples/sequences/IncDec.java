package sequences;

import gov.nasa.jpf.symbc.Debug;



/**
 *
 * @author Mithun Acharya
 *
 */
public class IncDec {

	private int global;

	public IncDec(){
		global = 0;
	}

	public void inc(int input){
		if (input == 0)
			;
		else
			;
		this.global++;
		//System.out.println("global=" + global);
	}
	public void dec(int input){
		if (input == 0)
			;
		else
			;
		this.global--;
		//System.out.println("global=" + global);
		//Debug.recordMethodTransition(this);
	}

	/**
	 *
	 * Observer method.
	 */
	public boolean isZero(){
		return global == 0;
	}

}
