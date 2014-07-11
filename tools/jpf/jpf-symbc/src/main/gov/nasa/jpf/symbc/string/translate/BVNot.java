package gov.nasa.jpf.symbc.string.translate;

public class BVNot implements BVExpr{
	public BVExpr expr;
	
	public BVNot (BVExpr expr) {
		this.expr = expr;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		
		sb.append ("not (");
		sb.append (expr.toString());
		sb.append (")");
		
		return sb.toString();
	}
	
	public String toSMTLib () {
		StringBuffer sb = new StringBuffer ();
		
		sb.append("(not ");
		sb.append(expr.toSMTLib());
		sb.append(")");
		
		return sb.toString();
	}
}
