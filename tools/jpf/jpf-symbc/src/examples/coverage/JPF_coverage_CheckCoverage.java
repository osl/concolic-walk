package coverage;

import gov.nasa.jpf.vm.MJIEnv;
import gov.nasa.jpf.symbc.numeric.PathCondition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JPF_coverage_CheckCoverage {
	private static Set<String> paths = new HashSet<String>();
	private static int caseNum = 0;

	public static void processTestCase(MJIEnv env, int objRef, int pathRef) {
		String path = env.getStringObject(pathRef);
    	if (! paths.contains(path)) {
    		paths.add(path);
    		Map<String,Object> varsVals = getPcVarsVals(env);
    		int x = (Integer)varsVals.get("x");
    		int y = (Integer)varsVals.get("y");
    		int origZ = new MyClassOriginal().myMethod(x, y);
    		int annoZ = new MyClassWithPathAnnotations().myMethod(x, y);
    		
    		caseNum++;
    		if (origZ == annoZ) {
    			System.out.format("TestCase %s:  x = %s  y = %s\nPath: %s\n\n",
    					caseNum, x, y, path);
    		} else {
    			System.out.format("Error for TestCase %s:  x = %s  y = %s\nPath: %s\n\n",
    					caseNum, x, y, path);
    			System.out.format("The annotated and original code got different results.\n" +
    					"Annotated Result: %s\n" +
    					"Original Result: %s\n\n",
    					annoZ, origZ);
    		}
    		
    	} else {
    		System.out.println("Already saw '" + path + "'");
    	}
    }
    
    private static Map<String,Object> getPcVarsVals(MJIEnv env) {
		Map<String,Object> varsVals = new HashMap<String,Object>();
		PathCondition pc = PathCondition.getPC(env);
		
		if (pc != null) {
			pc.solve();
			pc.header.getVarVals(varsVals);
		}
		return varsVals;
	}
}
