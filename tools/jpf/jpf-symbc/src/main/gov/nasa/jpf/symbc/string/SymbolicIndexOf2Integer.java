package gov.nasa.jpf.symbc.string;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicIndexOf2Integer extends SymbolicInteger{
	StringExpression source, expression;
	IntegerExpression minIndex;
	
	public SymbolicIndexOf2Integer (String name, int l, int u, StringExpression source, StringExpression expression, IntegerExpression minIndex) {
		super (name, l, u);
		this.source = source;
		if (expression == null) {
			throw new RuntimeException("Inst error");
		}
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
