package gov.nasa.jpf.symbc.string.translate;

public class BVExtract implements BVExpr{
	public BVExpr varName;
	public int startOffset; 
	public int endOffset;
	
	public BVExtract (BVExpr varName, int startOffset, int endOffset) {
		this.varName = varName;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append (varName);
		sb.append ("[");
		sb.append (startOffset);
		sb.append (":");
		sb.append (endOffset);
		sb.append ("]");
		return sb.toString();
	}
	
	public String toSMTLib () {
		StringBuffer sb = new StringBuffer ();
		sb.append ("((_ extract ");
		sb.append (startOffset);
		sb.append (" ");
		sb.append (endOffset);
		sb.append (") ");
		sb.append (varName.toSMTLib());
		sb.append (")");
		return sb.toString();
	}
}
