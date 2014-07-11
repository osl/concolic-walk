package abstractClasses;

public class Test {
	
	public static void foo(A a) {
		if(a != null) {
			System.out.println("chain===== start");
			a.f();
			System.out.println("chain ======end");
		}
	}
	
	public static void main (String[] args) {
		A a = null;
		foo(a);
	}
}