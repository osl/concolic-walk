package symbolicheap;

import gov.nasa.jpf.symbc.Debug;

public class NodeBuggy {

	int elem;
    NodeBuggy next;
    NodeBuggy left;
    NodeBuggy right;
	
    public NodeBuggy(int x) {
    	elem = x;
    	next = null;
    	left = null;
    	right = null;
    }
	
    void addGCIssue() {

       // this.left = (NodeBuggy) Debug.makeSymbolicRef("tmp", new NodeBuggy(5));
        Debug.printSymbolicRef(left, "left = ");
        this.left = null;

        if (this.right != null) {

        
        Debug.printSymbolicRef(right, "right = ");
        System.out.println(this.right.left);
        }

}


	void addSimple3() {
		int depth = 0;
    	NodeBuggy bigson = this;
    	while (bigson.left != null || bigson.right != null) {
    		if (bigson.right != null) {
    			bigson = bigson.right;			
    		} else {
    			bigson = bigson.left;
    		}
    		depth++;
    		if (depth == 2)
    			return;
		}
	}

	void addSimple3b() {
		int depth = 2;
    	NodeBuggy bigson = this;
    	while (((bigson.left != null || bigson.right != null)) && depth > 0) {
    		if (bigson.right != null) {
    			bigson = bigson.right;			
    		} else {
    			bigson = bigson.left;
    		}
    		depth--;
		}
	}
	static void addSimple(NodeBuggy n) {
		if(n==null) return;
		int depth = 2;
    	NodeBuggy bigson = n;
    	while (((bigson.left != null || bigson.right != null)) && depth > 0) {
    		if (bigson.right != null) {
    			bigson = bigson.right;			
    		} else {
    			bigson = bigson.left;
    		}
    		depth--;
		}
	}
	
	
	void addSimple4() {
    	NodeBuggy bigson = this;
    	if (bigson.left != null || bigson.right != null) {
    		if (bigson.right != null) {
    			bigson = bigson.right;			
    		} else {
    			bigson = bigson.left;
    		}
		}
    	if (bigson.left != null || bigson.right != null) {
    		if (bigson.right != null) {
    			bigson = bigson.right;			
    		} else {
    			bigson = bigson.left;
    		}
		}
	}
	
	public static void runTest(int x) {
    	NodeBuggy X = new NodeBuggy(5);
        X = (NodeBuggy) Debug.makeSymbolicRef("input_X", X);
        if (X != null) {
            X.addSimple3b();//broken
            //X.addSimple4(); //working
        }
        //Debug.printSymbolicRef(X, "node = ");
    }
	
	public static void main(String[] args) {	
		//addSimple(null);
		//runTest(1);
		NodeBuggy X = new NodeBuggy(5);
        X = (NodeBuggy) Debug.makeSymbolicRef("input_X", X);
		if(X!=null) X.addGCIssue();
	}

}
