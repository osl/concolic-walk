/**
 * 
 */
package gov.nasa.jpf.symbc.symexectree;

import gov.nasa.jpf.vm.MethodInfo;


/**
 * @author Kasper S. Luckow <luckow@cs.aau.dk>
 *
 */
public class MethodDesc {

	private final String methodName;
	private final int argsNum;
	
	public MethodDesc(String methodName, int argsNum) {
		this.methodName = methodName;
		this.argsNum = argsNum;
	}
	
	public MethodDesc(MethodInfo mi) {
		this.methodName = mi.getBaseName();
		this.argsNum = mi.getNumberOfArguments();
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	//TODO: make a more appropriate short name used for the templates
	public String getShortMethodName() {
		String shortName = this.methodName.substring(this.methodName.lastIndexOf('.') + 1, this.methodName.length());
		return shortName + "_" + this.argsNum + "_";
	}

	public int getArgsNum() {
		return argsNum;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + argsNum;
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDesc other = (MethodDesc) obj;
		if (argsNum != other.argsNum)
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return methodName + "(" + this.argsNum + ")";
	}
}
