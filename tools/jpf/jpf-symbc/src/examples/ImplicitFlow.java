
public class ImplicitFlow {
	
	int func(int H){
		int O;
		if (H == 0) O = 0;
		else if (H == 1) O = 1;
		else if (H == 2) O = 2;
		else if (H == 3) O = 3;
		else if (H == 4) O = 4;
		else if (H == 5) O = 5;
		else if (H == 6) O = 6;
		else O = 0;
		return O;
	}
	
	public static void main(String[] args) {
		ImplicitFlow imflow = new ImplicitFlow();
		System.out.println("Output is: " + imflow.func(1));
	}
	
}