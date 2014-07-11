package gov.nasa.jpf.symbc.string.translate;

public class BVConst implements BVExpr{
	public int value;
	
	public BVConst (int value) {
		this.value = value;
	}
	
	public String toString () {
		return String.valueOf(value);
	}
	
	public String toSMTLib () {
		StringBuilder sb = new StringBuilder ();
		
		sb.append ("(_ bv");
		sb.append (value);
		sb.append (" 8)");
		
		return sb.toString();
	}
}

