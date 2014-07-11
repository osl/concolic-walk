package strings;

//This example featured in the paper: "Modeling Imperative String Operations with Transducers"
public class WU_FTPD {
	public static void main (String [] args) {
		site_exec("!!!!!!!!!!!!!!!!!!!!!!!!!!!!%n");
	}

	//Some precission and speedup is still needed here
	public static void site_exec(String cmd) {
		String PATH = "/home/ftp/bin";
		int sp = cmd.indexOf(' ');
		int j;
		String result;
		if (sp == -1) {
			j = cmd.lastIndexOf('/');
			result = cmd.substring(j);
		}
		else {
			j = cmd.lastIndexOf('/', sp);
			result = cmd.substring(j);
		}
		//Take everything to lowercase
		if (result.length()  + PATH.length()> 32) { //Prevent buffer overflow
			System.out.println ("Will cause bufferoverflow");
			return;
		}
		String buf = PATH + result; 
		if (buf.contains ("%n")){
			throw new RuntimeException ("String may contain threat");
		}
		//System.out.println (buf);

	}
	
	//Some precission and speedup is still needed here
	public static void site_execLL(String cmd) {
		String PATH = "/home/ftp/bin";
		int sp = cmd.indexOf(' ');
		int j;
		String result;
		int slash = 0;
		if (sp == -1) {
			while (cmd.indexOf('/', slash) != -1) {
				slash++;
			}
		}
		else {
			int temp = cmd.indexOf('/', slash);
			while (temp < sp) {
				slash = temp + 1;
				temp = cmd.indexOf('/', slash);
			}
		}
		System.out.println("Slash: " + slash);
		result = cmd.substring(slash);
		//Take everything to lowercase
		
		if (result.length()  + PATH.length()> 32) { //Prevent buffer overflow
			System.out.println ("Will cause bufferoverflow");
			return;
		}
		String buf = PATH.concat (result);
		if (buf.contains ("%n")){
			throw new RuntimeException ("String may contain threat");
		}
		System.out.println (buf);

	}
}
