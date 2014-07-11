package gov.nasa.jpf.symbc.concolic.walk;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.symbc.concolic.walk.RealVector;
import gov.nasa.jpf.symbc.concolic.walk.RealVectorSpace;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.LinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.MinMax;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

import java.util.Arrays;
import java.util.BitSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestConcolicWalkSolver {

  private SymbolicInteger a;
  private SymbolicInteger b;
  private SymbolicInteger c;
  private RealVectorSpace space;

  @BeforeClass
  public static void initializeGlobalState() {
    Config defaultConfig = new Config(new String[0]);
    MinMax.collectMinMaxInformation(defaultConfig);
  }

  @Before
  public void setUp() {
    this.a = new SymbolicInteger("a");
    this.b = new SymbolicInteger("b");
    this.c = new SymbolicInteger("c");
    this.space = RealVectorSpace.forDimensions(a, b, c);
  }

  // Linear Zero Estimation

  @Test
  public void estimateIdentity() {
    RealVector p = intPoint(1, 0);
    Number valueAtP = 1;
    RealVector q = intPoint(-2, 0);
    Number valueAtQ = -2;

    RealVector r = ConcolicWalkSolver.linearlyEstimateZero(p, valueAtP, q, valueAtQ, Comparator.EQ);

    assertThat(r.get(a), equalTo(0.0));
    assertThat(r.get(b), equalTo(0.0));
  }

  @Test
  public void estimatingZeroSlopeShouldCauseRandomStep() {
    RealVector p = intPoint(1, 0);
    Number valueAtP = 23.0;
    RealVector q = intPoint(0, 1);
    Number valueAtQ = 23.0;

    RealVector r = ConcolicWalkSolver.linearlyEstimateZero(p, valueAtP, q, valueAtQ, Comparator.EQ);

    assertThat(r, not(equalTo(intPoint(0, 0))));
  }

  // Error Computation

  @Test
  public void errorOfFulfilledEqualityConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.EQ, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(7, 7)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedEqualityConstraintShouldBeGreaterZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.EQ, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(3, 7)) > 0.0);
  }

  @Test
  public void errorOfFulfilledGreaterThanConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.GT, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(42, -23)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedGreaterThanConstraintShouldBeGreaterZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.GT, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(3, 7)) > 0.0);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(-5, -5)) > 0.0);
  }

  @Test
  public void errorOfFulfilledGreaterThanOrEqualConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.GE, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(42, 23)), equalTo(0.0));
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(-42, -42)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedGreaterThanOrEqualConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.GE, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(3, 7)) > 0.0);
  }

  @Test
  public void errorOfFulfilledLessThanConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.LT, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(-42, 23)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedLessThanConstraintShouldBeGreaterZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.LT, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(213, 7)) > 0.0);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(-11, -11)) > 0.0);
  }

  @Test
  public void errorOfFulfilledLessThanOrEqualConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.LE, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(-2, 23)), equalTo(0.0));
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(1023, 1023)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedLessThanOrEqualConstraintShouldBeGreaterZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.LE, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(213, 7)) > 0.0);
  }

  @Test
  public void errorOfFulfilledInequalityConstraintShouldBeZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.NE, b);
    assertThat(ConcolicWalkSolver.computeError(c, intPoint(7, 3)), equalTo(0.0));
  }

  @Test
  public void errorOfViolatedInequalityConstraintShouldBeGreaterZero() {
    Constraint c = new LinearIntegerConstraint(a, Comparator.NE, b);
    assertTrue(ConcolicWalkSolver.computeError(c, intPoint(3, 3)) > 0.0);
  }

  // Finding the Maximum Error

  @Test
  public void indexOfMaxShouldFindMaximum() {
    double[] values = new double[] { 1.0, 3.14, -4.2, 0.0, 1.0 };
    assertThat(ConcolicWalkSolver.indexOfMaxIgnoringTabu(values, new int[5]), equalTo(1));
  }

  @Test
  public void indexOfMaxShouldIgnoreTabuValues() {
    double[] values = new double[] { 0.75, 3.14, -4.2, 2.71, 1.0 };
    int[] tabuIndices = new int[] {0, 1, 0, 0, 0};
    assertThat(ConcolicWalkSolver.indexOfMaxIgnoringTabu(values, tabuIndices), equalTo(3));
  }

  // Lookup Tables

  @SuppressWarnings("unchecked")
  @Test
  public void constraintsLookupTableShouldContainAllConstraints() {
    PathCondition pc = new PathCondition();
    Constraint aEqB = new LinearIntegerConstraint(a, Comparator.EQ, b);
    Constraint bGtC = new LinearIntegerConstraint(b, Comparator.GT, c);
    pc.prependUnlessRepeated(aEqB);
    pc.prependUnlessRepeated(bGtC);

    Map<Constraint, BitSet> varByCstr = new IdentityHashMap<>();
    List<Constraint>[] cstrByVar = new List[space.dimensions().size()];
    ConcolicWalkSolver.populateLookupTables(space, pc, cstrByVar, varByCstr);

    assertThat(cstrByVar[space.indexOf(a)], equalTo(Arrays.asList(aEqB)));
    assertThat(cstrByVar[space.indexOf(b)], equalTo(Arrays.asList(bGtC, aEqB)));
    assertThat(cstrByVar[space.indexOf(c)], equalTo(Arrays.asList(bGtC)));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void variableLookupTableShouldContainAllVariables() {
    PathCondition pc = new PathCondition();
    Constraint aEqB = new LinearIntegerConstraint(a, Comparator.EQ, b);
    Constraint bGtC = new LinearIntegerConstraint(b, Comparator.GT, c);
    pc.prependUnlessRepeated(aEqB);
    pc.prependUnlessRepeated(bGtC);

    Map<Constraint, BitSet> varByCstr = new IdentityHashMap<>();
    List<Constraint>[] cstrByVar = new List[space.dimensions().size()];
    ConcolicWalkSolver.populateLookupTables(space, pc, cstrByVar, varByCstr);

    assertTrue(varByCstr.get(aEqB).get(space.indexOf(a)));
    assertTrue(varByCstr.get(aEqB).get(space.indexOf(b)));
    assertFalse(varByCstr.get(aEqB).get(space.indexOf(c)));

    assertFalse(varByCstr.get(bGtC).get(space.indexOf(a)));
    assertTrue(varByCstr.get(bGtC).get(space.indexOf(b)));
    assertTrue(varByCstr.get(bGtC).get(space.indexOf(c)));
  }

  // Auxiliary Testing Methods

  private RealVector intPoint(int aVal, int bVal) {
    return space.makeVector().set(a, aVal).set(b, bVal).build();
  }
}
