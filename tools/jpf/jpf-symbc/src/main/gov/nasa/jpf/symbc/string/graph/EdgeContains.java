package gov.nasa.jpf.symbc.string.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdgeContains implements Edge{

	Vertex source, dest;
	String name;
	
	public EdgeContains (String name, Vertex source, Vertex end) {
		this.name = name;
		this.source = source;
		this.dest = dest;
	}
	
	@Override
	public boolean allVertecisAreConstant() {
		return source.isConstant() && dest.isConstant();
	}

	@Override
	public Vertex getDest() {
		return dest;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Vertex getSource() {
		return source;
	}

	@Override
	public List<Vertex> getSources() {
		List<Vertex>  result = new ArrayList<Vertex>();
		result.add(source);
		return result;
	}

	@Override
	public boolean isDirected() {
		return true;
	}

	@Override
	public boolean isHyper() {
		return false;
	}

	@Override
	public void setDest(Vertex v) {
		this.dest = v;
	}

	@Override
	public void setSource(Vertex v) {
		this.source = v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int smallest, biggest, v1HashCode, v2HashCode;
		v1HashCode = source.hashCode();
		v2HashCode = dest.hashCode();
		if (v1HashCode > v2HashCode) {
			smallest = v2HashCode;
			biggest = v1HashCode;
		}
		else {
			smallest = v1HashCode;
			biggest = v2HashCode;
		}
		result = prime * result + ((source == null) ? 0 : smallest);
		result = prime * result + ((dest == null) ? 0 : biggest);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EdgeContains)) {
			return false;
		}
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeContains other = (EdgeContains) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} 
		if (dest == null) {
			if (other.dest != null)
				return false;
		} 
		
		if (source.equals(other.source) && dest.equals(other.dest)) {
			//System.out.println("[NOTE] " + this.toString() + " is equal to " + other.toString());
			return true;
		}
		else if (source.equals(other.dest) && dest.equals(other.source)) {
			//System.out.println("[NOTE] " + this.toString() + " is equal to " + other.toString());
			return true;
		}
		
		return false;
	}

	@Override
	public Edge cloneAndSwapVertices(Map<Vertex, Vertex> oldToNew) {
		return new EdgeContains(name, oldToNew.get(source), oldToNew.get(dest));
	}
}
