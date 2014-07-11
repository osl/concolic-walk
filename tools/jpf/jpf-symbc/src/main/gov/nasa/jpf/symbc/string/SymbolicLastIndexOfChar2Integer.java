package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicLastIndexOfChar2Integer extends SymbolicInteger{
	StringExpression source; 
	IntegerExpression expression, minDist;
	
	public SymbolicLastIndexOfChar2Integer (String name, int l, int u, StringExpression source, IntegerExpression expression, IntegerExpression minDist) {
		super (name, l, u);
		this.source = source;
		this.expression = expression;
		this.minDist = minDist;
	}
	
	public StringExpression getSource() {
		return source;
	}
	
	public IntegerExpression getExpression () {
		return expression;
	}
	
	public IntegerExpression getMinDist() {
		return minDist;
	}

}
