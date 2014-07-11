package gov.nasa.jpf.symbc;

public class ExSymExeLongBytecodes2 {
	
  public static void main (String[] args) {
	  long x = 3;
	  long y = 5;
	  ExSymExeLongBytecodes2 inst = new ExSymExeLongBytecodes2();
	  inst.test(x, y);
  }

  /*
   * test LADD, LAND, LCMP, LOR, LREM, LSHL, LSHR, LSUM, LUSHR, LXOR bytecodes
   * no globals
   */
  
  public void test (long x, long z) { //invokevirtual
	  
	  System.out.println("Testing ExSymExeLongBytecodes2");
	  
	  long a = x;
	  long b = z;
	  long c = 34565; 
/*
	  long remain = a % c; //LREM - not supported
	  long res = 999999999999L & remain;  //LAND - not supported
	  long res2 = remain/c;  //LDIV - not supported
	  remain = remain | res2;  //LOR - not supported
	  remain = remain ^ res2; //LXOR - not supported
	  res = res << res2; //LSHL - not supported
	  res = res >> res2; //LSHR - not supported
	  res = res >>> res2; //LUSHR - not supported
	  long div = a / b; //LDIV - not supported  
*/	  
	  long sum = a + b; //LADD
	  
	  long temp = ( a < 0)? sum: b-a;
	  	 
	  if ( temp > c)
		  System.out.println("branch diff > c");
	  else
		  System.out.println("branch diff <= c");
		  
  }
}