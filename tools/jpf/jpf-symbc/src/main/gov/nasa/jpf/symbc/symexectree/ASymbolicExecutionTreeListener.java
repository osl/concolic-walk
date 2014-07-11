/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import java.util.HashSet;
import java.util.LinkedList;

import scale.common.RuntimeException;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.PropertyListenerAdapter;
import gov.nasa.jpf.jvm.bytecode.FieldInstruction;
import gov.nasa.jpf.jvm.bytecode.GETFIELD;
import gov.nasa.jpf.jvm.bytecode.GETSTATIC;
import gov.nasa.jpf.jvm.bytecode.PUTFIELD;
import gov.nasa.jpf.jvm.bytecode.PUTSTATIC;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.PCChoiceGenerator;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.symexectree.structure.SymbolicExecutionTree;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.choice.ThreadChoiceFromSet;

/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public abstract class ASymbolicExecutionTreeListener extends PropertyListenerAdapter {

	private boolean noMoreChoicesForTarget;
	private boolean targetHasStarted;
	private int injectedSymbID;
	
	private SymbolicExecutionTreeGenerator SETGenerator;
	protected Config jpfConf;
	
	public ASymbolicExecutionTreeListener(Config conf, JPF jpf) {
		this.noMoreChoicesForTarget = this.targetHasStarted = false;
		this.injectedSymbID = 0;
		
		this.jpfConf = conf;
		this.SETGenerator = new SymbolicExecutionTreeGenerator(conf, this.getNodeFactory());
	}
	
	protected abstract NodeFactory getNodeFactory();
	protected abstract void processSymbExecTree(LinkedList<SymbolicExecutionTree> trees);

	@Override
	public void instructionExecuted(VM vm, ThreadInfo currentThread, Instruction nextInstruction, Instruction executedInstruction) {
		if (!vm.getSystemState().isIgnored()) {
			MethodInfo mi = executedInstruction.getMethodInfo();
			if(SymExecTreeUtils.isInSymbolicCallChain(mi, currentThread.getTopFrame(), this.jpfConf)) {
				
			}
		}
	}
	
	@Override
	public void executeInstruction(VM vm, ThreadInfo currentThread, Instruction instructionToExecute) {
		if (!vm.getSystemState().isIgnored()) {
			MethodInfo mi = instructionToExecute.getMethodInfo();
			if(SymExecTreeUtils.isInSymbolicCallChain(mi, currentThread.getTopFrame(), this.jpfConf)) {
				if(instructionToExecute instanceof GETFIELD ||
					instructionToExecute instanceof GETSTATIC) {
						ThreadInfo ti = vm.getCurrentThread();
						if(ti.getTopFrame() != null) {
							if(ti.getTopFrame().getSlots().length > 0) {
								FieldInstruction fieldInstr = (FieldInstruction) instructionToExecute;
								ElementInfo ei = fieldInstr.peekElementInfo(ti);
								if(ei != null) {
									if(ei.isShared()) {
										if(!ei.hasFieldAttr(Expression.class) && !ei.isFrozen()) {//Assuming the if the field already has an attr of type Expression, it is symbolic.
											FieldInfo fi = fieldInstr.getFieldInfo();
											ei.addFieldAttr(fi, new SymbolicInteger("SHARED SYMB " + this.injectedSymbID++));
										}
									}
								}
							}
						}
					}

				if(instructionToExecute instanceof PUTFIELD ||
				   instructionToExecute instanceof PUTSTATIC) {
					ThreadInfo ti = vm.getCurrentThread();
					FieldInstruction putInstr = (FieldInstruction) instructionToExecute;
					
					ElementInfo eiOwner = putInstr.peekElementInfo(ti);
					FieldInfo fi = putInstr.getFieldInfo();
					if(fi.isReference() && eiOwner.isShared()) {
						int objRef = ti.getTopFrame().peek();
						if(objRef == -1) {
							//do some clever stuff
						} else {
							ElementInfo ei = ti.getElementInfo(objRef);
							this.setSharedness(ei, ti);
						}
					}					
				}
				
				this.SETGenerator.generate(new InstrContext(instructionToExecute, 
															 currentThread.getTopFrame().clone(),
															 currentThread,
															 PathCondition.getPC(vm)));
			}
		}
	}
	
	private void setSharedness(ElementInfo ei, ThreadInfo ti) {
		this.visitedEi.clear();
		recursivelySetSharedness(ei, ti);
		// TODO: are the static fields shared anyway?
	}
	
	private HashSet<ElementInfo> visitedEi = new HashSet<ElementInfo>();
	
	private void recursivelySetSharedness(ElementInfo ei, ThreadInfo ti) {
		if(visitedEi.contains(ei))
			return;
		ClassInfo ci = ei.getClassInfo();
		FieldInfo[] fis = ci.getDeclaredInstanceFields();
		
		for(FieldInfo fi : fis) {
			if(fi.isReference()) {
				int objRef = ei.getReferenceField(fi);
				if(objRef == -1)
					continue;
				ElementInfo thisEi = ti.getElementInfo(objRef);
				if(thisEi == null)
					throw new RuntimeException("ElementInfo is null!");
				thisEi.setShared(true);
				visitedEi.add(thisEi);
				recursivelySetSharedness(thisEi, ti);
			}
		}
	}
	
	@Override
	public void searchFinished(Search search) {
		LinkedList<SymbolicExecutionTree> trees = this.SETGenerator.getTrees();
		processSymbExecTree(trees);
	}
	
	@Override
	public void choiceGeneratorRegistered(VM vm, ChoiceGenerator<?> nextCG, ThreadInfo currentThread, Instruction executedInstruction) {
		if (!vm.getSystemState().isIgnored()) { //Not sure if this check is necessary...
			if(nextCG instanceof PCChoiceGenerator) {
				MethodInfo mi = executedInstruction.getMethodInfo();
				if(SymExecTreeUtils.isInSymbolicCallChain(mi, currentThread.getTopFrame(), this.jpfConf)) {
					this.SETGenerator.addChoice(new InstrContext(executedInstruction, 
																 currentThread.getTopFrame().clone(),
																 currentThread,
																 PathCondition.getPC(vm)),
																 (PCChoiceGenerator)nextCG);
				}
			}
		}
	}
	
	@Override
	public void stateBacktracked(Search search) {
		//if (!search.getVM().getSystemState().isIgnored()) {
			if(search.getVM().getChoiceGenerator() instanceof PCChoiceGenerator) {
				if(SymExecTreeUtils.isInSymbolicCallChain(search.getVM().getInstruction().getMethodInfo(), search.getVM().getCurrentThread().getTopFrame(), this.jpfConf)) {
					InstrContext instrCtx = new InstrContext(search.getVM().getInstruction(), 
							search.getVM().getCurrentThread().getTopFrame().clone(),
							search.getVM().getCurrentThread(),
							PathCondition.getPC(search.getVM()));
					this.SETGenerator.restoreChoice(instrCtx);
					if(this.SETGenerator.getChoices(instrCtx).isEmpty())
						noMoreChoicesForTarget = true;
				}
			}
	//	}
	}
	

	@Override
	public void choiceGeneratorAdvanced (VM vm, ChoiceGenerator<?> currentCG) {
		if (!vm.getSystemState().isIgnored()) { //Not sure if this check is necessary...
			if(currentCG instanceof ThreadChoiceFromSet) {
				ThreadChoiceFromSet tc = (ThreadChoiceFromSet) currentCG;				
				ThreadInfo tiChoices[] = tc.getChoices();				
				for(int i = 0; i < tiChoices.length; i++) {
					if(tiChoices[i].getTopFrame() != null) { //We select the thread executing the symbolic method(s) we are targeting
						MethodInfo mInfos[] = tiChoices[i].getTopFrame().getClassInfo().getDeclaredMethodInfos();
						for(MethodInfo mInfo : mInfos) {
							if(SymExecTreeUtils.isMethodInfoSymbolicTarget(mInfo, this.jpfConf)) {
								tc.select(i);
								this.targetHasStarted = true;
								return;
							}
						}
					}
				}
				if(this.targetHasStarted || 
				   this.noMoreChoicesForTarget)
					vm.ignoreState(true);
			}
		}
	}
}
