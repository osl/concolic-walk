package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicLastIndexOf2Integer extends SymbolicInteger{
	StringExpression source, expression;
	IntegerExpression minIndex;
	
	public SymbolicLastIndexOf2Integer (String name, int l, int u, StringExpression source, StringExpression expression, IntegerExpression minIndex) {
		super (name, l, u);
		this.source = source;
		this.expression = expression;
		this.minIndex = minIndex;
	}
	
	public StringExpression getSource() {
		return source;
	}
	
	public StringExpression getExpression () {
		return expression;
	}
	
	public IntegerExpression getMinIndex () {
		return minIndex;
	}

}
