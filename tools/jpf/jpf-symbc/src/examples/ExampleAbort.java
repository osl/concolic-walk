



import gov.nasa.jpf.vm.Verify;

import java.util.EnumSet;

public class ExampleAbort {
	enum Failure { EARTH_SENSOR, LAS_CNTRL, CM_MASS, CM_RCS };
	EnumSet<Failure> failures = EnumSet.noneOf(Failure.class); 
	
	boolean nextStateSelected = false;  
	
	public static void main(String[] args) {
		int alt = 0; // this value is ignored if we want to execute the method symbolically
		boolean ctrlMotorField = Verify.randomBool(); 
		System.out.println("Invoke abort with ctrlMotorField="+ctrlMotorField);
		(new ExampleAbort()).abort(alt,ctrlMotorField);
	}

	// this is a simplified example showing method abort taken from CEV_15EOR_LOR
    public void abort (int altitude, boolean controlMotorFired) {
      if (!controlMotorFired) 
    	  failures.add(Failure.LAS_CNTRL);
      
      if (altitude <= 120000) {
        if (controlMotorFired) {
          setNextState("abortLowActiveLAS");
        } else {
          setNextState("abortPassiveLAS");
        }
      }
      
      if (altitude >= 120000) { 
    	  setNextState("abortHighActiveLAS");
      }
      
    }
    
    public void setNextState (String nextState) {
    	assert !nextStateSelected : "ambiguous transition";
    	nextStateSelected = true;
    	System.out.println("next state is:" + nextState);
    }
    
    
}
