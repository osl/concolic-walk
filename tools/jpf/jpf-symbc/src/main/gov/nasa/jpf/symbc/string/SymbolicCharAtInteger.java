package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicCharAtInteger extends SymbolicInteger{
	StringExpression se;
	IntegerExpression index;
	boolean constant;
	public SymbolicCharAtInteger (String name, int l, int u, StringExpression se, IntegerExpression index) {
		super (name, l, u);
		this.se = se;
		this.index = index;
		constant = false;
	}
	
	public SymbolicCharAtInteger (String name, int l, int u, StringExpression se, IntegerExpression index, boolean constant) {
		this (name, l, u, se, index);
		this.constant = constant;
	}
	
	public boolean isConstant () {
		return constant;
	}

}
