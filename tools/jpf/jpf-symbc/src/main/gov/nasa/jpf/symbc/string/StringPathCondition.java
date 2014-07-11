/*  Copyright (C) 2005 United States Government as represented by the
Administrator of the National Aeronautics and Space Administration
(NASA).  All Rights Reserved.

Copyright (C) 2009 Fujitsu Laboratories of America, Inc.

DISCLAIMER OF WARRANTIES AND LIABILITIES; WAIVER AND INDEMNIFICATION

A. No Warranty: THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY
WARRANTY OF ANY KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY,
INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE
WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR FREEDOM FROM
INFRINGEMENT, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL BE ERROR
FREE, OR ANY WARRANTY THAT DOCUMENTATION, IF PROVIDED, WILL CONFORM TO
THE SUBJECT SOFTWARE. NO SUPPORT IS WARRANTED TO BE PROVIDED AS IT IS PROVIDED "AS-IS".

B. Waiver and Indemnity: RECIPIENT AGREES TO WAIVE ANY AND ALL CLAIMS
AGAINST FUJITSU LABORATORIES OF AMERICA AND ANY OF ITS AFFILIATES, THE
UNITED STATES GOVERNMENT, ITS CONTRACTORS AND SUBCONTRACTORS, AS WELL
AS ANY PRIOR RECIPIENT.  IF RECIPIENT'S USE OF THE SUBJECT SOFTWARE
RESULTS IN ANY LIABILITIES, DEMANDS, DAMAGES, EXPENSES OR LOSSES ARISING
FROM SUCH USE, INCLUDING ANY DAMAGES FROM PRODUCTS BASED ON, OR RESULTING
FROM, RECIPIENT'S USE OF THE SUBJECT SOFTWARE, RECIPIENT SHALL INDEMNIFY
AND HOLD HARMLESS FUJITSU LABORATORTIES OF AMERICA AND ANY OF ITS AFFILIATES,
THE UNITED STATES GOVERNMENT, ITS CONTRACTORS AND SUBCONTRACTORS, AS WELL
AS ANY PRIOR RECIPIENT, TO THE EXTENT PERMITTED BY LAW.  RECIPIENT'S SOLE
REMEDY FOR ANY SUCH MATTER SHALL BE THE IMMEDIATE, UNILATERAL
TERMINATION OF THIS AGREEMENT.

*/
package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.PathCondition;

public class StringPathCondition {
	  static boolean flagSolved = false;

	  public StringConstraint header;
	  int count = 0;

	  PathCondition npc = null;

	  public StringPathCondition(PathCondition npc) {
	    this.npc = npc;
	    header = null;
	  }

	  public StringPathCondition make_copy(PathCondition npc) {
	    StringPathCondition pc_new = new StringPathCondition(npc);
	    pc_new.header = this.header;
	    pc_new.count = this.count;
	    return pc_new;
	  }

	  // constraints on strings
	  public void _addDet(StringComparator c, StringExpression l, String r) {
	    flagSolved = false; // C
	    _addDet(c, l, new StringConstant(r));
	  }

	  public void _addDet(StringComparator c, String l, StringExpression r) {
	    flagSolved = false; // C
	    _addDet(c, new StringConstant(l), r);
	  }

	  public void _addDet(StringComparator c,  StringExpression r) {
		    StringConstraint t;

		    flagSolved = false; // C

		    t = new StringConstraint(c, r);

		    if (!hasConstraint(t)) {
		      t.and = header;
		      header = t;
		      count++;
		    }
		  }

	  public void _addDet(StringComparator c, StringExpression l, StringExpression r) {
	    StringConstraint t;

	    flagSolved = false; // C

	    t = new StringConstraint(r, c, l);

	    if (!hasConstraint(t)) {
	      t.and = header;
	      header = t;
	      count++;
	    }
	  }

	  public int count() {
	    return count;
	  }

	  public boolean hasConstraint(StringConstraint c) {
	    StringConstraint t = header;

	    while (t != null) {
	      if (c.equals(t)) {
	        return true;
	      }

	      t = t.and;
	    }
	    return false;
	  }

	  public boolean solve() {// warning: solve calls simplify
	  // SymbolicStringConstraintsGeneral solver = new SymbolicStringConstraintsGeneral();
	  // solver.solve(this);
	      StringPathCondition.flagSolved = true;
	      return true;
	  }

	  public boolean simplify() {
	    SymbolicStringConstraintsGeneral solver = new SymbolicStringConstraintsGeneral();
	    boolean result = solver.isSatisfiable(this);
	    return result;

	  }

	  public String stringPC() {
	    return "SPC # = " + count + ((header == null) ? "" : "\n" + header.stringPC());
	  }

	  public String toString() {
	    return "SPC # = " + count + ((header == null) ? "" : "\n" + header.toString());
	  }

	}
