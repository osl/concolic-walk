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
import static gov.nasa.jpf.symbc.concolic.walk.Util.printDebug;
import gov.nasa.jpf.symbc.concolic.FunctionExpression;
import gov.nasa.jpf.symbc.concolic.IntegerFunctionExpression;
import gov.nasa.jpf.symbc.numeric.BinaryLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.BinaryNonLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.BinaryRealExpression;
import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.IntegerExpression;
import gov.nasa.jpf.symbc.numeric.MathFunction;
import gov.nasa.jpf.symbc.numeric.MathRealExpression;
import gov.nasa.jpf.symbc.numeric.Operator;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Interpreter that computes the value of an {@link Expression} at a given
 * {@link RealVector valuation point}.
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
class ExpressionEvaluator extends ConstraintExpressionVisitor {
  private final Deque<Number> operandStack;
  private final RealVector valuation;

  public ExpressionEvaluator(RealVector valuation) {
    this.operandStack = new ArrayDeque<>();
    this.valuation = valuation;
  }

  public Number top() {
    return operandStack.getFirst();
  }

  @Override
  public void postVisit(BinaryLinearIntegerExpression expr) {
    applyBinaryIntOp(expr.getOp());
  }

  @Override
  public void postVisit(BinaryNonLinearIntegerExpression expr) {
    applyBinaryIntOp(expr.op);  // expr lacks a getOp() method
  }

  @Override
  public void postVisit(IntegerExpression expr) {
    if (expr instanceof IntegerFunctionExpression) {
      try {
        Method m = ((IntegerFunctionExpression) expr).getMethod();
        applyStaticMethod(m);
      } catch (NoSuchMethodException e) {
        printDebug(ExpressionEvaluator.class, "NoSuchMethodException " + expr);
      }
    }
  }

  @Override
  public void postVisit(BinaryRealExpression expr) {
    applyBinaryDoubleOp(expr.getOp());
  }

  @Override
  public void postVisit(FunctionExpression expr) {
    try {
      Method m = expr.getMethod();
      applyStaticMethod(m);
    } catch (NoSuchMethodException e) {
      printDebug(ExpressionEvaluator.class, "NoSuchMethodException " + expr);
    }
  }

  @Override
  public void postVisit(MathRealExpression expr) {
    applyRealMathFunction(expr.getOp());
  }

  @Override
  public void postVisit(SymbolicInteger expr) {
    operandStack.push((int) Math.round(valuation.get(expr)));
  }

  @Override
  public void postVisit(SymbolicReal expr) {
    operandStack.push(valuation.get(expr));
  }

  @Override
  public void postVisit(IntegerConstant expr) {
    operandStack.push(expr.value());
  }

  @Override
  public void postVisit(RealConstant expr) {
    operandStack.push(expr.value());
  }

  private void applyStaticMethod(Method method) {
    int modifiers = method.getModifiers();
    if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
      throw new IllegalArgumentException("Could not invoke " + method);
    }
    Object result = Integer.valueOf(0);
    try {
      // Pop the method arguments from the stack and invoke the method via reflection.
      Object[] args = new Object[method.getParameterTypes().length];
      for (int i = args.length - 1; i >= 0; --i) {
        args[i] = operandStack.pop();
      }
      result = method.invoke(null, args);
    } catch (IllegalAccessException e) {
      printDebug(ExpressionEvaluator.class, "IllegalAccessException " + method);
    } catch (InvocationTargetException e) {
      printDebug(ExpressionEvaluator.class, "InvocationTargetException " + method);
    } catch (SecurityException e) {
      printDebug(ExpressionEvaluator.class, "SecurityException " + method);
    }
    if (result instanceof Number) {
      operandStack.push((Number) result);
    } else {
      operandStack.push(0);
      printDebug(ExpressionEvaluator.class, "Non-Number return type " + method);
    }
  }

  public void applyBinaryIntOp(Operator operator) {
    int operand2 = (int) Math.round(operandStack.pop().doubleValue());
    int operand1 = (int) Math.round(operandStack.pop().doubleValue());
    int result;
    switch (operator) {
    case AND:
      result = operand1 & operand2;
      break;
    case CMP:
      result = (operand1 < operand2) ? -1 : (operand1 == operand2 ? 0 : 1);
      break;
    case DIV:
      result = operand1 / operand2;
      break;
    case MINUS:
      result = operand1 - operand2;
      break;
    case MUL:
      result = operand1 * operand2;
      break;
    case OR:
      result = operand1 | operand2;
      break;
    case PLUS:
      result = operand1 + operand2;
      break;
    case SHIFTL:
      result = operand1 << operand2;
      break;
    case SHIFTR:
      result = operand1 >> operand2;
      break;
    case SHIFTUR:
      result = operand1 >>> operand2;
      break;
    case XOR:
      result = operand1 ^ operand2;
      break;
    default:
      throw new AssertionError("Unsupported int operator: " + operator);
    }
    operandStack.push(result);
  }

  public void applyBinaryDoubleOp(Operator operator) {
    double operand2 = operandStack.pop().doubleValue();
    double operand1 = operandStack.pop().doubleValue();
    double result;
    switch (operator) {
    case CMP:
      // TODO: Handle NaN
      result = (operand1 < operand2) ? -1 : (operand1 == operand2 ? 0 : 1);
      break;
    case DIV:
      result = operand1 / operand2;
      break;
    case MINUS:
      result = operand1 - operand2;
      break;
    case MUL:
      result = operand1 * operand2;
      break;
    case PLUS:
      result = operand1 + operand2;
      break;
    default:
      throw new AssertionError("Unsupported double operator: " + operator);
    }
    operandStack.push(result);
  }

  public void applyRealMathFunction(MathFunction func) {
    double operand2 = 0.0;
    if (func == MathFunction.POW || func == MathFunction.ATAN2) {
      operand2 = operandStack.pop().doubleValue();
    }
    double operand1 = operandStack.pop().doubleValue();
    double result;

    switch (func) {
    case ACOS:
      result = Math.acos(operand1);
      break;
    case ASIN:
      result = Math.asin(operand1);
      break;
    case ATAN:
      result = Math.atan(operand1);
      break;
    case ATAN2:
      result = Math.atan2(operand1, operand2);
      break;
    case COS:
      result = Math.cos(operand1);
      break;
    case EXP:
      result = Math.exp(operand1);
      break;
    case LOG:
      result = Math.log(operand1);
      break;
    case POW:
      result = Math.pow(operand1, operand2);
      break;
    case SIN:
      result = Math.sin(operand1);
      break;
    case SQRT:
      result = Math.sqrt(operand1);
      break;
    case TAN:
      result = Math.tan(operand1);
      break;
    default:
      throw new AssertionError("Unsupported double function: " + func);
    }
    operandStack.push(result);
  }

  @Override
  public String toString() {
    return defaultStringRepresentation(this, operandStack);
  }
}
