//
// Copyright (C) 2014 The University of Illinois Board of Trustees.
// All Rights Reserved.
//
// The software in this Java package is distributed under the MIT License.
// See the MIT-LICENSE file at the top of the distribution directory tree
// for the complete license.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package gov.nasa.jpf.symbc.concolic.walk;

import static gov.nasa.jpf.symbc.concolic.walk.ConstraintIterator.eachConstraint;
import static gov.nasa.jpf.symbc.concolic.walk.RealVector.minus;
import static gov.nasa.jpf.symbc.concolic.walk.RealVector.plus;
import static gov.nasa.jpf.symbc.concolic.walk.RealVector.times;
import static gov.nasa.jpf.symbc.concolic.walk.Util.evaluateAndSubtractSides;
import static gov.nasa.jpf.symbc.concolic.walk.Util.isEmpty;
import static gov.nasa.jpf.symbc.concolic.walk.Util.isSatisfied;
import static gov.nasa.jpf.symbc.concolic.walk.Util.printDebug;
import static gov.nasa.jpf.symbc.concolic.walk.Util.union;
import static gov.nasa.jpf.symbc.concolic.walk.Util.variablesIn;
import gov.nasa.jpf.symbc.concolic.walk.ConstraintSplitter;
import gov.nasa.jpf.symbc.concolic.walk.RealVector;
import gov.nasa.jpf.symbc.concolic.walk.RealVectorSpace;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicConstraintsGeneral;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of the <em>Concolic Walk</em> algorithm for solving complex
 * arithmetic path conditions.
 * 
 * <p>
 * The {@link gov.nasa.jpf.symbc.concolic.PCAnalyzer} uses this algorithm to
 * determine the satisfiability of path conditions if the
 * {@link gov.nasa.jpf.symbc.SymbolicInstructionFactory#heuristicWalkMode} flag
 * is set (via the {@code symbolic.heuristic_walk} configuration key).
 * 
 * <p>
 * The paper "Solving Complex Path Conditions through Heuristic Search on
 * Induced Polytopes" by Peter Dinges and Gul Agha, 2014, describes
 * the algorithm in detail.  In particular, see section 3.
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
public class ConcolicWalkSolver {

  // NOTE: The terms used in the paper slightly differ from the ones used here.

  /**
   * Number of algorithm iterations added for each constraint in the path
   * condition. The paper speaks of <em>steps</em> instead of iterations and
   * denotes this constant as <em>I</em>. 
   */
  public static int ITERATIONS_PER_CONSTRAINT = 150;
  /**
   * Number of random neighbor {@link RealVector points} to generate per iteration.
   * The paper calls points <em>environments</em> and denotes this constant
   * as <em>R</em>.  
   */
  public static int NEIGHBORS_GENERATED_PER_ITERATION = 10;
  /**
   * Multiplier for computing how many iterations a variable is tabu if changing
   * it failed to yield a better point. The paper uses this constant and
   * {@link #MIN_TABU_ITERATIONS} to compute the number of tabu iterations
   * <em>T</em>.
   */
  public static float TABU_ITERATIONS_PER_VARIABLE = 0.5f;
  /**
   * Minimum number of iterations that a variable is tabu if changing it failed
   * to yield a better point. See {@link #TABU_ITERATIONS_PER_VARIABLE} above.
   */
  public static int MIN_TABU_ITERATIONS = 3;
  /**
   * Enable trying the hard-coded {@link #SEEDED_VALUES} for the changed variable?
   */
  public static boolean ENABLE_SEEDING = false;
  /**
   * Enable estimating additional neighbors that <em>should</em> satisfy one
   * of the constraints violated by the current variable if the constraint was
   * linear?  See the discussion of Finding Neighbors in section 3.3 of the paper.
   */
  public static boolean ENABLE_BISECTION = true;
  
  private final static int RANDOMIZATION_RADIUS_EXPONENT = 12;
  /** Error value used if the error function of a constraint returns NaN */
  private final static double NAN_ERROR = 0.125 * Double.MAX_VALUE;
  private final static double[] SEEDED_VALUES =
    { 0.0, 1.0, -1.0, Double.MAX_VALUE, Double.MIN_VALUE };

  private final static Random random = new Random();

  /**
   * Calls {@link #solve(PathCondition, SymbolicConstraintsGeneral)}.
   */
  public boolean isSatisfiable(PathCondition pc, SymbolicConstraintsGeneral solver) {
    return solve(pc, solver);
  }

  /**
   * Returns {@code true} if the Concolic Walk algorithm could find a solution
   * for the given path condition; {@code false} otherwise.
   * 
   * If a solution is found, the method sets the {@code flagSolved} of the given
   * {@code pc} to {@code true} and assigns the solution's values to the
   * variables in the {@code pc}.
   * 
   * <p>
   * The Concolic Walk algorithm is incomplete, which means that the method may
   * return {@code false} despite the path condition being satisfiable.
   */
  public boolean solve(PathCondition pc, SymbolicConstraintsGeneral solver) {
    if (pc == null || isEmpty(pc)) {
      return true;
    }
    // TODO: Find constants (one side is variable)
    // TODO: Copy nonLinear constraints and substitute the constants.
    PathCondition linearPc = new PathCondition();
    PathCondition nonLinearPc = new PathCondition();
    ConstraintSplitter.splitInto(pc, linearPc, nonLinearPc);  // Algorithm 1, lines 2--3

    if (!solver.solve(linearPc)) {  // lines 4--5
      return false;
    }
    if (isEmpty(nonLinearPc)) {
      PathCondition.flagSolved = true;
      return true;
    }
    PathCondition.flagSolved = solveWithAdaptiveVariableSearch(linearPc, nonLinearPc);

    return PathCondition.flagSolved;
  }

  /**
   * Returns {@code true} if the Concolic Walk algorithm could find a solution
   * for the given non-linear constraints on the polytope described by the given
   * linear constraints.
   * 
   * If a solution is found, the method assigns the solution's values to the
   * variables in the {@code nonLinearPc}.
   * 
   * @param linearPc
   *          the polytope (described by a set of linear constraints) in which
   *          the algorithm searches for solutions. The method assumes that the
   *          linear constraints have been solved and that the solution values
   *          have been assigned to the appearing variables.
   * @param nonLinearPc
   *          the non-linear constraints to solve.
   * 
   * @see Algorithms 1 and 3 in the paper.
   */
  private static boolean solveWithAdaptiveVariableSearch(PathCondition linearPc, PathCondition nonLinearPc) {
    assert !isEmpty(nonLinearPc);

    // Partially based on the paper "Yet Another Local Search Method for Constraint Solving"
    // by Philippe Codognet and Daniel Diaz, 2001.

    // NOTE: End-of-line comments give the variable names and line numbers
    //       used in Algorithms 1 and 2 of the paper.
    
    // Interpret every variable in the path condition as a dimension
    // in a real-valued vector space.
    RealVectorSpace vectorSpace =
        RealVectorSpace.forDimensions(union(variablesIn(linearPc), variablesIn(nonLinearPc)));
    int numberOfVariables = vectorSpace.dimensions().size();

    RealVector p = makeVectorFromSolutions(vectorSpace, variablesIn(linearPc));  // Alg. 1: \alpha, line 4

    printDebug(ConcolicWalkSolver.class, "Linear PC: ", linearPc);
    printDebug(ConcolicWalkSolver.class, "Linear PC solution: ", p);
    printDebug(ConcolicWalkSolver.class, "Solving non-linear PC\n", nonLinearPc);

    // Initialize lookup tables and local variables
    Map<Constraint, BitSet> variableIndicesByConstraint = new IdentityHashMap<>(nonLinearPc.count());
    @SuppressWarnings("unchecked")
    List<Constraint>[] constraintsByVariableIndex = new List[numberOfVariables];
    populateLookupTables(vectorSpace, nonLinearPc, constraintsByVariableIndex, variableIndicesByConstraint);

    double[] errorByVariables = new double[numberOfVariables];  // Alg. 1: \epsilon
    int[] tabuVariables = new int[numberOfVariables];  // Alg. 1: \tau

    int iterationCount = 1;  // i
    int iterationLimit = nonLinearPc.count() * ITERATIONS_PER_CONSTRAINT;  // Alg. 1: I

    while (!isSatisfied(nonLinearPc, p)) {  // Alg. 1: line 7
      if (iterationCount > iterationLimit) {  // Alg. 1: line 8
        printDebug(ConcolicWalkSolver.class, "Could not find solution within ", iterationLimit, " iterations");
        return false;
      }
      ++iterationCount;  // Alg. 1: line 9

      // Compute errors
      double errorAtP = 0.0;  // Alg. 1: e_\alpha
      Arrays.fill(errorByVariables, 0.0);  // Alg. 1: line 14
      for (Constraint c : eachConstraint(nonLinearPc)) {  // Alg. 1: lines 15--20
        double e = computeError(c, p);
        errorAtP += e;
        incrementElements(errorByVariables, variableIndicesByConstraint.get(c), e);
      }
      printDebug(ConcolicWalkSolver.class, "p = ", p, " -> error ", errorAtP);

      // Try to find a better solution by modifying the "worst" non-tabu variable
      int wiggleVarIndex = indexOfMaxIgnoringTabu(errorByVariables, tabuVariables);
      if (wiggleVarIndex == -1) { // All variables might be tabu,  Alg. 1: lines 10--13
        for (int i = 0; i < tabuVariables.length; ++i) {
          p = makeRandomNeighborInPolytope(p, linearPc, vectorSpace.dimensions().get(i));
          if (p == null) {
            return false;
          }
          tabuVariables[i] = 0;
        }
        printDebug(ConcolicWalkSolver.class, "All variables are tabu.  Took random step to ", p);
        continue;
      }
      Object wiggleVar = vectorSpace.dimensions().get(wiggleVarIndex);  // Alg. 1: x, line 21
      printDebug(ConcolicWalkSolver.class, "Wiggling ", wiggleVar);

      // Find best neighbor (Algorithm 3)
      
      double minError = Double.POSITIVE_INFINITY;  // Alg. 3: e_\mu
      RealVector minNeighbor = null;
      for (int i = 0; i < NEIGHBORS_GENERATED_PER_ITERATION; ++i) {
        RealVector q = makeRandomNeighborInPolytope(p, linearPc, wiggleVar);  // Alg. 3: \beta
        RealVector r = null;  // Alg. 3: \gamma

        if (q == null) { // No random neighbor could be found
          break;
        }

        double errorAtQ = computeError(nonLinearPc, q);  // Alg. 3: e_\beta, line 7
        double errorAtR = Double.POSITIVE_INFINITY;  // Alg. 3: e_\gamma

        if (ENABLE_BISECTION && constraintsByVariableIndex[wiggleVarIndex] != null) {  // Alg. 3: line 5
          // Pick a random unsatisfied constraint
          List<Constraint> constraintsForVar = new ArrayList<>(constraintsByVariableIndex[wiggleVarIndex]);
          Collections.shuffle(constraintsForVar);
          Constraint constraint = null;
          for (int k = 0; k < constraintsForVar.size(); ++k) {
            constraint = constraintsForVar.get(k);
            if (!isSatisfied(constraint, p)) {
              break;
            }
          }
          Number valueAtP = evaluateAndSubtractSides(constraint, p);
          Number valueAtQ = evaluateAndSubtractSides(constraint, q);
          r = linearlyEstimateZero(p, valueAtP, q, valueAtQ, constraint.getComparator());  // Alg. 3: line 6

          if (isSatisfied(linearPc, r)) {
            errorAtR = computeError(nonLinearPc, r);
          }
        }

        printDebug(ConcolicWalkSolver.class, "Random neighbors");
        printDebug(ConcolicWalkSolver.class, "    q = ", q, " -> error ", errorAtQ);
        printDebug(ConcolicWalkSolver.class, "    r = ", r, " -> error ", errorAtR);

        if (errorAtQ < minError) {  // Alg. 3: lines 9--12
          minError = errorAtQ;
          minNeighbor = q;
        }
        if (errorAtR < minError) { // Alg. 3: lines 13--16
          minError = errorAtR;
          minNeighbor = r;
        }
      }

      if (ENABLE_SEEDING) {
        for (double seed : SEEDED_VALUES) {
          RealVector s = vectorSpace.makeVector(p).set(wiggleVar, seed).build();
          double errorAtS = computeError(nonLinearPc, s);
          if (isSatisfied(linearPc, s) && errorAtS < minError) {
            minError = errorAtS;
            minNeighbor = s;
          }
        }
      }

      if (minError < errorAtP) {  // Alg. 1: lines 23--27
        printDebug(ConcolicWalkSolver.class, "Found new neighbor");
        p = minNeighbor;
        decrementElements(tabuVariables);
      } else {  // Alg 1: lines 27--29
        printDebug(ConcolicWalkSolver.class, "Could not find better neighbor");
        tabuVariables[wiggleVarIndex] = Math.max(Math.round(TABU_ITERATIONS_PER_VARIABLE * vectorSpace.dimensions().size()), MIN_TABU_ITERATIONS);
        printDebug(ConcolicWalkSolver.class, "Tabu ", Arrays.toString(tabuVariables));
      }
    }
    printDebug(ConcolicWalkSolver.class, "Found solution: ", p);
    vectorSpace.assignToVariables(p);
    return true;
  }

  /**
   * Returns a new point in the {@code polytope} that differs from the given
   * {@code point} in the given {@code variable} (= dimension), or {@code null}
   * if no such point could be found.
   * 
   * @see Algorithm 4 in the paper.
   */
  private static RealVector makeRandomNeighborInPolytope(RealVector point, PathCondition polytope, Object variable) {
    // Implement this as Dikin walk, see Kannan and Narayanan "Random Walks
    // on Polytopes and an Affine Interior Point Method for Linear Programing".
    RealVector.Builder deltaBuilder = point.space().makeVector();
    RealVector neighbor;

    final int e = 2 * RANDOMIZATION_RADIUS_EXPONENT;
    for (int i = 0; i < 25; ++i) {
      double g = random.nextDouble();
      int j = random.nextInt(e) - RANDOMIZATION_RADIUS_EXPONENT;
      double delta = (j > 0) ? g * (1 << j) : 1.0 / (g * (1 << -j));
      deltaBuilder.set(variable, delta);
      neighbor = plus(point, deltaBuilder.build());

      if (isSatisfied(polytope, neighbor)) {
        return neighbor;
      }
    }
    printDebug(ConcolicWalkSolver.class, "Failed to find random neighbor in polytope");
    return null;
  }

  /**
   * Returns a point {@code r} where the linear function {@code f} with
   * {@code f(p) = valueAtP} and {@code f(q) = valueAtQ} is zero, that is,
   * {@code f(r) = 0}.
   * 
   * <p> 
   * NOTE: The returned point does not necessarily lie within the polytope.
   * 
   * <p>
   * This is the BisectionStep function that appears in the paper.
   */
  static RealVector linearlyEstimateZero(RealVector p, Number valueAtP, RealVector q, Number valueAtQ, Comparator comparator) {
    double vP = valueAtP.doubleValue();
    double vQ = valueAtQ.doubleValue();
    double t0 = (vP != vQ) ? -vP / (vQ - vP) : random.nextGaussian();
    if (comparator != Comparator.EQ) {
      t0 *= (1.0 + (random.nextInt(250) * 0.01));
      if (t0 == 0) {
        t0 = random.nextGaussian();
      }
    }
    return plus(times(t0, minus(q, p)), p);
  }

  private final static String SYM_INT_SUFFIX = SymbolicInteger.SYM_INT_SUFFIX;
  private final static String SYM_REAL_SUFFIX = "_SYMREAL";

  /**
   * Returns a point in the {@code vectorSpace} that is described by the
   * solution values attached to the given set of {@code variables}.
   */
  private static RealVector makeVectorFromSolutions(RealVectorSpace vectorSpace, Set<Object> variables) {
    RealVector.Builder builder = vectorSpace.makeVector();
    if (variables.isEmpty()) {
      return builder.build();
    }

    Map<String, Object> varNamesToValues = new HashMap<>();
    for (Object var : variables) {
      if (var instanceof SymbolicInteger) {
        ((SymbolicInteger) var).getVarsVals(varNamesToValues);
      } else if (var instanceof SymbolicReal) {
        ((SymbolicReal) var).getVarsVals(varNamesToValues);
      } else {
        throw new AssertionError("Unknown variable type: " + var);
      }
    }

    for (Object var : variables) {
      String varName;
      if (var instanceof SymbolicInteger) {
        varName = ((SymbolicInteger) var).getName();
        if (varName.endsWith(SYM_INT_SUFFIX)) {
          varName = varName.substring(0, varName.lastIndexOf(SYM_INT_SUFFIX));
        }
      } else if (var instanceof SymbolicReal) {
        varName = ((SymbolicReal) var).getName();
        if (varName.endsWith(SYM_REAL_SUFFIX)) {
          varName = varName.substring(0, varName.lastIndexOf(SYM_REAL_SUFFIX));
        }
      } else {
        throw new AssertionError("Unknown variable type: " + var);
      }

      Number val = (Number) varNamesToValues.get(varName);
      if (val == null) {
        throw new AssertionError("Variable " + var + " has no attached solution");
      }
      builder.set(var, val.doubleValue());
    }
    return builder.build();
  }

  /**
   * Returns the cumulative error score for the constraints in the given path
   * condition at the given valuation point.
   * 
   * <p>
   * This is Algorithm 2 in the paper.
   */
  static double computeError(PathCondition pc, RealVector valuation) {
    double totalError = 0;
    for (Constraint c : eachConstraint(pc)) {
      totalError += computeError(c, valuation);
    }
    return totalError;
  }

  static double computeError(Constraint constraint, RealVector valuation) {
    return computeError(constraint.getComparator(), evaluateAndSubtractSides(constraint, valuation));
  }

  static double computeError(Comparator comparator, Number value) {
    return computeError(comparator, value.doubleValue());
  }

  static double computeError(Comparator comparator, double value) {
    if (Double.isNaN(value)) {
      return NAN_ERROR;
    }
    switch (comparator) {
    case EQ:
      return Math.abs(value);
    case GE:
      return value >= 0 ? 0 : -value + 1;
    case GT:
      return value > 0 ? 0 : -value + 1;
    case LE:
      return value <= 0 ? 0 : value + 1;
    case LT:
      return value < 0 ? 0 : value + 1;
    case NE:
      return value != 0 ? 0 : 1;
    }
    throw new AssertionError();
  }

  static void incrementElements(double[] values, BitSet selectedIndices, double increment) {
    for (int i = selectedIndices.nextSetBit(0); i >= 0; i = selectedIndices.nextSetBit(i+1)) {
      values[i] += increment;
    }
  }

  static void decrementElements(int[] values) {
    for (int i = 0; i < values.length; ++i) {
      if (values[i] > 0) {
        --values[i];
      }
    }
  }

  static int indexOfMaxIgnoringTabu(double[] values, int[] tabuIndices) {
    double currentMax = Double.NEGATIVE_INFINITY;
    int currentMaxIndex = -1;

    for (int i = 0; i < values.length; ++i) {
      if (tabuIndices[i] > 0) {
        continue;
      }
      if (currentMax < values[i]) {
        currentMaxIndex = i;
        currentMax = values[i];
      }
    }
    return currentMaxIndex;
  }

  static void populateLookupTables(RealVectorSpace space, PathCondition pc, List<Constraint>[] constraintsByVariableIndex, Map<Constraint, BitSet> variableIndicesByConstraint) {
    int numberOfVariables = space.dimensions().size();
    for (Constraint constraint : eachConstraint(pc)) {
      BitSet variableIndices = new BitSet(numberOfVariables);
      variableIndicesByConstraint.put(constraint, variableIndices);
      for (Object variable : variablesIn(constraint)) {
        int varIndex = space.indexOf(variable);
        variableIndices.set(varIndex);
        if (constraintsByVariableIndex[varIndex] == null) {
          constraintsByVariableIndex[varIndex] = new ArrayList<>();
        }
        constraintsByVariableIndex[varIndex].add(constraint);
      }
    }
  }
}
