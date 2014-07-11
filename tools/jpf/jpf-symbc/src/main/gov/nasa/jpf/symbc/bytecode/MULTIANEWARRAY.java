package gov.nasa.jpf.symbc.bytecode;


import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.string.SymbolicLengthInteger;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.ClassLoaderInfo;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;

/**
 * Symbolic version of the MULTIANEWARRAY class from jpf-core. Like NEWARRAY,
 * the difference from the jpf-core version is a snippet to detect if a symbolic
 * variable is being used as the size of the new array, and treat it accordingly.
 * 
 * And someone should review this one too :)
 * TODO: to review
 */

public class MULTIANEWARRAY extends gov.nasa.jpf.jvm.bytecode.MULTIANEWARRAY {

	public MULTIANEWARRAY(String typeName, int dimensions) {
		super(typeName, dimensions);
	}

	@Override
	public Instruction execute(ThreadInfo ti) {
		arrayLengths = new int[dimensions];
		StackFrame sf = ti.getModifiableTopFrame();
		for (int i = dimensions - 1; i >= 0; i--) {
			Object attr = sf.getOperandAttr();
			
			if(attr instanceof SymbolicLengthInteger) {
				arrayLengths[i] = ((SymbolicLengthInteger) attr).solution;
				sf.pop();
			} else 	if(attr instanceof IntegerExpression) {
				arrayLengths[i] = ((IntegerExpression) attr).solution();
				sf.pop();
			} else {
				arrayLengths[i] = sf.pop();
			}
		}

		//the remainder of the code is identical to the parent class
		
		// there is no clinit for array classes, but we still have  to create a class object
		// since its a builtin class, we also don't have to bother with NoClassDefFoundErrors
		ClassInfo ci = ClassLoaderInfo.getCurrentResolvedClassInfo(type);
		if (!ci.isRegistered()) {
			ci.registerClass(ti);
			ci.setInitialized();
		}
		    
		int arrayRef = allocateArray(ti.getHeap(), type, arrayLengths, ti, 0);

		// put the result (the array reference) on the stack
		sf.push(arrayRef, true);

		return getNext(ti);
	}
}
