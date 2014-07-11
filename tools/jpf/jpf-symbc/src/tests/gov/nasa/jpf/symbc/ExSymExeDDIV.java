package gov.nasa.jpf.symbc;
import gov.nasa.jpf.symbc.numeric.*;

public class ExSymExeDDIV {

  public static void main (String[] args) {
	  double x = 5.0;
	  (new ExSymExeDDIV()).test(0.0,x);
  }

  public  int test (double x, double y) {
	double res=x/y;
	return 0;
  }

}

