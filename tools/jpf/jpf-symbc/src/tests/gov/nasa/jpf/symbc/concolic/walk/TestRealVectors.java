package gov.nasa.jpf.symbc.concolic.walk;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.symbc.concolic.walk.RealVector;
import gov.nasa.jpf.symbc.concolic.walk.RealVectorSpace;
import gov.nasa.jpf.symbc.numeric.MinMax;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRealVectors {

  private SymbolicInteger x;
  private SymbolicReal y;
  private Set<Object> dimensions;
  private RealVectorSpace space;

  @BeforeClass
  public static void initializeGlobalState() {
    Config defaultConfig = new Config(new String[0]);
    MinMax.collectMinMaxInformation(defaultConfig);
  }

  @Before
  public void setUp() {
    this.x = new SymbolicInteger("x");
    this.y = new SymbolicReal("y");
    dimensions = new HashSet<>();
    dimensions.add(x);
    dimensions.add(y);
    space = RealVectorSpace.forDimensions(dimensions);
  }

  // Building and Value Access

  @Test
  public void emptyVectorShouldBeZero() {
    RealVector zero = vector().build();
    assertThat(zero.get(x), equalTo(0.0));
    assertThat(zero.get(y), equalTo(0.0));
  }

  @Test
  public void settingValuesShouldHaveEffect() {
    RealVector v = vector().set(x, 3.14).set(y, 2.89).build();
    assertThat(v.get(x), equalTo(3.14));
    assertThat(v.get(y), equalTo(2.89));
  }

  @Test
  public void builtVectorsShouldNotShareValues() {
    RealVector.Builder builder = vector();
    builder.set(x, 3.14);
    RealVector v1 = builder.build();
    builder.set(x, 2.89);
    RealVector v2 = builder.build();
    assertThat(v1.get(x), equalTo(3.14));
    assertThat(v2.get(x), equalTo(2.89));
  }

  @Test(expected=IllegalArgumentException.class)
  public void accessingNullDimensionShouldThrow() {
    vector().build().get(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void accessingUnknownDimensionsShouldThrow() {
    vector().build().get(new SymbolicInteger("z"));
  }

  // Equality

  @Test
  public void equalVectorsShouldBeEqual() {
    RealVector v1 = vector().set(x, 1.0).set(y, -23.42).build();
    RealVector v2 = vector().set(y, -23.42).set(x, 1.0).build();
    assertThat(v1, equalTo(v2));
  }

  @Test
  public void equalVectorsShouldHaveSameHash() {
    RealVector v1 = vector().set(x, 1.0).set(y, -23.42).build();
    RealVector v2 = vector().set(y, -23.42).set(x, 1.0).build();
    assertThat(v1.hashCode(), equalTo(v2.hashCode()));
  }

  @Test
  public void spaceShouldDistinguishVectors() {
    RealVector v1 = RealVectorSpace.forDimensions(dimensions).makeVector().build();
    RealVector v2 = RealVectorSpace.forDimensions(dimensions).makeVector().build();
    assertThat(v1, not(equalTo(v2)));
  }

  @Test
  public void valuesShouldDistinguishVectors() {
    RealVector v1 = vector().set(x, 1.0).build();
    RealVector v2 = vector().set(x, -1.0).build();
    assertThat(v1, not(equalTo(v2)));
  }

  @Test
  public void spaceShouldInfluenceHashCode() {
    RealVector v1 = RealVectorSpace.forDimensions(dimensions).makeVector().build();
    RealVector v2 = RealVectorSpace.forDimensions(dimensions).makeVector().build();
    assertThat(v1.hashCode(), not(equalTo(v2.hashCode())));
  }

  @Test
  public void valuesShouldInfluenceHashCode() {
    RealVector v1 = vector().set(x, 1.0).build();
    RealVector v2 = vector().set(x, -1.0).build();
    assertThat(v1.hashCode(), not(equalTo(v2.hashCode())));
  }

  // Operations

  @Test
  public void addingVectorsShouldYieldSum() {
    RealVector v1 = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector v2 = vector().set(x, 0.14).set(y, 0.89).build();
    RealVector v3 = RealVector.plus(v1, v2);
    assertThat(v3.get(x), equalTo(3.14));
    assertThat(v3.get(y), equalTo(2.89));
  }

  @Test
  public void addingVectorsShouldNotInfluenceOperands() {
    RealVector v1 = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector v2 = vector().set(x, 0.14).set(y, 0.89).build();
    RealVector.plus(v1, v2);

    assertThat(v1.get(x), equalTo(3.0));
    assertThat(v1.get(y), equalTo(2.0));
    assertThat(v2.get(x), equalTo(0.14));
    assertThat(v2.get(y), equalTo(0.89));
  }

  @Test
  public void subtractingVectorsShouldYieldDifference() {
    RealVector v1 = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector v2 = vector().set(x, 0.14).set(y, 0.88).build();
    RealVector v3 = RealVector.minus(v1, v2);
    assertThat(v3.get(x), equalTo(2.86));
    assertThat(v3.get(y), equalTo(1.12));
  }

  @Test
  public void subtractingVectorsShouldNotInfluenceOperands() {
    RealVector v1 = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector v2 = vector().set(x, 0.14).set(y, 0.89).build();
    RealVector.minus(v1, v2);

    assertThat(v1.get(x), equalTo(3.0));
    assertThat(v1.get(y), equalTo(2.0));
    assertThat(v2.get(x), equalTo(0.14));
    assertThat(v2.get(y), equalTo(0.89));
  }

  @Test
  public void scalingVectorShouldYieldScaledVector() {
    RealVector v1 = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector v2 = RealVector.times(1.5, v1);
    assertThat(v2.get(x), equalTo(4.5));
    assertThat(v2.get(y), equalTo(3.0));
  }

  @Test
  public void scalingVectorShouldNotInfluenceOperand() {
    RealVector v = vector().set(x, 3.0).set(y, 2.0).build();
    RealVector.times(1.5, v);

    assertThat(v.get(x), equalTo(3.0));
    assertThat(v.get(y), equalTo(2.0));
  }

  // Solution Assignment

  @Test
  public void assignShouldSetSolutions() {
    RealVector v = vector().set(x, 3.14).set(y, 2.89).build();
    space.assignToVariables(v);
    assertThat(x.solution, equalTo(3));
    assertThat(y.solution, equalTo(2.89));
  }

  // Auxiliary Testing Methods

  private RealVector.Builder vector() {
    return space.makeVector();
  }
}
