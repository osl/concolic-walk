/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree.visualizer;

import gov.nasa.jpf.jvm.bytecode.IfInstruction;
import gov.nasa.jpf.jvm.bytecode.InvokeInstruction;
import gov.nasa.jpf.jvm.bytecode.ReturnInstruction;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.symexectree.Transition;
import gov.nasa.jpf.symbc.symexectree.structure.IfNode;
import gov.nasa.jpf.symbc.symexectree.structure.InvokeNode;
import gov.nasa.jpf.symbc.symexectree.structure.ReturnNode;
import gov.nasa.jpf.symbc.symexectree.structure.StdNode;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;

import java.awt.Color;
import java.util.LinkedList;

import att.grappa.Attribute;
import att.grappa.Node;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class CompleteTreeVisualizer extends AVisualizer {
	
	public CompleteTreeVisualizer(OUTPUT_FORMAT format) {
		super(format);
	}

	@Override
	protected LinkedList<Attribute> getNodeAttr(StdNode treeNode) {
		LinkedList<Attribute> attrs = new LinkedList<>();
		
		StringBuilder lblBuilder = new StringBuilder();
		lblBuilder.append(treeNode.getInstructionContext().getInstr().getMnemonic()).append("\\n")
				  .append("(").append(treeNode.getInstructionContext().getInstr().getFilePos()).append(")\\n");
		for(Transition in : treeNode.getIncomingTransitions()) {
			gov.nasa.jpf.symbc.symexectree.structure.Node srcNode = in.getSrcNode();
			Instruction instr = srcNode.getInstructionContext().getInstr();
			if(instr instanceof IfInstruction) {
				PathCondition pc = treeNode.getInstructionContext().getPathCondition();
				lblBuilder.append(getPathConditionString(pc));
			}
		}
		attrs.add(new Attribute(Attribute.NODE, Attribute.LABEL_ATTR, lblBuilder.toString()));
		
		return attrs;
	}
	
	@Override
	protected LinkedList<Attribute> getNodeAttr(InvokeNode treeNode) {
		LinkedList<Attribute> attrs = new LinkedList<>();
		
		StringBuilder lblBuilder = new StringBuilder();
		lblBuilder.append(treeNode.getInstructionContext().getInstr().getMnemonic()).append("\\n");
		
		Instruction instr = treeNode.getInstructionContext().getInstr();
		if(instr instanceof InvokeInstruction) { // Should not be necessary, but better safe than sorry
			InvokeInstruction invokeInstr = (InvokeInstruction) instr;
			lblBuilder.append("Calling:\\n")
					  .append(invokeInstr.getInvokedMethod().getFullName());
		}
		attrs.add(new Attribute(Attribute.NODE, Attribute.LABEL_ATTR, lblBuilder.toString()));
		attrs.add(new Attribute(Attribute.NODE, Attribute.COLOR_ATTR, Color.red));
		attrs.add(new Attribute(Attribute.NODE, Attribute.SHAPE_ATTR, Attribute.BOX_SHAPE));
		return attrs;
	}

	@Override
	protected LinkedList<Attribute> getNodeAttr(ReturnNode treeNode) {
		LinkedList<Attribute> attrs = new LinkedList<>();
		
		StringBuilder lblBuilder = new StringBuilder();
		lblBuilder.append(treeNode.getInstructionContext().getInstr().getMnemonic()).append("\\n");
		
		Instruction instr = treeNode.getInstructionContext().getInstr();
		if(instr instanceof ReturnInstruction) { // Should not be necessary, but better safe than sorry
			StackFrame frame = treeNode.getInstructionContext().getFrame().getPrevious();
			if(frame != null)
				lblBuilder.append("Returning to:\\n").append(frame.getMethodInfo().getFullName());
		}
		attrs.add(new Attribute(Attribute.NODE, Attribute.LABEL_ATTR, lblBuilder.toString()));
		attrs.add(new Attribute(Attribute.NODE, Attribute.COLOR_ATTR, Color.red));
		attrs.add(new Attribute(Attribute.NODE, Attribute.SHAPE_ATTR, Attribute.BOX_SHAPE));
		return attrs;
	}
	
	@Override
	protected LinkedList<Attribute> getNodeAttr(IfNode treeNode) {
		LinkedList<Attribute> attrs = new LinkedList<>();
		Instruction instr = treeNode.getInstructionContext().getInstr();
		StringBuilder lblBuilder = new StringBuilder();
		lblBuilder.append(instr.getMnemonic()).append("\\n");
		lblBuilder.append("(").append(instr.getFilePos()).append(")");
		
		attrs.add(new Attribute(Attribute.NODE, Attribute.LABEL_ATTR, lblBuilder.toString()));
		attrs.add(new Attribute(Attribute.NODE, Attribute.COLOR_ATTR, Color.blue));
		attrs.add(new Attribute(Attribute.NODE, Attribute.SHAPE_ATTR, Attribute.DIAMOND_SHAPE));
		return attrs;
	}
	
	@Override
	protected LinkedList<Attribute> getFinalNodeAttr(gov.nasa.jpf.symbc.symexectree.structure.Node treeNode) {
		LinkedList<Attribute> attrs = new LinkedList<>();
		Instruction instr = treeNode.getInstructionContext().getInstr();
		StringBuilder lblBuilder = new StringBuilder();
		lblBuilder.append(instr.getMnemonic()).append("\\n");
		lblBuilder.append("(").append(instr.getFilePos()).append(")").append("\\n")
				  .append(this.getPathConditionString(treeNode.getInstructionContext().getPathCondition()));
		
		attrs.add(new Attribute(Attribute.NODE, Attribute.LABEL_ATTR, lblBuilder.toString()));
		attrs.add(new Attribute(Attribute.NODE, Attribute.COLOR_ATTR, Color.red));
		return attrs;
	}
	
	@Override
	protected LinkedList<Attribute> getEdgeAttr(Node srcNode, Node targetNode) {
		return new LinkedList<>();
	}
	
	private String getPathConditionString(PathCondition pc) {
		if(pc != null) {
			StringBuilder pcBuilder = new StringBuilder();
			pcBuilder.append("Path condition:\\n");
			String[] pcs = pc.header.stringPC().split("&&");	
			for(int i = 0; i < pcs.length; i++) {
				pcBuilder.append(pcs[i]);
				if(i != pcs.length - 1)
					pcBuilder.append(" &&\\n");
			}
			return pcBuilder.append("\r").toString();
		} else
			return "";
	}
}
