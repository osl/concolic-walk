package gov.nasa.jpf.symbc.string.testing;

import gov.nasa.jpf.symbc.string.graph.Edge;

public class EdgeWeight {
	final Class e;
	final int weight;
	
	public EdgeWeight (Class e, int weight) {
		this.e = e;
		this.weight = weight;
	}
}
