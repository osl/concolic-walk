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

import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Real vector space that uses {@link SymbolicInteger}s and {@link SymbolicReal}s
 * to label its dimensions.
 * 
 * The RealVectorSpace maps the dimension labels to unique integer indices,
 * which allows {@link RealVector}s to store their values in arrays (instead of
 * maps).
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
class RealVectorSpace {

  private final List<Object> dimensions;
  private final Map<Object, Integer> dimensionIndices;

  private RealVectorSpace(List<Object> dimensions) {
    this.dimensions = Collections.unmodifiableList(dimensions);
    this.dimensionIndices = new HashMap<>(dimensions.size());
    for (int i = 0; i < dimensions.size(); ++i) {
      dimensionIndices.put(dimensions.get(i), i);
    }
  }

  public List<Object> dimensions() {
    return dimensions;
  }

  public int indexOf(Object dimension) {
    Integer index = dimensionIndices.get(dimension);
    if (index == null) {
      throw new IllegalArgumentException("Unknown dimension: " + dimension);
    }
    return index;
  }

  public RealVector.Builder makeVector() {
    return new RealVector.Builder(this);
  }

  public RealVector.Builder makeVector(RealVector initialValues) {
    return new RealVector.Builder(initialValues);
  }

  public MutableRealVector makeMutableVector() {
    return new MutableRealVector(this);
  }

  public void assignToVariables(RealVector vector) {
    if (vector.space() != this) {
      throw new IllegalArgumentException("Vector does not belong to this space");
    }
    for (Object var : dimensions) {
      if (var instanceof SymbolicInteger) {
        ((SymbolicInteger) var).solution = (int) Math.round(vector.get(var));
      } else if (var instanceof SymbolicReal) {
        ((SymbolicReal) var).solution = vector.get(var);
      } else {
        throw new AssertionError("Unknown symbolic variable type");
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(RealVectorSpace.class.getSimpleName());
    sb.append("{");
    for (int i = 0; i < dimensions.size(); ++i) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(labelDimension(dimensions.get(i)));
    }
    sb.append("}");
    return sb.toString();
  }

  public static String labelDimension(Object dimension) {
    if (dimension instanceof SymbolicInteger) {
      return ((SymbolicInteger) dimension).getName();
    } else if (dimension instanceof SymbolicReal) {
      return ((SymbolicReal) dimension).getName();
    } else {
      throw new IllegalArgumentException("Unknown dimension type: " + dimension);
    }
  }

  public static RealVectorSpace forDimensions(Set<Object> symbolicVariables) {
    for (Object var : symbolicVariables) {
      if (!(var instanceof SymbolicInteger || var instanceof SymbolicReal)) {
        throw new IllegalArgumentException("Unknown symbolic variable type: " + var);
      }
    }
    List<Object> sortedDimensions = new ArrayList<>(symbolicVariables);
    Collections.sort(sortedDimensions, ALPHABETICAL);
    return new RealVectorSpace(sortedDimensions);
  }

  public static RealVectorSpace forDimensions(Object... symbolicVariables) {
    return forDimensions(new HashSet<Object>(Arrays.asList(symbolicVariables)));
  }

  public static RealVectorSpace extend(RealVectorSpace space, Set<Object> extraVariables) {
    for (Object var : space.dimensions) {
      if (extraVariables.contains(var)) {
        throw new IllegalArgumentException("Extra variable already in space: " + var);
      }
    }
    List<Object> sortedDimensions = new ArrayList<>(space.dimensions);
    sortedDimensions.addAll(extraVariables);
    Collections.sort(sortedDimensions, ALPHABETICAL);
    return new RealVectorSpace(sortedDimensions);
  }

  private final static Comparator<Object> ALPHABETICAL = new Comparator<Object>() {
    @Override
    public int compare(Object arg0, Object arg1) {
      return arg0.toString().compareTo(arg1.toString());
    }
  };
}
