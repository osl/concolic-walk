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

import static gov.nasa.jpf.symbc.concolic.walk.RealVectorSpace.labelDimension;

import java.util.Arrays;
import java.util.List;

/**
 * Immutable point in a {@link RealVectorSpace}.
 * 
 * Every RealVector belongs to a RealVectorSpace.  Vector operations require
 * that both operands belong to the same space, which prevents accidentally
 * mixing entries that mean different things.
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
final class RealVector {

  private final RealVectorSpace space;
  private final double[] values;

  private RealVector(RealVectorSpace space, double[] values) {
    assert space.dimensions().size() == values.length;
    this.space = space;
    this.values = values;  // Vector takes ownership of values.
  }

  public RealVectorSpace space() {
    return space;
  }

  public double get(Object dimension) {
    return values[space.indexOf(dimension)];
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RealVector)) {
      return false;
    }
    RealVector otherVector = (RealVector) other;
    return this.space == otherVector.space
        && Arrays.equals(this.values, otherVector.values);
  }

  @Override
  public int hashCode() {
    int hash = space.hashCode() * 31;
    for (double val : values) {
      hash = hash * 31 + ((int) val);
    }
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(RealVector.class.getSimpleName());
    sb.append("{");
    List<Object> dimensions = space.dimensions();
    for (int i = 0; i < dimensions.size(); ++i) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(labelDimension(dimensions.get(i))).append(": ");
      sb.append(values[i]);
    }
    sb.append("}");
    return sb.toString();
  }

  // Operations

  public static RealVector plus(RealVector a, RealVector b) {
    checkSameSpace(a, b);
    int n = a.values.length;
    double[] resultValues = new double[n];
    for (int i = 0; i < n; ++i) {
      resultValues[i] = a.values[i] + b.values[i];
    }
    return new RealVector(a.space, resultValues);
  }

  public static RealVector minus(RealVector a, RealVector b) {
    checkSameSpace(a, b);
    int n = a.values.length;
    double[] resultValues = new double[n];
    for (int i = 0; i < n; ++i) {
      resultValues[i] = a.values[i] - b.values[i];
    }
    return new RealVector(a.space, resultValues);
  }

  public static RealVector times(double scalar, RealVector a) {
    int n = a.values.length;
    double[] resultValues = new double[n];
    for (int i = 0; i < n; ++i) {
      resultValues[i] = scalar * a.values[i];
    }
    return new RealVector(a.space, resultValues);
  }

  private static void checkSameSpace(RealVector a, RealVector b) {
    if (a.space != b.space) {
      throw new IllegalArgumentException("Vectors must belong to the same space");
    }
  }

  // Builder

  public static class Builder {

    private final RealVectorSpace space;
    private final double[] values;

    public Builder(RealVectorSpace space) {
      this.space = space;
      this.values = new double[space.dimensions().size()];
    }

    public Builder(RealVector initialValues) {
      this.space = initialValues.space;
      this.values = Arrays.copyOf(initialValues.values, initialValues.values.length);
    }

    public Builder set(Object dimension, double value) {
      values[space.indexOf(dimension)] = value;
      return this;
    }
    
    public RealVector build() {
      return new RealVector(space, Arrays.copyOf(values, values.length));
    }
  }
}
