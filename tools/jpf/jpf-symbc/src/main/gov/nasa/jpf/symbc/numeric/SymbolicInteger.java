//
//Copyright (C) 2006 United States Government as represented by the
//Administrator of the National Aeronautics and Space Administration
//(NASA).  All Rights Reserved.
//
//This software is distributed under the NASA Open Source Agreement
//(NOSA), version 1.3.  The NOSA has been approved by the Open Source
//Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
//directory tree for the complete NOSA document.
//
//THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
//KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
//LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
//SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
//A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
//THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
//DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

package gov.nasa.jpf.symbc.numeric;

import gov.nasa.jpf.symbc.SymbolicInstructionFactory;

import java.util.Map;
import java.util.Random;

public class SymbolicInteger extends LinearIntegerExpression
{
	public static int UNDEFINED = Integer.MIN_VALUE;;
	public int _min = 0;
	public int _max = 0;
	public int solution = UNDEFINED; // C

	int unique_id;

	public static String SYM_INT_SUFFIX = "_SYMINT";
	private String name;

	public SymbolicInteger () {
		super();
		unique_id = MinMax.UniqueId++;
		PathCondition.flagSolved=false;
		name = "INT_" + hashCode();
		_min = MinMax.getVarMinInt(name);
		_max = MinMax.getVarMaxInt(name);
	}

	public SymbolicInteger (String s) {
		super();
		unique_id = MinMax.UniqueId++;
		//PathCondition.flagSolved=false;
		name = s;
		_min = MinMax.getVarMinInt(name);
		_max = MinMax.getVarMaxInt(name);
		//trackedSymVars.add(fixName(name));

	}

	public SymbolicInteger (int l, int u) {
		super();
		unique_id = MinMax.UniqueId++;
		_min = l;
		_max = u;
		//PathCondition.flagSolved=false;
    name = "INT_" + hashCode();
	}

	public SymbolicInteger (String s, int l, int u) {
		super();
		unique_id = MinMax.UniqueId++;
		_min = l;
		_max = u;
		name = s;
		//PathCondition.flagSolved=false;
		//trackedSymVars.add(fixName(name));

	}

	public String getName() {
		return (name != null) ? name : "INT_" + hashCode();
	}

	public String stringPC () {
		return (name != null) ? name : "INT_" + hashCode();
	}

	public String toString () {
		if (!PathCondition.flagSolved) {
			return (name != null) ? name : "INT_" + hashCode();

		} else {
			return (name != null) ? name + "[" + solution + "]" :
				"INT_" + hashCode() + "[" + solution + "]";
		}
	}

	public int solution() {
		if (PathCondition.flagSolved) {
			if (solution == UNDEFINED && SymbolicInstructionFactory.concolicMode) {
				solution = (new Random().nextInt(_max-_min))+_min;
			}
			return solution;
		}
		else
			throw new RuntimeException("## Error: PC not solved!");
	}

    public void getVarsVals(Map<String,Object> varsVals) {
    	varsVals.put(fixName(name), solution);
    }

    private String fixName(String name) {
    	if (name.endsWith(SYM_INT_SUFFIX)) {
    		name = name.substring(0, name.lastIndexOf(SYM_INT_SUFFIX));
    	}
    	return name;
    }

    public boolean equals (Object o) {
        return (o instanceof SymbolicInteger) &&
               (this.equals((SymbolicInteger) o));
    }

    private boolean equals (SymbolicInteger s) {
//        if (name != null)
//            return (this.name.equals(s.name)) &&
//                   (this._max == s._max) &&
//                   (this._min == s._min);
//        else
//            return (this._max == s._max) &&
//                   (this._min == s._min);
    	return this.unique_id == s.unique_id;
    }

    public int hashCode() {
        //return Integer.toHexString(_min ^ _max).hashCode();
    	return unique_id;
    }

    protected void finalize() throws Throwable {
    	//System.out.println("Finalized " + this);
    }
    
    @Override
	public void accept(ConstraintExpressionVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

	@Override
	public int compareTo(Expression expr) {
		if (expr instanceof SymbolicInteger) {
			SymbolicInteger e = (SymbolicInteger) expr;
			int a = unique_id;
			int b = e.unique_id;
			return (a < b) ? -1 : (a > b) ? 1 : 0;
		} else {
			return getClass().getCanonicalName().compareTo(expr.getClass().getCanonicalName());
		}
	}
}
