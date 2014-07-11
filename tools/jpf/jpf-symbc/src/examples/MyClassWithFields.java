import gov.nasa.jpf.symbc.Symbolic;


public class MyClassWithFields {
    @Symbolic("true")
    public int field1;

	@Symbolic("true")
    public int field2;
    
    public int myMethod1() {
		int z = field1 + field2;
		if (z > 0) {
			z = 1;
		} else {
			z = z - field1;
		}
		z = field1 * z;
		return z;
    }
}