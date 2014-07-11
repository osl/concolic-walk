package gov.nasa.jpf.symbc;

public class ExSymExeResearch {
  
  
  public static void main (String[] args) {
      int x = 3;
      int y = 5;
      ExSymExeResearch inst = new ExSymExeResearch();
      inst.test(x, y);
  }

 
  public int test (int a, int b) { //invokevirtual
	  int result=0;
      System.out.println("Testing ExSymExeResearch");
    if (a >=0 && a <100 && b>=0 && b <100) {
      int sum = a + b;
      int diff = a - b;
      int temp;
      
      if (sum > 0)
          temp = a;
      else
          temp = b;
      if (temp < diff)
          result = temp;
      else
          result = diff;
    }
    return result;
        
  }
}

