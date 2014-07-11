package gov.nasa.jpf.symbc.string.translate;

public class BVITE implements BVExpr{
	BVExpr ifpart, thenpart, elsepart;
	
	public BVITE (BVExpr ifpart, BVExpr thenpart, BVExpr elsepart) {
		this.ifpart = ifpart;
		this.thenpart = thenpart;
		this.elsepart = elsepart;
	}
	
	public String toSMTLib () {
		StringBuilder sb = new StringBuilder ();
		
		sb.append ("(ite ");
		sb.append (ifpart.toSMTLib());
		sb.append (" ");
		sb.append (thenpart.toSMTLib());
		sb.append (" ");
		sb.append (elsepart.toSMTLib());
		sb.append (")");
		
		return sb.toString();
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		
		sb.append ("IF (");
		sb.append (ifpart.toString());
		sb.append (") THEN (");
		sb.append (thenpart.toString());
		sb.append (") ELSE (");
		sb.append (elsepart.toString());
		sb.append (")");
		
		return sb.toString();
	}
}
