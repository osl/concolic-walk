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

import static gov.nasa.jpf.symbc.concolic.walk.ExpressionClassifier.expressionType;
import static gov.nasa.jpf.symbc.concolic.walk.Util.defaultStringRepresentation;
import static gov.nasa.jpf.symbc.concolic.walk.Util.preVisitAllAfterCast;
import gov.nasa.jpf.symbc.concolic.walk.ExpressionClassifier.ExpressionType;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.LinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.LogicalORLinearIntegerConstraints;
import gov.nasa.jpf.symbc.numeric.MixedConstraint;
import gov.nasa.jpf.symbc.numeric.NonLinearIntegerConstraint;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.RealConstraint;

/**
 * Constraint visitor that separates the linear {@link Constraint}s in a
 * {@link PathCondition} from the non-linear constraints.
 * 
 * Instead of using the visitor directly, use the
 * {@link #splitInto(PathCondition, PathCondition, PathCondition)} method to
 * separate the constraints in a PathCondition.
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
class ConstraintSplitter extends ConstraintExpressionVisitor {
  private final PathCondition linearConstraints;
  private final PathCondition nonLinearConstraints;

  private ConstraintSplitter(PathCondition linearConstraints, PathCondition nonLinearConstraints) {
    this.linearConstraints = linearConstraints;
    this.nonLinearConstraints = nonLinearConstraints;
  }

  @Override
  public void preVisit(Constraint constraint) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void preVisit(LogicalORLinearIntegerConstraints constraint) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void preVisit(MixedConstraint constraint) {
    prepend(new MixedConstraint(constraint), pathConditionFor(constraint));
  }

  @Override
  public void preVisit(LinearIntegerConstraint constraint) {
    prepend(new LinearIntegerConstraint(constraint), pathConditionFor(constraint));
  }

  @Override
  public void preVisit(NonLinearIntegerConstraint constraint) {
    if (isLinear(constraint)) {
      prepend(new LinearIntegerConstraint(constraint.getLeft(), constraint.getComparator(), constraint.getRight()), linearConstraints);
    } else {
      prepend(new NonLinearIntegerConstraint(constraint), nonLinearConstraints);
    }
  }

  @Override
  public void preVisit(RealConstraint constraint) {
    prepend(new RealConstraint(constraint), pathConditionFor(constraint));
  }

  private PathCondition pathConditionFor(Constraint constraint) {
    return isLinear(constraint) ? linearConstraints : nonLinearConstraints;
  }

  private boolean isLinear(Constraint constraint) {
    ExpressionType leftType = expressionType(constraint.getLeft());
    if (leftType == ExpressionType.NONLINEAR) {
      return false;
    }
    ExpressionType rightType = expressionType(constraint.getRight());
    if (rightType == ExpressionType.NONLINEAR) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return defaultStringRepresentation(this, linearConstraints, nonLinearConstraints);
  }

  private static void prepend(Constraint constraint, PathCondition pc) {
    constraint.and = pc.header;
    pc.header = constraint;
  }

  public static void splitInto(PathCondition source, PathCondition linearConstraints, PathCondition nonLinearConstraints) {
    preVisitAllAfterCast(new ConstraintSplitter(linearConstraints, nonLinearConstraints), source);
    linearConstraints.recomputeCount();
    nonLinearConstraints.recomputeCount();
  }
}
