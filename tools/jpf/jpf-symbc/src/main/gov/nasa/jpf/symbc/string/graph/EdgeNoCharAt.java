package gov.nasa.jpf.symbc.string.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdgeNoCharAt implements Edge{

	Vertex v1, v2;
	String name;
	
	public EdgeNoCharAt (String name, Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
		this.name = name;
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
	public Edge cloneAndSwapVertices(Map<Vertex, Vertex> oldToNew) {
		return new EdgeNoCharAt(name, oldToNew.get(v1), oldToNew.get(v2));
	}

}
