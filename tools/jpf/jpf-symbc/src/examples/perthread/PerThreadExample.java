package perthread;
/**
 * from test for missed paths in concurrent threads with very little interaction
 * in core
 */

class PerThreadExample {
	
	  static class X {
	    boolean pass;
	   X next;
	  }

	  static class InstanceFieldPropagation extends Thread {
	    X myX; // initially not set

	    public void run() {
	    	doWork();
	    }
	    
	    public void doWork() {
	    	try {
	    		if (myX!=null) {
	    			myX.next = new X();
	    			X localX = new X();
	    			localX.next = new X();
	    			localX.next.next = new X();
	    			myX = localX;
	    			
	    			if(myX.next==null) {
	    				System.out.println("In if");
	    				int a = 2;
	    			}
	    		}
	    	} catch(NullPointerException e) {}
	    }

	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		    
		      InstanceFieldPropagation mp = new InstanceFieldPropagation();
		      mp.start();
		      
		      X x = new X();
		      System.out.println("M: new " + x);
		      mp.myX = x;        // (0) x not shared until this GOT executed
		     
		      //Thread.yield();  // this would expose the error
		      System.out.println("M: x.pass=true");
		      x.pass = true;     // (1) need to break BEFORE assignment or no error
	}
		 
}

