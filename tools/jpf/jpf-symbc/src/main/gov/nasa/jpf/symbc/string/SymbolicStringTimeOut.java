package gov.nasa.jpf.symbc.string;

import java.util.TimerTask;

public class SymbolicStringTimeOut extends TimerTask{

	@Override
	public void run() {
		synchronized (SymbolicStringConstraintsGeneral.mutexTimedOut) {
			SymbolicStringConstraintsGeneral.timedOut = true;
		}
		
	}

}
