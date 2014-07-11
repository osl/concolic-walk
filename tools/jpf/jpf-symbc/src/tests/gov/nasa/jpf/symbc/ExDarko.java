package gov.nasa.jpf.symbc;


public class ExDarko {

	public static void main(String[] args) {
		new ExDarko().unboxed(1,2);
		new ExDarko().boxed(1,2);
		new ExDarko().customBoxed(1,2);
	}

	private void unboxed(int i, int j) {
		if (i == j) {
			System.out.println("HIT");
		}
	}

	private void boxed(int i, int j) {
		if (new Integer(i).equals(new Integer(j))) {
			System.out.println("HIT");
		}
	}

	private void customBoxed(int i, int j) {
		if (new MyInteger(i).equals(new MyInteger(j))) {
			System.out.println("HIT");
		}
	}

}

class MyInteger {
	int value;

	public MyInteger(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof MyInteger)) {
			return false;
		}
		MyInteger other = (MyInteger) obj;
		return this.value == other.value;
	}
}
