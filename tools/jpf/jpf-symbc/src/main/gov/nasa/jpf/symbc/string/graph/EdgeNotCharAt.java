package gov.nasa.jpf.symbc.string.graph;

import gov.nasa.jpf.symbc.numeric.IntegerExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdgeNotCharAt implements EdgeChar {

	Vertex v1, v2;
	String name;
	IntegerExpression index;
	IntegerExpression value;
	
	public EdgeNotCharAt(String name, Vertex v1, Vertex v2, IntegerExpression index, IntegerExpression value) {
		this.v1 = v1;
		this.v2 = v2;
		this.name = name;
		this.index = index;
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeNotCharAt other = (EdgeNotCharAt) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}

	@Override
	public boolean allVertecisAreConstant() {
		return v1.isConstant() && v2.isConstant();
	}

	@Override
	public Vertex getDest() {
		return v2;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Vertex getSource() {
		return v1;
	}

	@Override
	public List<Vertex> getSources() {
		List<Vertex> result = new ArrayList<Vertex>();
		result.add(v1);
		return result;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	@Override
	public boolean isHyper() {
		return false;
	}

	@Override
	public void setDest(Vertex v) {
		v2 = v;
		
	}

	@Override
	public void setSource(Vertex v) {
		v1 = v;
		
	}
	
	@Override
	public IntegerExpression getIndex() {
		return index;
	}
	
	@Override
	public IntegerExpression getValue() {
		return value;
	}

	@Override
	public Edge cloneAndSwapVertices(Map<Vertex, Vertex> oldToNew) {
		return new EdgeNotCharAt(name, oldToNew.get(v1), oldToNew.get(v2),
				index, value);
	}
}
