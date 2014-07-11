package uberlazy;

/**
 * @author Neha Rungta
 * 
 * This class tests whether all the non-deterministic choices arising
 * from polymorphism are accounted for. The initialization of the 
 * data structure n is all the classes that Node is an instanceof
 * It also serves as a test case when for the partition function 
 * at the non-deterministic choice of the instanceof class
 * 
 * Without the polymorphism only the second print statement will 
 * be executed. With polymorphism the first statements prints twice
 * and the else prints once. With the correct partition function
 * each statement prints once. 
 **/

import gov.nasa.jpf.symbc.Symbolic;


public class TestInstanceOfByteCode {
	
	@Symbolic("true")
	Node n;

	
	public void run () {
		if(n != null) {
			if(n instanceof dblNode) {
				System.out.println("You can store reals in this data structure");
			} else {
				System.out.println("Don't store a Real in here");
			}
		}
	}
	
	public static void main(String[] args) {
		TestInstanceOfByteCode tt = new TestInstanceOfByteCode();
		tt.run();
	}
	
}