package gov.nasa.jpf.symbc;

public class ExLazy {

	public static void test(ExLazy x, int y) {
        if (x == null && y==0) {
               System.out.println(1);
        } else {
                System.out.println(2);
        }
	}
	public static void main (String[] args) {

		  test(null,0);

	  }
}
