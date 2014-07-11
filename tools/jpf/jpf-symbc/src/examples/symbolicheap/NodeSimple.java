package symbolicheap;

import gov.nasa.jpf.symbc.Debug;

public class NodeSimple {

	int elem;
    NodeSimple next;
  
	
    public NodeSimple(int x) {
    	elem = x;
    	next = null;
    }
	
    public void test(NodeSimple n) {
    	if(n!=null && n.next!=null)
    		System.out.println("2 elements");
    }
	public static void main(String[] args) {	
		
		NodeSimple X = new NodeSimple(5);
        (new NodeSimple(0)).test(X);
	}

}
