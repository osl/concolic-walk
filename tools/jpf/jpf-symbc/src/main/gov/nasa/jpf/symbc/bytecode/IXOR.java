package gov.nasa.jpf.symbc.bytecode;



import gov.nasa.jpf.symbc.numeric.*;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;

public class IXOR extends gov.nasa.jpf.jvm.bytecode.IXOR {

	@Override
	public Instruction execute (ThreadInfo th) {
		StackFrame sf = th.getModifiableTopFrame();
		IntegerExpression sym_v1 = (IntegerExpression) sf.getOperandAttr(0); 
		IntegerExpression sym_v2 = (IntegerExpression) sf.getOperandAttr(1);
		
		if(sym_v1==null && sym_v2==null)
			return super.execute(th); // we'll still do the concrete execution
		else {
			int v1 = sf.pop();
			int v2 = sf.pop();
			sf.push(0, false); // for symbolic expressions, the concrete value does not matter
		
			IntegerExpression result = null;
			if(sym_v1!=null) {
				if (sym_v2!=null)
					result = sym_v1._xor(sym_v2);
				else // v2 is concrete
					result = sym_v1._xor(v2);
			}
			else if (sym_v2!=null)
				result = sym_v2._xor(v1);
			sf.setOperandAttr(result);
		
			//System.out.println("Execute IADD: "+result);
		
			return getNext(th);
		}
	
	}

}
