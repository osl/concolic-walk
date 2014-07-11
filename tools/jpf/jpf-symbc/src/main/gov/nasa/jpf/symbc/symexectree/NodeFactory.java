/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import gov.nasa.jpf.symbc.symexectree.structure.IfNode;
import gov.nasa.jpf.symbc.symexectree.structure.InvokeNode;
import gov.nasa.jpf.symbc.symexectree.structure.MonitorEnterNode;
import gov.nasa.jpf.symbc.symexectree.structure.MonitorExitNode;
import gov.nasa.jpf.symbc.symexectree.structure.Node;
import gov.nasa.jpf.symbc.symexectree.structure.ReturnNode;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public abstract class NodeFactory {

	public NodeFactory() {
		
	}
	public abstract MonitorEnterNode constructMonitorEnterNode(InstrContext instrCtx);
	
	public abstract MonitorExitNode constructMonitorExitNode(InstrContext instrCtx);
	
	public abstract Node constructStdNode(InstrContext instrCtx);
	
	public abstract IfNode constructIfNode(InstrContext instrCtx);

	public abstract InvokeNode constructInvokeNode(InstrContext instrCtx);

	public abstract ReturnNode constructReturnNode(InstrContext instrCtx);
	
	public final MonitorEnterNode constructMonitorEnterNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		MonitorEnterNode n = this.constructMonitorEnterNode(instrCtx);
		tree.addNode(n);
		return n;
	}
	
	public final MonitorExitNode constructMonitorExitNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		MonitorExitNode n = this.constructMonitorExitNode(instrCtx);
		tree.addNode(n);
		return n;
	}	
	
	public final Node constructStdNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		Node n = this.constructStdNode(instrCtx);
		tree.addNode(n);
		return n;
	}
	
	public final IfNode constructIfNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		IfNode n = this.constructIfNode(instrCtx);
		tree.addNode(n);
		return n;
	}
	
	public final InvokeNode constructInvokeNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		InvokeNode n = this.constructInvokeNode(instrCtx);
		tree.addNode(n);
		return n;
	}
	
	public final ReturnNode constructReturnNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		ReturnNode n = this.constructReturnNode(instrCtx);
		tree.addNode(n);
		return n;
	}
	
}
