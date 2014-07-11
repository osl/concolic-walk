package gov.nasa.jpf.symbc.concolic.walk;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.symbc.concolic.walk.ExpressionEvaluator;
import gov.nasa.jpf.symbc.concolic.walk.RealVector;
import gov.nasa.jpf.symbc.concolic.walk.RealVectorSpace;
import gov.nasa.jpf.symbc.numeric.BinaryRealExpression;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.MathFunction;
import gov.nasa.jpf.symbc.numeric.MathRealExpression;
import gov.nasa.jpf.symbc.numeric.MinMax;
import gov.nasa.jpf.symbc.numeric.Operator;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExpressionEvaluator {

  private SymbolicInteger a;
  private SymbolicInteger b;
  private SymbolicReal x;
  private SymbolicReal y;
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
    this.x = new SymbolicReal("x");
    this.y = new SymbolicReal("y");
    this.space = RealVectorSpace.forDimensions(a, b, x, y);
  }

  // Variables and Constants

  @Test
  public void evaluatorShouldUseIntegerConstants() {
    RealVector p = space.makeVector().build();
    assertThat(evaluate(new IntegerConstant(23), p).intValue(), equalTo(23));
  }

  @Test
  public void evaluatorShouldUseRealConstants() {
    RealVector p = space.makeVector().build();
    assertThat(evaluate(new RealConstant(3.14), p).doubleValue(), equalTo(3.14));
  }

  @Test
  public void evaluatorShouldUseAssignmentForIntegerVariables() {
    RealVector p = space.makeVector().set(a, 42).build();
    assertThat(evaluate(a, p).intValue(), equalTo(42));
  }

  @Test
  public void evaluatorShouldUseAssignmentForRealVariables() {
    RealVector p = space.makeVector().set(x, 2.89).build();
    assertThat(evaluate(x, p).doubleValue(), equalTo(2.89));
  }

  // Integer Expressions

  @Test
  public void addIntegers() {
    Number actual = evaluate(a._plus(b), intPoint(18, 5));
    assertThat(actual.intValue(), equalTo(23));
  }

  @Test
  public void subtractIntegers() {
    Number actual = evaluate(a._minus(b), intPoint(23, 65));
    assertThat(actual.intValue(), equalTo(-42));
  }

  @Test
  public void negIntegers() {
    Number actual = evaluate(a._neg(), intPoint(23, 0));
    assertThat(actual.intValue(), equalTo(-23));
  }

  @Test
  public void multiplyIntegers() {
    Number actual = evaluate(a._mul(b), intPoint(6, 7));
    assertThat(actual.intValue(), equalTo(42));
  }

  @Test
  public void divideIntegers() {
    Number actual = evaluate(a._div(b), intPoint(23, 2));
    assertThat(actual.intValue(), equalTo(11));  // Integer arithmetic drops the 0.5
  }

  @Test
  public void shiftIntegersLeft() {
    Number actual = evaluate(a._shiftL(b), intPoint(8, 2));
    assertThat(actual.intValue(), equalTo(32));
  }

  @Test
  public void shiftIntegersRight() {
    Number actual = evaluate(a._shiftR(b), intPoint(-1, 5));
    assertThat(actual.intValue(), equalTo(-1));
  }

  @Test
  public void shiftIntegersLogicallyRight() {
    Number actual = evaluate(a._shiftUR(b), intPoint(-1, 1));
    assertThat(actual.intValue(), equalTo(Integer.MAX_VALUE));
  }

  @Test
  public void andIntegers() {
    Number actual = evaluate(a._and(b), intPoint(17, 5));
    assertThat(actual.intValue(), equalTo(1));
  }

  @Test
  public void integerPolynomial() {
    // a**3 + a*b + 7
    Number actual = evaluate(a._mul(a)._mul(a)._plus(a._mul(b))._plus(7), intPoint(3, -8));
    assertThat(actual.intValue(), equalTo(10));
  }

  // Real Expressions

  @Test
  public void addReals() {
    Number actual = evaluate(x._plus(y), realPoint(3.0, 0.14));
    assertThat(actual.doubleValue(), equalTo(3.14));
  }

  @Test
  public void subtractReals() {
    Number actual = evaluate(x._minus(y), realPoint(2.89, 3.14));
    assertThat(actual.doubleValue(), equalTo(-0.25));
  }

  @Test
  public void negReals() {
    Number actual = evaluate(x._neg(), realPoint(3.14, 0));
    assertThat(actual.doubleValue(), equalTo(-3.14));
  }

  @Test
  public void multiplyReals() {
    Number actual = evaluate(x._mul(y), realPoint(4.2, 0.1));
    assertThat(actual.doubleValue(), equalTo(0.42000000000000004));
  }

  @Test
  public void divideReals() {
    Number actual = evaluate(x._div(y), realPoint(4.2, 0.7));
    assertThat(actual.doubleValue(), equalTo(6.000000000000001));
  }

  @Test
  public void cmpReals() {
    Number actual = evaluate(new BinaryRealExpression(x, Operator.CMP, y), realPoint(2.89, 3.14));
    assertThat(actual.intValue(), equalTo(-1));
  }

  @Test
  public void realSine() {
    Number actual = evaluate(new MathRealExpression(MathFunction.SIN, x), realPoint(3.14159, 0.0));
    assertTrue(0 < actual.doubleValue() && actual.doubleValue() < 0.0001);
  }

  @Test
  public void realPow() {
    Number actual = evaluate(new MathRealExpression(MathFunction.POW, x, y), realPoint(2.0, 3.0));
    assertThat(actual.doubleValue(), equalTo(8.0));
  }

  // Auxiliary Testing Methods

  private RealVector intPoint(int aVal, int bVal) {
    return space.makeVector().set(a, aVal).set(b, bVal).build();
  }

  private RealVector realPoint(double xVal, double yVal) {
    return space.makeVector().set(x, xVal).set(y, yVal).build();
  }

  private static Number evaluate(Expression expr, RealVector point) {
    ExpressionEvaluator evaluator = new ExpressionEvaluator(point);
    expr.accept(evaluator);
    return evaluator.top();
  }
}
