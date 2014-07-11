package gov.nasa.jpf.symbc.string;

import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SymbolicIntegerGenerator {
	
	Map<String, SymbolicInteger> map;
	
	public SymbolicIntegerGenerator () {
		map = new HashMap<String, SymbolicInteger>();
	}
	
	public SymbolicInteger create (String name, int l, int u) {
		SymbolicInteger result = map.get(name);
		if (result == null) {
			//System.out.println("[SymbolicIntegerGenerator] Making new: " + name);
			result = new SymbolicInteger(name, l, u);
			map.put(name, result);
		}
		return result;
	}
}
