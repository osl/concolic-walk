package gov.nasa.jpf.symbc;

import gov.nasa.jpf.util.test.TestJPF;
import org.junit.Test;

class S_E_test {
	public O_S_test[] s;
	S_E_test() {
		s = new O_S_test[1];
		s[0] = new O_S_test();
	}
}
class O_S_test {
	public P_test 		d_p;		
	public P_test 		c_p;		
	
	public O_S_test () {
		
		//d_p = new P_test();
		c_p = new P_test();
			
	}
}

class P_test {
	int v_M;
}

public class TestSymbc extends TestJPF {
    S_E_test e = new S_E_test();
    O_S_test x;

    int las;

    //     sets inputs
    public void setSYMINPUTS_JPF(int l_j_c, int c_v_M) {
        las = l_j_c;
        x.c_p.v_M = c_v_M;
    }
  
   public static void main(String[] args) {
        runTestsOfThisClass(args);
   }

    @Test
    public void testSymbcDriver() {
        TestSymbc frd = new TestSymbc();
        frd.x = frd.e.s[0];
        frd.setSYMINPUTS_JPF(0, 0);
        if (frd.las == 1) {
        	if (frd.x.c_p.v_M >= 0) {
                System.out.println("br 1");
            }
        } else{
        	if (frd.x.c_p.v_M >= 0) {
            	System.out.println("br 2");;
            }
        }
    }
} 
