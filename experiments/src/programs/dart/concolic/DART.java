package concolic;

// taken from the original DART paper

public class DART {
	public static void test(int x, int y) {
		if (x*x*x > 0){
			if(x>0 && y==10)
				abort();
		} else {
			if (x>0 && y==20)
				abort();
		}
	}

    public static void abort() {
    	assert(false);
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test(2,10);
	}
}
