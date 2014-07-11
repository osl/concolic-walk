package gov.nasa.jpf.symbc;

import gov.nasa.jpf.vm.MJIEnv;
import gov.nasa.jpf.vm.NativePeer;
import gov.nasa.jpf.annotation.MJI;
import gov.nasa.jpf.symbc.numeric.PathCondition;

public class JPF_gov_nasa_jpf_symbc_TestUtils extends NativePeer {
	@MJI
	public static int getPathCondition____Ljava_lang_String_2(MJIEnv env, int objRef) {
		PathCondition pc = PathCondition.getPC(env);
		if (pc != null) {
			return env.newString(pc.stringPC());
		}
		return env.newString("");
	}
	@MJI
	public static int getSolvedPathCondition____Ljava_lang_String_2(MJIEnv env, int objRef) {
		PathCondition pc = PathCondition.getPC(env);
		if (pc != null) {
			pc.solve();
			return env.newString(pc.toString());
		}
		return env.newString("");
	}
}
