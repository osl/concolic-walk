package gov.nasa.jpf.symbc.string.graph;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;

public interface EdgeChar extends Edge {
	public IntegerExpression getIndex();

	public IntegerExpression getValue();
}
