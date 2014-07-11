package gov.nasa.jpf.symbc.green;

//import gov.nasa.jpf.symbc.probsym.Analyze;

public class MattPaperExample {

	public static void covered(int br) {
	  // Analyze.coverage(""+br);
	}

	public static int m(int x, int y) {
		if (x < 0) x = -x;
		if (y < 0) y = -y;
		if (x < 10) return 1;
		else if (9 < y) return -1;
		else return 0;
	}

	public static void main(String[] args) {
		covered(10 + m(1, 1));
	}

}
