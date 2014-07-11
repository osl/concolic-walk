package gov.nasa.jpf.symbc;

import gov.nasa.jpf.util.test.TestJPF;

public class InvokeTest extends TestJPF {

  protected static final String INSN_FACTORY = "+jvm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory";

  protected static String makePCAssertString(String location, String goodPC, String badPC) {
    return String.format("Bad Path condition in %s:\nEXPECTED:\n%s\nACTUAL:\n%s\n", location, goodPC, badPC);
  }

  protected static String trimPC(String pc) {
    return pc.substring(pc.indexOf("\n") + 1);
  }

  // Check whether the current PatchPathcondition looks like "# = 1\n <newPC> && <oldPC>"
  protected static boolean pcMatches(String newPC) {
    String currentPC = TestUtils.getPathCondition();
    currentPC = trimPC(currentPC);
    newPC = trimPC(newPC);
    return newPC.equals(currentPC);
  }

  protected static String joinPC(String pc, String oldPC) {
    pc = trimPC(pc);
    oldPC = trimPC(oldPC);
    if (oldPC.length() > 0) {
      return pc + " && " + oldPC;
    }
    return pc;
  }
}
