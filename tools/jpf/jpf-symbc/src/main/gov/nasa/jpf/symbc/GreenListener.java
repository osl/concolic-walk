package gov.nasa.jpf.symbc;

import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import za.ac.sun.cs.green.util.Reporter;

public class GreenListener extends ListenerAdapter {

	public GreenListener() { }

	@Override
	public void searchFinished(Search s) {
		SymbolicInstructionFactory.greenSolver.shutdown();
		SymbolicInstructionFactory.greenSolver.report(new Reporter() {
			@Override
			public void report(String context, String message) {
				System.out.println(context + ":: " + message);
			}
		});

	}

}