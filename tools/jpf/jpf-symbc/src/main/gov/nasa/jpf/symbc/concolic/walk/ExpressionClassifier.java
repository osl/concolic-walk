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

import static gov.nasa.jpf.symbc.concolic.walk.Util.defaultStringRepresentation;
import gov.nasa.jpf.symbc.concolic.FunctionExpression;
import gov.nasa.jpf.symbc.numeric.BinaryLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.BinaryNonLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.BinaryRealExpression;
import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.MathFunction;
import gov.nasa.jpf.symbc.numeric.MathRealExpression;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Visitor that determines whether an {@link Expression} is
 * {@link ExpressionType constant, linear, or non-linear}.
 * 
 * Instead of using the visitor directly, use the static
 * {@link #expressionType(Expression)} method to classify an Expression.
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
class ExpressionClassifier extends ConstraintExpressionVisitor {

  public static enum ExpressionType { CONSTANT, LINEAR, NONLINEAR }
  // This is ugly, but we cannot import the enum identifiers to avoid
  // the {@code ExpressionType.} prefix.
  private final static ExpressionType CONSTANT = ExpressionType.CONSTANT;
  private final static ExpressionType LINEAR = ExpressionType.LINEAR;
  private final static ExpressionType NONLINEAR = ExpressionType.NONLINEAR;

  private final Deque<ExpressionType> expressionTypeStack;

  private ExpressionClassifier() {
    this.expressionTypeStack = new ArrayDeque<>();
  }

  public ExpressionType expressionType() {
    return expressionTypeStack.getFirst();
  }

  void reset() {
    expressionTypeStack.clear();
  }

  @Override
  public void postVisit(BinaryLinearIntegerExpression expr) {
    expressionTypeStack.pop();
    expressionTypeStack.pop();
    expressionTypeStack.push(LINEAR);
  }

  @Override
  public void postVisit(BinaryNonLinearIntegerExpression expr) {
    expressionTypeStack.pop();
    expressionTypeStack.pop();
    expressionTypeStack.push(NONLINEAR);
  }

  @Override
  public void postVisit(BinaryRealExpression expr) {
    ExpressionType rightType = expressionTypeStack.pop();
    ExpressionType leftType = expressionTypeStack.pop();

    if (leftType == NONLINEAR || rightType == NONLINEAR) {
      expressionTypeStack.push(NONLINEAR);
      return;
    }
    if (leftType == CONSTANT && rightType == CONSTANT) {
      expressionTypeStack.push(CONSTANT);
      return;
    }

    switch (expr.getOp()) {
    case PLUS:
    case MINUS:
      expressionTypeStack.push(LINEAR);
      break;
    case MUL:
    case DIV:
      if (rightType == CONSTANT) {
        expressionTypeStack.push(LINEAR);
      } else {
        expressionTypeStack.push(NONLINEAR);
      }
      break;
    case AND:
    case OR:
    case SHIFTL:
    case SHIFTR:
    case SHIFTUR:
    case XOR:
      throw new AssertionError("Encountered bit-operation on doubles: " + expr);
    case CMP:
      expressionTypeStack.push(NONLINEAR);
    default:
      throw new AssertionError("Unsupported double operator " + expr.getOp() + " in " + expr);
    }
  }

  @Override
  public void postVisit(MathRealExpression expr) {
    ExpressionType type1 = expressionTypeStack.pop();
    ExpressionType type2 = null;
    if (expr.getOp() == MathFunction.ATAN2 || expr.getOp() == MathFunction.POW) {
      type2 = expressionTypeStack.pop();  // The only functions that take two arguments
    }
    if (type1 == CONSTANT && (type2 == null || type2 == CONSTANT)) {
      expressionTypeStack.push(CONSTANT);
      return;
    }
    expressionTypeStack.push(NONLINEAR);
  }

  @Override
  public void postVisit(FunctionExpression expr) {
    throw new UnsupportedOperationException(expr.toString());
  }

  @Override
  public void postVisit(SymbolicInteger expr) {
    expressionTypeStack.push(LINEAR);
  }

  @Override
  public void postVisit(SymbolicReal expr) {
    expressionTypeStack.push(LINEAR);
  }

  @Override
  public void postVisit(IntegerConstant expr) {
    expressionTypeStack.push(CONSTANT);
  }

  @Override
  public void postVisit(RealConstant expr) {
    expressionTypeStack.push(CONSTANT);
  }

  @Override
  public String toString() {
    return defaultStringRepresentation(this, expressionTypeStack);
  }

  private final static ExpressionClassifier INSTANCE = new ExpressionClassifier();

  public static ExpressionType expressionType(Expression expr) {
    INSTANCE.reset();
    expr.accept(INSTANCE);
    return INSTANCE.expressionType();
  }
}
