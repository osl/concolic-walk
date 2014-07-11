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

import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.PathCondition;

import java.util.Iterator;

/**
 * Auxiliary class to allow for-each iteration over the {@link Constraint}s in a
 * {@link PathCondition}.
 * 
 * <p>
 * Usage example: {@code for (Constraint c : eachConstraint(pc)) { ... } }
 * 
 * @author Peter Dinges <pdinges@acm.org>
 */
class ConstraintIterator implements Iterator<Constraint> {

  private Constraint nextConstraint;

  public ConstraintIterator(Constraint constraint) {
    this.nextConstraint = constraint;
  }

  @Override
  public boolean hasNext() {
    return nextConstraint != null;
  }

  @Override
  public Constraint next() {
    Constraint result = nextConstraint;
    nextConstraint = nextConstraint.getTail();
    return result;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  public static Iterable<Constraint> eachConstraint(final PathCondition pc) {
    return new Iterable<Constraint>() {
      @Override
      public Iterator<Constraint> iterator() {
        return new ConstraintIterator(pc.header);
      }
    };
  }
}
