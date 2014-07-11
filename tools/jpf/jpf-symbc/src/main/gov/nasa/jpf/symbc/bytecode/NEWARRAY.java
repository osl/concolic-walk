package gov.nasa.jpf.symbc.bytecode;




import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.string.SymbolicLengthInteger;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.ClassLoaderInfo;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;

/**
 * Symbolic version of the NEWARRAY class from jpf-core. Has some extra code to
 * detect if a symbolic variable is being used as the size of the new array, and
 * treat it accordingly.
 * 
 * Someone with more experience should review this :)
 * TODO: to review; Corina: this code does not make too much sense: for now I will comment it out 
 * who wrote it?
 * 
 */

public class NEWARRAY extends gov.nasa.jpf.jvm.bytecode.NEWARRAY {

	public NEWARRAY(int typeCode) {
    	super(typeCode);
    }
	
	@Override
	public Instruction execute( ThreadInfo ti) {
		StackFrame frame = ti.getModifiableTopFrame();

	    arrayLength = frame.pop();
	    Heap heap = ti.getHeap();

	    if (arrayLength < 0){
	      return ti.createAndThrowException("java.lang.NegativeArraySizeException");
	    }

	    // there is no clinit for array classes, but we still have  to create a class object
	    // since its a builtin class, we also don't have to bother with NoClassDefFoundErrors
	    String clsName = "[" + type;
	    ClassInfo ci = ClassLoaderInfo.getCurrentResolvedClassInfo(clsName);

	    if (!ci.isRegistered()) {
	      ci.registerClass(ti);
	      ci.setInitialized();
	    }
	   
	    if (heap.isOutOfMemory()) { // simulate OutOfMemoryError
	      return ti.createAndThrowException("java.lang.OutOfMemoryError",
	                                        "trying to allocate new " +
	                                          getTypeName() +
	                                        "[" + arrayLength + "]");
	    }
	    
	    ElementInfo eiArray = heap.newArray(type, arrayLength, ti);
	    int arrayRef = eiArray.getObjectRef();
	    
	    frame.pushRef(arrayRef);

	    return getNext(ti);
		
		// old code
	    // Corina: I'll comment it out for the time being
	    /*
		StackFrame sf = ti.getModifiableTopFrame();
		Object attr = sf.getOperandAttr();
		
		if(attr instanceof SymbolicLengthInteger) {
			arrayLength = ((SymbolicLengthInteger) attr).solution;
			sf.pop();
		} else 	if(attr instanceof IntegerExpression) {
			if (!PathCondition.flagSolved) {
				// TODO I don't know what to do in this case; I believe this
				// only happens if the array initialization is
				// located before a program branch.
				
				// Corina: in reality here we should do the equivalent of lazy initialization for arrays
				throw new RuntimeException(
						"Path condition is not solved; Check the comments above this line for more details!");
			}
			arrayLength = ((IntegerExpression) attr).solution();
			sf.pop();
		} else {
			arrayLength = sf.pop();
		}

		//the remainder of the code is identical to the parent class
		
	    Heap heap = ti.getHeap();

	    if (arrayLength < 0){
	      return ti.createAndThrowException("java.lang.NegativeArraySizeException");
	    }

	    // there is no clinit for array classes, but we still have  to create a class object
	    // since its a builtin class, we also don't have to bother with NoClassDefFoundErrors
	    String clsName = "[" + type;
	    ClassInfo ci = ClassInfo.getResolvedClassInfo(clsName);

	    if (!ci.isRegistered()) {
	      ci.registerClass(ti);
	      ci.setInitialized();
	    }
	   
	    if (heap.isOutOfMemory()) { // simulate OutOfMemoryError
	      return ti.createAndThrowException("java.lang.OutOfMemoryError",
	                                        "trying to allocate new " +
	                                          getTypeName() +
	                                        "[" + arrayLength + "]");
	    }
	    
	    int arrayRef = heap.newArray(type, arrayLength, ti);
	    sf.push(arrayRef, true);

	    ti.getVM().getSystemState().checkGC(); // has to happen after we push the new object ref
	    
	    return getNext(ti);
	    */
	}
}