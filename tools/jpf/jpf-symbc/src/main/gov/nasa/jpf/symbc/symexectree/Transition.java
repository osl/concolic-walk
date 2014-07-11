/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import gov.nasa.jpf.symbc.symexectree.structure.Node;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class Transition implements ISymbolicExecutionTreeElement {

	private Node srcNode;
	private Node dstNode;
	
	public Transition(Node src, Node dst) {
		this.srcNode = src;
		this.srcNode.addOugoingTransitions(this);
		this.dstNode = dst;
		this.dstNode.addIncomingTransition(this);
	}
	
	public Transition(Node src, Node dst, SymbolicExecutionTree tree) {
		this(src, dst);
		tree.addTransition(this);
	}
	
	public Node getSrcNode() {
		return srcNode;
	}
	
	public Node getDstNode() {
		return dstNode;
	}
	
	public void setSrcNode(Node srcNode) {
		this.srcNode = srcNode;
	}

	public void setDstNode(Node dstNode) {
		this.dstNode = dstNode;
	}

	@Override
	public void accept(SymbolicExecutionTreeVisitor visitor) {
		visitor.visit(this);
		this.dstNode.accept(visitor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dstNode == null) ? 0 : dstNode.hashCode());
		result = prime * result + ((srcNode == null) ? 0 : srcNode.hashCode());
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
		Transition other = (Transition) obj;
		
		return new EqualsBuilder().append(srcNode, other.srcNode)
								  .append(dstNode, other.dstNode)
								  .isEquals();
	}
	
}
