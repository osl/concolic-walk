public class JpfTargetTcas {
    public static void main(String[] args){ 
	// JPF-Symbc overrides the concrete argument
	Tcas.start_symbolic(0,0,0,0,0,0,0,0,0,0,0,0);
    }
}
