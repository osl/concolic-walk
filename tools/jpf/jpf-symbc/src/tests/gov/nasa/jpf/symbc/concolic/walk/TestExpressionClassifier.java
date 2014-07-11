package gov.nasa.jpf.symbc.concolic.walk;

import static gov.nasa.jpf.symbc.concolic.walk.ExpressionClassifier.expressionType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.symbc.concolic.walk.ExpressionClassifier.ExpressionType;
import gov.nasa.jpf.symbc.numeric.Expression;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.MathFunction;
import gov.nasa.jpf.symbc.numeric.MathRealExpression;
import gov.nasa.jpf.symbc.numeric.MinMax;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.RealExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExpressionClassifier {

  private SymbolicInteger a;
  private SymbolicInteger b;
  private IntegerConstant zero;

  private SymbolicReal x;
  private SymbolicReal y;
  private RealConstant pi;

  @BeforeClass
  public static void initializeGlobalState() {
    Config defaultConfig = new Config(new String[0]);
    MinMax.collectMinMaxInformation(defaultConfig);
  }

  @Before
  public void setUp() {
    this.a = new SymbolicInteger("a");
    this.b = new SymbolicInteger("b");
    this.zero = new IntegerConstant(0);

    this.x = new SymbolicReal("x");
    this.y = new SymbolicReal("y");
    this.pi = new RealConstant(3.14159);
  }

  // Constants

  @Test
  public void integerConstantShouldBeConstant() {
    assertThat(expressionType(zero), isConstant());
  }

  @Test
  public void realConstantShouldBeConstant() {
    assertThat(expressionType(pi), isConstant());
  }

  @Test
  public void sumOfConstantsShouldBeConstant() {
    assertThat(expressionType(pi._plus(2.89)), isConstant());
  }

  @Test
  public void productOfConstantsShouldBeConstant() {
    assertThat(expressionType(pi._mul(1.41)), isConstant());
  }

  // Variables

  @Test
  public void integerVariableShouldBeLinear() {
    assertThat(expressionType(a), isLinear());
  }

  @Test
  public void realVariableShouldBeLinear() {
    assertThat(expressionType(x), isLinear());
  }

  // Basic Operations

  @Test
  public void scaledIntegerVariableShouldBeLinear() {
    assertThat(expressionType(a._mul(4)), isLinear());
  }

  @Test
  public void scaledRealVariableShouldBeLinear() {
    assertThat(expressionType(x._mul(2.17)), isLinear());
  }

  @Test
  public void integerSumShouldBeLinear() {
    assertThat(expressionType(a._plus(b)), isLinear());
  }

  @Test
  public void realSumShouldBeLinear() {
    assertThat(expressionType(x._plus(y)), isLinear());
  }

  @Test
  public void realDifferenceShouldBeLinear() {
    assertThat(expressionType(x._minus(y)), isLinear());
  }

  @Test
  public void scaledRealSumShouldBeLinear() {
    assertThat(expressionType(x._plus(y)._mul(2.17)), isLinear());
  }

  @Test
  public void integerProductShouldBeNonLinear() {
    assertThat(expressionType(a._mul(b)), isNonLinear());
  }

  @Test
  public void realProductShouldBeNonLinear() {
    assertThat(expressionType(x._mul(y)), isNonLinear());
  }

  @Test
  public void realQuotientShouldBeNonLinear() {
    assertThat(expressionType(x._div(y)), isNonLinear());
  }

  @Test
  public void quadraticRealTermShouldBeNonLinear() {
    assertThat(expressionType(pi._div(x)._plus(y)), isNonLinear());
  }

  // MathRealExpressions

  @Test
  public void realFunctionsOfConstantsShouldBeConstant() {
    assertThat(expressionType(cos(pi)), isConstant());
  }

  @Test
  public void realFunctionsShouldBeNonLinear() {
    assertThat(expressionType(cos(x)), isNonLinear());
  }

  // Auxiliary Testing Methods

  private static Expression cos(RealExpression expr) {
    return new MathRealExpression(MathFunction.COS, expr);
  }

  private static Matcher<ExpressionType> isConstant() {
    return equalTo(ExpressionType.CONSTANT);
  }

  private static Matcher<ExpressionType> isLinear() {
    return equalTo(ExpressionType.LINEAR);
  }

  private static Matcher<ExpressionType> isNonLinear() {
    return equalTo(ExpressionType.NONLINEAR);
  }
}
