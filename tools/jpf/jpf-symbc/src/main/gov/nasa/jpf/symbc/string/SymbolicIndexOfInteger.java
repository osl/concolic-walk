package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicIndexOfInteger extends SymbolicInteger{
	StringExpression source, expression;
	
	public SymbolicIndexOfInteger (String name, int l, int u, StringExpression source, StringExpression expression) {
		super (name, l, u);
		this.source = source;
		if (expression == null) {
			throw new RuntimeException("Init error");
		}
		this.expression = expression;
	}
	
	public StringExpression getSource() {
		return source;
	}
	
	public StringExpression getExpression () {
		return expression;
	}

}
