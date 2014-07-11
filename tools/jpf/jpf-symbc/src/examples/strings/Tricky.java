package strings;

import java.util.Random;

//This example featured in the paper: "Precise Analysis of String Expressions". Can not be solved yet
public class Tricky {
	static String bar(int n, int k, String op) {
		if (k==0) return "";
		return op+n+"]"+bar(n-1,k-1,op)+" ";
	}

	static String foo(int n) {
		StringBuffer b = new StringBuffer();
		if (n<2) b.append("(");
		for (int i=0; i<n; i++) b.append("(");
		String s = bar(n-1,n/2-1,"*").trim();
		String t = bar(n-n/2,n-(n/2-1),"+").trim();
		//return b.toString()+n+(s+t).replace(']',')');
		return b.toString()+n+(s+t);
	}

	public static void test (int n) {
		String s = foo (n);
		System.out.println(s);
	}

	public static void main(String args[]) {
		/*int n = new Random().nextInt();
		System.out.println(new Tricky().foo(n));*/
		test(5);
	}
}
