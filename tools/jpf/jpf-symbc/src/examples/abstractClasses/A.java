package abstractClasses;

abstract class A  {
	void m () {
		System.out.println(" m in A");
	}
	void n () {
		System.out.println("n in A");
	}
	void f() {
		System.out.println("f in A");
		m ();
		n ();
	}
}