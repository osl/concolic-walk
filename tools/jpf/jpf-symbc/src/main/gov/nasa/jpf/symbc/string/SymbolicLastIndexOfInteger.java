package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicLastIndexOfInteger extends SymbolicInteger{
	StringExpression source, expression;
	
	public SymbolicLastIndexOfInteger (String name, int l, int u, StringExpression source, StringExpression expression) {
		super (name, l, u);
		this.source = source;
		this.expression = expression;
	}
	
	public StringExpression getSource() {
		return source;
	}
	
	public StringExpression getExpression () {
		return expression;
	}

}
