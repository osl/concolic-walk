package gov.nasa.jpf.symbc.numeric.visitors;

import java.util.HashSet;
import java.util.Set;

import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

public class CollectVariableVisitor extends ConstraintExpressionVisitor {

	private Set<Object> variables = new HashSet<Object>();
	
	@Override
	public void postVisit(SymbolicReal realVariable) {
		variables.add(realVariable);
	}

	@Override
	public void postVisit(SymbolicInteger integerVariable) {
		variables.add(integerVariable);
	}

	public Set<Object> getVariables() {
		return variables;
	}

}
