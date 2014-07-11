package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicIndexOfCharInteger extends SymbolicInteger{
	StringExpression source; 
	IntegerExpression expression;
	
	public SymbolicIndexOfCharInteger (String name, int l, int u, StringExpression source, IntegerExpression expression) {
		super (name, l, u);
		this.source = source;
		this.expression = expression;
	}
	
	public StringExpression getSource() {
		return source;
	}
	
	public IntegerExpression getExpression () {
		return expression;
	}

}
