/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.jvm.bytecode.DCMPG;
import gov.nasa.jpf.jvm.bytecode.DCMPL;
import gov.nasa.jpf.jvm.bytecode.FCMPG;
import gov.nasa.jpf.jvm.bytecode.FCMPL;
import gov.nasa.jpf.jvm.bytecode.IfInstruction;
import gov.nasa.jpf.jvm.bytecode.InvokeInstruction;
import gov.nasa.jpf.jvm.bytecode.LCMP;
import gov.nasa.jpf.jvm.bytecode.MONITORENTER;
import gov.nasa.jpf.jvm.bytecode.MONITOREXIT;
import gov.nasa.jpf.jvm.bytecode.ReturnInstruction;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.symbc.symexectree.structure.MonitorEnterNode;
import gov.nasa.jpf.symbc.symexectree.structure.Node;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.Instruction;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class SymbolicExecutionTreeGenerator {

	private Config jpfConf;
	private NodeFactory nodeFactory;
	
	private LinkedList<MethodDesc> symbolicMethods;
	private HashMap<MethodDesc, TranslationUnit> methTUMap;
	
	public SymbolicExecutionTreeGenerator(Config jpfConf, NodeFactory nodeFactory) {
		this.jpfConf = jpfConf;
		this.nodeFactory = nodeFactory;
		
		String[] methods = this.jpfConf.getStringArray("symbolic.method");
		this.symbolicMethods = SymExecTreeUtils.convertJPFConfSymbcDescs(methods);
		this.methTUMap = new HashMap<MethodDesc, TranslationUnit>();
		for(MethodDesc m : this.symbolicMethods) {
			TranslationUnit tu = new TranslationUnit(m);
			this.methTUMap.put(m, tu);
		}
	}
	
	public void generate(InstrContext instrCtx) {
		MethodDesc mi = SymExecTreeUtils.getTargetMethodOfFrame(this.symbolicMethods, instrCtx.getFrame());
		TranslationUnit tu = this.methTUMap.get(mi);
		Node nxtNode = null;
		if(instrCtx.getThreadInfo().isFirstStepInsn()) { //this might cause issues
			return;
		}
		if(this.isBranchingInstr(instrCtx.getInstr())) {
			if(tu.hasBranchingInstrBeenTranslated(instrCtx)) {
				nxtNode = tu.getBranchingInstrNode(instrCtx);
			} else {
				nxtNode = this.constructNode(instrCtx, tu.getSymTree());
				if(nxtNode == null)
					return;
				tu.addBranchingInstrCtx(instrCtx, nxtNode);
			}
		} else {
			nxtNode = this.constructNode(instrCtx, tu.getSymTree());
			if(nxtNode == null)
				return;
		}
		
		if(tu.getPrevNode() != null) {
			if(!skipTransition(tu.getPrevNode(), nxtNode))
				new Transition(tu.getPrevNode(), nxtNode, tu.getSymTree());
		}
		tu.setPrevNode(nxtNode);
	}
	
	private boolean isBranchingInstr(Instruction instr) {
		return instr instanceof IfInstruction || // ifle, ifgt if_cmple etc.
			   instr instanceof LCMP ||
			   instr instanceof FCMPL ||
			   instr instanceof FCMPG ||
			   instr instanceof DCMPG ||
			   instr instanceof DCMPL;
	}
	
	private Node constructNode(InstrContext instrCtx, SymbolicExecutionTree tree) {
		Instruction instr = instrCtx.getInstr();
		if(isBranchingInstr(instr)) {
			return this.nodeFactory.constructIfNode(instrCtx, tree);
		} else if(instr instanceof InvokeInstruction) {
			return this.nodeFactory.constructInvokeNode(instrCtx, tree);
		} else if(instr instanceof ReturnInstruction) {
			return this.nodeFactory.constructReturnNode(instrCtx, tree);
		} else if(instr instanceof MONITORENTER) {
			return this.nodeFactory.constructMonitorEnterNode(instrCtx, tree);
		} else if(instr instanceof MONITOREXIT) {
			return this.nodeFactory.constructMonitorExitNode(instrCtx, tree);
		} else {
			return this.nodeFactory.constructStdNode(instrCtx, tree);
		}
	}
	
	//We skip the construction of some transitions; if-instructions create
	// loops to themselves because of the way JPF executes them
	private boolean skipTransition(Node prevNode, Node nxtNode) {
		return prevNode == nxtNode;
	}
	
	public void addChoice(InstrContext instrCtx, PCChoiceGenerator pcCg) {
		MethodDesc mi = SymExecTreeUtils.getTargetMethodOfFrame(this.symbolicMethods, instrCtx.getFrame());
		TranslationUnit tu = this.methTUMap.get(mi);
		tu.addChoice(instrCtx, pcCg);
	}
	
	public void restoreChoice(InstrContext instrCtx) {
		MethodDesc mi = SymExecTreeUtils.getTargetMethodOfFrame(this.symbolicMethods, instrCtx.getFrame());
		TranslationUnit tu = this.methTUMap.get(mi);
		tu.restoreToPrevChoice();
	}
	
	public Stack<ChoiceContext> getChoices(InstrContext instrCtx) {
		MethodDesc mi = SymExecTreeUtils.getTargetMethodOfFrame(this.symbolicMethods, instrCtx.getFrame());
		TranslationUnit tu = this.methTUMap.get(mi);
		return tu.getChoices();
	}
	
	public LinkedList<SymbolicExecutionTree> getTrees() {
		LinkedList<SymbolicExecutionTree> trees = new LinkedList<SymbolicExecutionTree>();
		for(TranslationUnit tu : this.methTUMap.values())
			trees.add(tu.getSymTree());
		return trees;
	}

	private class TranslationUnit {
		private Stack<ChoiceContext> choices;
		private SymbolicExecutionTree tree;
		private Node prevNode;
		
		private HashMap<InstrContext, Node> branchingInstrToNodeMap;
		
		public TranslationUnit(MethodDesc method) {	
			this.tree = new SymbolicExecutionTree(method);
			this.choices = new Stack();
			this.branchingInstrToNodeMap = new HashMap<InstrContext, Node>();
			this.prevNode = null;
		}
		
		public boolean hasBranchingInstrBeenTranslated(InstrContext instrCtx) {
			return this.branchingInstrToNodeMap.containsKey(instrCtx);
		}
		
		public Node getBranchingInstrNode(InstrContext instrCtx) {
			return this.branchingInstrToNodeMap.get(instrCtx);
		}
		
		public Node getPrevNode() {
			return this.prevNode;
		}
		
		public void setPrevNode(Node node) {
			this.prevNode = node;
		}
		
		public void addChoice(InstrContext instrCtx, PCChoiceGenerator pcCg) {
			this.choices.add(new ChoiceContext(instrCtx, pcCg));
		}
		
		public void addBranchingInstrCtx(InstrContext ifInstrCtx, Node node) {
			this.branchingInstrToNodeMap.put(ifInstrCtx, node);
		}
		
		public Stack<ChoiceContext> getChoices() {
			return this.choices;
		}
		
		public void restoreToPrevChoice() {
			if(this.choices.size() > 0) {
				ChoiceContext choiceCtx = this.choices.peek();
				this.prevNode = this.branchingInstrToNodeMap.get(choiceCtx.getInstrCtx());
				PCChoiceGenerator cg = choiceCtx.getCg();
				if(cg.getProcessedNumberOfChoices() >= cg.getTotalNumberOfChoices())
					this.choices.pop();
			}
		}
		
		public SymbolicExecutionTree getSymTree() {
			return this.tree;
		}
	}
	
	private class ChoiceContext {
		private final PCChoiceGenerator cg;
		private final InstrContext instrCtx;
		
		public ChoiceContext(InstrContext instrCtx, PCChoiceGenerator pcCg) {
			this.cg = pcCg;
			this.instrCtx = instrCtx;
		}

		public PCChoiceGenerator getCg() {
			return cg;
		}

		public InstrContext getInstrCtx() {
			return instrCtx;
		}
	}
}
