package gov.nasa.jpf.symbc.string.translate;

public class BVTrue implements BVExpr{

	public String toString () {
		return "true";
	}
	
	public String toSMTLib () {
		return "true";
	}
}
