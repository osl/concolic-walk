package gov.nasa.jpf.symbc.string.translate;

public class BVLT implements BVExpr{
	
	BVExpr left, right;
	
	public BVLT (BVExpr left, BVExpr right) {
		this.left = left;
		this.right = right;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append ("(");
		sb.append (left.toString());
		sb.append (") < (");
		sb.append (right.toString());
		sb.append (")");
		return sb.toString();
	}
	
	public String toSMTLib () {
		StringBuffer sb = new StringBuffer ();
		sb.append("(bvult ");
		sb.append (left.toSMTLib());
		sb.append (" ");
		sb.append (right.toSMTLib());
		sb.append (")");
		return sb.toString();
	}

}
