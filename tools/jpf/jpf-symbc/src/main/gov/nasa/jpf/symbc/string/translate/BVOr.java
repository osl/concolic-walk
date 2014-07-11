package gov.nasa.jpf.symbc.string.translate;

public class BVOr implements BVExpr{
	public BVExpr left, right;
	
	public BVOr (BVExpr left, BVExpr right) {
		this.left = left;
		this.right = right;
	}

	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append ("(");
		sb.append (left.toString());
		sb.append (") OR (");
		sb.append (right.toString());
		sb.append (")");
		return sb.toString();
	}
		
	public String toSMTLib () {
		StringBuffer sb = new StringBuffer ();
		sb.append("(or ");
		sb.append (left.toSMTLib());
		sb.append (" ");
		sb.append (right.toSMTLib());
		sb.append (")");
		return sb.toString();
	}
	
	
}
