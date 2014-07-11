/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.symbc.symexectree.Transition;
import gov.nasa.jpf.symbc.symexectree.structure.IfNode;
import gov.nasa.jpf.symbc.symexectree.structure.InvokeNode;
import gov.nasa.jpf.symbc.symexectree.structure.ReturnNode;
import gov.nasa.jpf.symbc.symexectree.structure.StdNode;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;
import gov.nasa.jpf.vm.Instruction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;

import att.grappa.Attribute;
import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public abstract class AVisualizer {
	
	private HashMap<gov.nasa.jpf.symbc.symexectree.structure.Node, Node> visitedTreeNodesMap;
	private OUTPUT_FORMAT format;

	private int uniqueID;
	
	public AVisualizer(OUTPUT_FORMAT format) {
		this.format = format;
		this.uniqueID = 0;
	}
	
	public void prettyPrintSymTrees(List<SymbolicExecutionTree> trees, String dotOutputBasePath) throws VisualizerException {
		for(SymbolicExecutionTree g : trees) {
			try {
				prettyPrintSymTree(g, dotOutputBasePath);
			} catch (Exception e) {
				throw new VisualizerException(e);
			}
		}
	}
	
	public void prettyPrintSymTree(SymbolicExecutionTree tree, String dotOutputBasePath) throws FileNotFoundException, InterruptedException {
		Graph grappaGraph = makeGrappaGraph(tree);
		File file = new File(dotOutputBasePath + 
							 ((dotOutputBasePath.endsWith("/")) ? "" : "/") + 
							 tree.getTargetMethod().getMethodName() + ".dot"); 
		FileOutputStream fo = new FileOutputStream(file);
		grappaGraph.printGraph(fo);
		
		try {
			fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(format != OUTPUT_FORMAT.DOT) {
			if(!(SystemUtils.IS_OS_LINUX ||
				 SystemUtils.IS_OS_MAC_OSX ||
				 SystemUtils.IS_OS_MAC))
				throw new VisualizerException("UNIX-like OS required for outputting symbolic execution tree as " + format.getFormat());
			convertDotFile(file, format);
			file.delete();
		}
	}
	
	private Graph makeGrappaGraph(SymbolicExecutionTree tree) {
		visitedTreeNodesMap = new HashMap<gov.nasa.jpf.symbc.symexectree.structure.Node, Node>();
		Graph grappaGraph = new Graph(tree.getTargetMethod().getShortMethodName());
		Attribute graphAttr = new Attribute(Attribute.SUBGRAPH, 
										   	Attribute.LABEL_ATTR, 
										   	tree.getTargetMethod().getShortMethodName());
		grappaGraph.setAttribute(graphAttr);
		
		recursivelyTraverseSymTree(tree.getRootNode(), grappaGraph);
		return grappaGraph;
	}
	
	private Node recursivelyTraverseSymTree(gov.nasa.jpf.symbc.symexectree.structure.Node treeNode, Graph grappaGraph) {
		if(visitedTreeNodesMap.containsKey(treeNode)) {
			return visitedTreeNodesMap.get(treeNode);
		}
		Instruction instr = treeNode.getInstructionContext().getInstr();
		Node targetNode = new Node(grappaGraph, instr.getMnemonic() + this.uniqueID++);
		
		LinkedList<Attribute> attrs = new LinkedList<>();
		if(treeNode.getOutgoingTransitions().size() == 0)
			attrs.addAll(this.getFinalNodeAttr(treeNode));
		else {
			if(treeNode instanceof InvokeNode) {
				attrs.addAll(this.getNodeAttr((InvokeNode)treeNode));
			} else if(treeNode instanceof ReturnNode) {
				attrs.addAll(this.getNodeAttr((ReturnNode)treeNode));
			} else if(treeNode instanceof IfNode) {
				attrs.addAll(this.getNodeAttr((IfNode)treeNode));
			} else if(treeNode instanceof StdNode){
				attrs.addAll(this.getNodeAttr((StdNode)treeNode));
			} else {
				throw new SymExecTreeVisualizerException("Node with type: " + treeNode.getClass().getCanonicalName() + " is not supported");
			}
		}
		
		for(Attribute attr : attrs)
			targetNode.setAttribute(attr);
			
		visitedTreeNodesMap.put(treeNode, targetNode);
		for(Transition t : treeNode.getOutgoingTransitions()) {
			Node destinationNode = recursivelyTraverseSymTree(t.getDstNode(), grappaGraph);
			Edge basicBlockEdge = new Edge(grappaGraph, 
										   targetNode,
										   destinationNode);
			LinkedList<Attribute> edgeAttrs = this.getEdgeAttr(targetNode, destinationNode);
			for(Attribute attr : edgeAttrs)
				basicBlockEdge.setAttribute(attr);
			grappaGraph.addEdge(basicBlockEdge);
		}
		return targetNode;
	}
	
	/**
	 * Override this for specifying the attributes for edges
	 * in the graph
	 * @param srcNode, targetNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getEdgeAttr(Node srcNode, Node targetNode);
	/**
	 * Override this method if you want different attributes
	 * for the 'normal' nodes (e.g. aload, istore) in the graph.
	 * @param treeNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getNodeAttr(StdNode treeNode);
	
	/**
	 * Override this method if you want different attributes
	 * for the 'invoke' nodes in the graph.
	 * @param treeNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getNodeAttr(InvokeNode treeNode);
	
	/**
	 *  Override this method if you want different attributes
	 * for the 'return' nodes in the graph.
	 * @param treeNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getNodeAttr(ReturnNode treeNode);
	
	/**
	 *  Override this method if you want different attributes
	 * for the 'if' nodes in the graph.
	 * @param treeNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getNodeAttr(IfNode treeNode);
	
	/**
	 *  Override this method if you want different attributes
	 * for the 'final' nodes in the graph.
	 * @param treeNode
	 * @return
	 */
	protected abstract LinkedList<Attribute> getFinalNodeAttr(gov.nasa.jpf.symbc.symexectree.structure.Node treeNode);
	
	/**
	 * @param file
	 * @throws InterruptedException
	 */
	private void convertDotFile(File file, OUTPUT_FORMAT format) throws InterruptedException {
		String dotCmd = "dot " + file.getPath() + " -T" + format.getFormat() + " -o " + file.getPath().replace(".dot", "." + format.getFormat());
		try {
			Process p = Runtime.getRuntime().exec(dotCmd);
			p.waitFor();
			p.exitValue();
			p.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
