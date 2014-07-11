package compositional;

public class DART_SMART {
// from P Godefroid
	
	int is_positive(int x) {
		if(x>0) return 1;
		return 0;
	}
	
	static int N = 100;
	void top(int [] s) {// N inputs
		int cnt = 0;
		for (int i=0; i< 100; i++)
			cnt=cnt+is_positive(s[i]);
		if(cnt==3)
			assert(false); //error
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] in = new int[N];
		(new DART_SMART()).top(in);

	}
}
