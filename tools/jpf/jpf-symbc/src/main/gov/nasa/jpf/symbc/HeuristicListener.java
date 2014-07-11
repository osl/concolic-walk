//TODO: needs to be simplified;


//
//Copyright (C) 2007 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
package gov.nasa.jpf.symbc;


import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.PropertyListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.vm.ChoiceGenerator;

public class HeuristicListener extends PropertyListenerAdapter  {

	
	public HeuristicListener(Config conf, JPF jpf) {
	}


	public void stateAdvanced(Search search) {
		System.out.println("State advanced here.");
		ChoiceGenerator<?> cg = search.getVM().getSystemState().getChoiceGenerator();
		int n = cg.getTotalNumberOfChoices();
		if (cg instanceof PCChoiceGenerator) {
			System.out.println("got a PC choice generator "+ n + " "+
			((PCChoiceGenerator) cg).getNextChoice() + " "+((PCChoiceGenerator) cg).getCurrentPC());
		
		if(((PCChoiceGenerator) cg).getNextChoice()==0) { // replace this condition with yours
			System.out.println("backtrack");
			search.getVM().getSystemState().setIgnored(true);
		}
		
		}
	}
	
}
