package gov.nasa.jpf.symbc.string;

public class StringUtility {
	public static String findLeftSide (String entireString, String rightString) {
		
		int i = 1;
		while (i < rightString.length()) {
			if (entireString.charAt(entireString.length() - i) != rightString.charAt(rightString.length() - i)) {
				break;
			}
			i ++;
		}
		int index = entireString.length() - i;
		return entireString.substring(0, index);
	}
	
	public static String findRightSide (String entireString, String leftString) {
		
		int i = 0;
		while (i < leftString.length()) {
			if (entireString.charAt(i) != leftString.charAt(i)) {
				break;
			}
			i ++;
		}
		int index = i;
		return entireString.substring(index);
	}
}
