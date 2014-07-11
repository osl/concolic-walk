package sequences;


import gov.nasa.jpf.symbc.Debug;

/**
 *
 * @author Mithun Acharya
 *
 */
public class MySymbolicDriverForBST {
	/*
	To run this driver, you need to configure your JPF command line arguments as
  	+vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory
  	+vm.classpath=.
  	+vm.storage.class=
  	+symbolic.method=methodSequenceDriver(sym#sym)
  	+jpf.report.console.finished=
  	+jpf.listener=gov.nasa.jpf.symbc.SymbolicListener
  	MySymbolicDriverForBST
	*/
	public static void main(String[] args){
		MethodSequenceGeneratorTao methodSequenceGeneratorTao = new MethodSequenceGeneratorTao();
		// <className, number_of_public_methods, sequence_length, range>
		methodSequenceGeneratorTao.invokeMethodSequenceDriver("BST", 3, 3, 3);
		Debug.printPC("\nMySymbolicDriverForStack.testDriver Path Condition: ");
	}

}
