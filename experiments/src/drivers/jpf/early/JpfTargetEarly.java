public class JpfTargetEarly {
    public static void main(String[] args){ 
	// JPF-Symbc overrides the concrete argument
	Early.commitEarly(0, 0);
    }
}
