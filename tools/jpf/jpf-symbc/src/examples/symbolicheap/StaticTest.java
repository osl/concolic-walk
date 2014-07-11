package symbolicheap;

import gov.nasa.jpf.symbc.Debug;

public class StaticTest {

	int elem;
    static StaticTest sfield;
    
   
	
    static public void test(StaticTest n) {
    	sfield = n;
    	if(sfield!=null && sfield.elem>0)
    		System.out.println("test static >0");
    	else
    		System.out.println("test static <=0");
    }
	public static void main(String[] args) {	
		test(null);
	}

}
