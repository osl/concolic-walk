package strings;

public class MSExample {

  //@Symbolic("true")
  public static String s = "http://www./EasyChair";

  private static boolean IsEasyChairQuery(String str) {
    // (1) check that str contains "/" followed by anything not
    // containing "/" and containing "EasyChair"
    int lastSlash = str.lastIndexOf('/');
    if (lastSlash < 0) {
      return false;
    }
    String rest = str.substring(lastSlash + 1);
    if (!rest.contains("EasyChair")) {
      return false;
    }
    // (2) Check that str starts with "http://"
    if (!str.startsWith("http://")) {
      return false;
    }
    // (3) Take the string between "http://" and the last "/".
    // if it starts with "www." strip the "www." off
    String t =
        str.substring("http://".length(), lastSlash);
    if (t.startsWith("www.")) {
    	
      t = t.substring("www.".length());
    }
    // (4) Check that after stripping we have either "live.com"
    // or "google.com"
    if (!(t.equals("live.com")) && !(t.equals("google.com"))) {
      return false;
    }
    // s survived all checks 
    //throw new RuntimeException("Give string that satisfies this");*/
    return true;
  } 

  public static void doTest() {
    IsEasyChairQuery(s);
  }

  public static void main(String srgs[]) {
    doTest();
    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
  }
}
