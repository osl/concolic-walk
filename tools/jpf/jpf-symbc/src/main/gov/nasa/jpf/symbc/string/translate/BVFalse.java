package gov.nasa.jpf.symbc.string.translate;

public class BVFalse implements BVExpr{
	
	public String toString () {
		return "false";
	}
	
	public String toSMTLib () {
		return "false";
	}

}
