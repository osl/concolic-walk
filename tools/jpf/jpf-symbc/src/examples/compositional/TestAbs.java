package compositional;

// from Saswat Anand

public class TestAbs {
  int abs(int x) {
	  if(x>0) return x;
	  else if (x==0)
		  	return 100;
	  else
		  return -x;
  }
  void testAbs(int p, int q) {
	  int m = abs(p);
	  int n = abs(q);
	  if(m>n && p>0)
		  assert false;
  }
  public static void main(String[] args) {
		(new TestAbs()).testAbs(0,0);

	}
}
