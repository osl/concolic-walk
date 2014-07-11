package gov.nasa.jpf.symbc;

public class ExSymExe34 {
	
	C1 c1 = new C1();
	
  /*
   * test symbolic values set in one method and used in another
   * to get method summaries
   */
  public static void main (String[] args) {
	  System.out.println("Testing ExSymExe34");
	  ExSymExe34 inst = new ExSymExe34();
	  inst.setValues(1,2);
	  inst.testC(inst.c1);
  }
  
  public void setValues(int x, int y){
	  c1.putField1(x);
	  c1.putField2(y);
  }
  
  public void testC(C1 c1) {

	  if (c1.getField1() > 0)
		  System.out.println("branch FOO1");
	  else
		  System.out.println("branch FOO2");
	  if (c1.getField2() > 0)
		  System.out.println("branch BOO1");
	  else
		  System.out.println("branch BOO2");
  }
  
  protected class C1{
	  public int field1 = 0;
	  public int field2 = 0;
	  
	  public void putField1(int f1){
		  this.field1 = f1;
	  }
	  
	  public void putField2(int f2){
		  this.field2 = f2;
	  }
	  
	  public int getField1(){
		  return this.field1;
	  }
	  public int getField2(){
		  return this.field2;
	  }
}
}
