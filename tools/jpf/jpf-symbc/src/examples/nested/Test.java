package nested;


public class Test {

	public static void foo(O o) {
		if(o != null) {
			if(o instanceof A) {
				System.out.println("it is an instance of A");
				if(!(o instanceof C)) {
					System.out.println("it is not an instance of C");
					assert (o instanceof B);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		O o = null;
		foo(o);
	}
}