
public class NumberExample {
	
	private int value = 0;
	
	public double run(int val) {
		Number n = new Integer(val);
		if(n.doubleValue() == 10.0) {
			System.out.println("foo");
		} else{
			System.out.println("boo");
		}
		if(n.intValue() == 10) {
			System.out.println("foo int");
		} else {
			System.out.println("boo int");
		}
		return (double) value;
	}

	public static void main(String[] args) {
		NumberExample num = new NumberExample();
		num.run(1);
	}
	
}