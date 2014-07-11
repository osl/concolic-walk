package gov.nasa.jpf.symbc;
import gov.nasa.jpf.util.test.TestJPF;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class TestSymbolicJPF extends TestJPF {
  
  static final String TEST_CLASS = "gov.nasa.jpf.numeric.TestSymbolic";
  static final String NO_TRACE = "+jpf.report.console.property_violation=error";
  static final String INSN_FACTORY = "+vm.insn_factory.class=gov.nasa.jpf.symbc.SymbolicInstructionFactory";
  static final String STORAGE = "+vm.storage.class= ";
  
  public static void main(String args[]) {
    JUnitCore.main(TEST_CLASS + "JPF");
  }
  
  //------------- IADD
  @Test
  public void testIADD_bothSymbolic () {
    String[] args = { STORAGE, NO_TRACE, INSN_FACTORY, TEST_CLASS, "testIADD_bothSymbolic" };
    verifyNoPropertyViolation(args);
  }

  @Test
  public void testIADD_oneConcrete () {
    String[] args = { STORAGE, NO_TRACE, INSN_FACTORY, TEST_CLASS, "testIADD_oneConcrete" };
    verifyNoPropertyViolation(args);
  }
  //------------- ISUB
  @Test
  public void testISUB_bothSymbolic () {
    String[] args = { STORAGE, NO_TRACE, INSN_FACTORY, TEST_CLASS, "testISUB_bothSymbolic" };
    verifyNoPropertyViolation(args);
  }

  @Test
  public void testISUB_oneConcrete () {
    String[] args = { STORAGE, NO_TRACE, INSN_FACTORY, TEST_CLASS, "testISUB_oneConcrete" };
    verifyNoPropertyViolation(args);
  }
}
