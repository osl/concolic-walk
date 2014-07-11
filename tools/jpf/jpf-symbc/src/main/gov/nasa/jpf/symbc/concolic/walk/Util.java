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
import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.LinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.LogicalORLinearIntegerConstraints;
import gov.nasa.jpf.symbc.numeric.MixedConstraint;
import gov.nasa.jpf.symbc.numeric.NonLinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.Operator;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.RealConstraint;
import gov.nasa.jpf.symbc.numeric.visitors.CollectVariableVisitor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class Util {

  public static void preVisitAllAfterCast(ConstraintExpressionVisitor visitor, PathCondition pc) {
    for (Constraint c : eachConstraint(pc)) {
      preVisitAfterCast(visitor, c);
    }
  }

  public static void preVisitAfterCast(ConstraintExpressionVisitor visitor, Constraint constraint) {
    // Java dispatches overloaded methods using static types, so calling
    // {@code constraint.accept(visitor)} always means
    // {@code visitor.preVisit(Constraint)}.
    if (constraint instanceof LinearIntegerConstraint) {
      visitor.preVisit((LinearIntegerConstraint) constraint);
    } else if (constraint instanceof LogicalORLinearIntegerConstraints) {
      visitor.preVisit((LogicalORLinearIntegerConstraints) constraint);
    } else if (constraint instanceof MixedConstraint) {
      visitor.preVisit((MixedConstraint) constraint);
    } else if (constraint instanceof NonLinearIntegerConstraint) {
      visitor.preVisit((NonLinearIntegerConstraint) constraint);
    } else if (constraint instanceof RealConstraint) {
      visitor.preVisit((RealConstraint) constraint);
    } else {
      throw new AssertionError("Constraint of unknown: " + constraint);
    }
  }

  public static Set<Object> variablesIn(PathCondition pc) {
    if (isEmpty(pc)) {
      return Collections.emptySet();
    }
    CollectVariableVisitor cvv = new CollectVariableVisitor();
    for (Constraint constraint : eachConstraint(pc)) {
      constraint.accept(cvv);
    }
    return cvv.getVariables();
  }

  public static Set<Object> variablesIn(Constraint constraint) {
    if (constraint == null) {
      return Collections.emptySet();
    }
    CollectVariableVisitor cvv = new CollectVariableVisitor();
    constraint.accept(cvv);
    return cvv.getVariables();
  }

  public static Set<Object> variablesIn(Expression expression) {
    if (expression == null || expression instanceof IntegerConstant || expression instanceof RealConstant) {
      return Collections.emptySet();
    }
    CollectVariableVisitor cvv = new CollectVariableVisitor();
    expression.accept(cvv);
    return cvv.getVariables();
  }

  // Constraint Evaluation

  public static Number evaluateAndSubtractSides(Constraint constraint, RealVector valuation) {
    ExpressionEvaluator evaluator = new ExpressionEvaluator(valuation);

    constraint.getLeft().accept(evaluator);
    boolean leftIsNegative = evaluator.top().doubleValue() < 0;
    constraint.getRight().accept(evaluator);
    boolean rightIsNegative = evaluator.top().doubleValue() < 0;

    evaluator.applyBinaryDoubleOp(Operator.MINUS);
    Number result = evaluator.top();
    if (leftIsNegative == rightIsNegative) {
      // Can neither overflow nor underflow
      return result;
    }

    boolean resultIsNegative = result.doubleValue() < 0;
    boolean underflowOccurred = leftIsNegative && !resultIsNegative;
    boolean overflowOccurred = rightIsNegative && resultIsNegative;
    if (!underflowOccurred && !overflowOccurred) {
      return result;
    }

    if (result instanceof Integer) {
      return (underflowOccurred ? Integer.MIN_VALUE : Integer.MAX_VALUE);
    } else if (result instanceof Double) {
      return (underflowOccurred ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
    } else {
      throw new AssertionError("Result has unknown number type: " + result);
    }
  }

  // Constraint Satisfaction

  public static boolean isSatisfied(PathCondition pc, RealVector valuation) {
    for (Constraint c : eachConstraint(pc)) {
      if (!isSatisfied(c, valuation)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isSatisfied(Constraint constraint, RealVector valuation) {
    double value = evaluateAndSubtractSides(constraint, valuation).doubleValue();
    return isSatisfied(constraint.getComparator(), value);
  }

  public static boolean isSatisfied(Comparator comparator, Number value) {
    return isSatisfied(comparator, value.doubleValue());
  }

  public static boolean isSatisfied(Comparator comparator, double value) {
    switch (comparator) {
    case EQ:
      return value == 0.0;
    case GE:
      return value >= 0.0;
    case GT:
      return value > 0.0;
    case LE:
      return value <= 0.0;
    case LT:
      return value < 0.0;
    case NE:
      return value != 0.0;
    default:
      throw new AssertionError("Unexpected comparator in " + comparator);
    }
  }

  public static boolean isEmpty(PathCondition pc) {
    return pc.header == null;
  }

  // Sets

  public static <T> Set<T> union(Collection<T> collection1, Collection<T> collection2) {
    Set<T> result = new HashSet<>();
    result.addAll(collection1);
    result.addAll(collection2);
    return result;
  }

  // Debugging

  // The overloaded methods help to avoid generating a costly string representation
  // before we find out whether we actually need it.
  
  public static void printDebug(Class<?> source, Object message) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    printDebug(source, message.toString());
  }

  public static void printDebug(Class<?> source, Object message1, Object message2) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    printDebug(source, message1.toString() + message2);
  }

  public static void printDebug(Class<?> source, Object message1, Object message2, Object message3) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    printDebug(source, message1.toString() + message2 + message3);
  }

  public static void printDebug(Class<?> source, Object message1, Object message2, Object message3, Object message4) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    printDebug(source, message1.toString() + message2 + message3 + message4);
  }


  public static void printDebug(Class<?> source, Object message1, Object message2, Object message3, Object message4, Object... messages) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    StringBuffer sb = new StringBuffer();
    sb.append(message1).append(message2).append(message3).append(message4);
    for (Object m : messages) {
      sb.append(m);
    }
    printDebug(source, sb.toString());
  }

  public static void printDebug(Object source, String message) {
    printDebug(source.getClass(), message);
  }

  public static void printDebug(Class<?> klass, String message) {
    if (!SymbolicInstructionFactory.debugMode) {
      return;
    }
    System.out.println("[CW] - " + klass.getSimpleName() + ": " + message);
  }

  public static String defaultStringRepresentation(Object this_, Object... fieldValues) {
    StringBuilder sb = new StringBuilder(this_.getClass().getSimpleName());
    sb.append("{");
    for (int i = 0; i < fieldValues.length; ++i) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(fieldValues[i]);
    }
    sb.append("}");
    return sb.toString();
  }


  private Util() {
    throw new AssertionError("not constructible");
  }
}
