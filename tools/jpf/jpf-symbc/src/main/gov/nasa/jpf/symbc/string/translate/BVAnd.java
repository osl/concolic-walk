package gov.nasa.jpf.symbc.string.translate;

public class BVAnd implements BVExpr{
	public BVExpr left, right;
	
	public BVAnd (BVExpr left, BVExpr right) {
		this.left = left;
		this.right = right;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append ("(");
		sb.append (left.toString());
		sb.append (") AND (");
		sb.append (right.toString());
		sb.append (")");
		return sb.toString();
	}
	
	public String toSMTLib () {
		StringBuffer sb = new StringBuffer ();
		sb.append("(and ");
		sb.append (left.toSMTLib());
		sb.append (" ");
		sb.append (right.toSMTLib());
		sb.append (")");
		return sb.toString();
	}
}
