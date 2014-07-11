package gov.nasa.jpf.symbc.string.graph;

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

import java.util.List;
import java.util.Map;

public interface Edge {
	public String getName ();
	
	public Vertex getSource();
	
	public void setSource(Vertex v);
	
	public List<Vertex> getSources ();
	
	public Vertex getDest ();
	
	public void setDest (Vertex v);
	
	public boolean isHyper ();
	
	public boolean isDirected ();
	
	public boolean allVertecisAreConstant();

	/**
	 * Create a deep clone of the edge, replacing any vertexes with the ones in
	 * 'oldToNew'
	 * 
	 * @param oldToNew
	 *            Map with vertices to be replaced
	 * @return Cloned Edge with vertices replaced
	 */
	
	public Edge cloneAndSwapVertices(Map<Vertex, Vertex> oldToNew); 
}
