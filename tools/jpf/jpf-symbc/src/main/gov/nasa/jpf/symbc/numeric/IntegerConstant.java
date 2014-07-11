//
//Copyright (C) 2005 United States Government as represented by the
//Administrator of the National Aeronautics and Space Administration
//(NASA).  All Rights Reserved.
//
//This software is distributed under the NASA Open Source Agreement
//(NOSA), version 1.3.  The NOSA has been approved by the Open Source
//Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
//directory tree for the complete NOSA document.
//
//THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
//KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
//LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
//SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
//A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
//THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
//DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

package gov.nasa.jpf.symbc.numeric;

import java.util.Map;
import static gov.nasa.jpf.symbc.numeric.Operator.*;

public class IntegerConstant extends LinearIntegerExpression {
  public int value;

  public IntegerConstant (int i) {
    value = i;
  }

  public IntegerExpression _minus (int i) {
      //simplify
      if (i == 0)
          return this;

    return new IntegerConstant(value - i);
  }

  public IntegerExpression _minus_reverse (int i) {
    return new IntegerConstant(i - value);
  }
  public IntegerExpression _minus_reverse (long i) {
	    return new IntegerConstant((int) i - value);
	  }

  public IntegerExpression _minus (IntegerExpression e) {
      //simplify
      if (e instanceof IntegerConstant) {
          IntegerConstant ic = (IntegerConstant)e;
          if (ic.value == 0)
              return this;
      }
      if (e == this)
          return new IntegerConstant(0);

    if (e instanceof IntegerConstant) {
      return new IntegerConstant(value - ((IntegerConstant) e).value);
    } else {
      return super._minus(e);
    }
  }

  public IntegerExpression _div (int i) {
      //simplify
      if (i == 1)
          return this;
      assert (i != 0);
    return new IntegerConstant(value / i);
  }

  public IntegerExpression _div_reverse (int i) {
      //simplify
	  assert  (value !=0);
    return new IntegerConstant(i / value);
  }

  public IntegerExpression _div_reverse (long i) {
      //simplify
	  assert  (value !=0);
    return new IntegerConstant((int) i / value);
  }

  public IntegerExpression _div (IntegerExpression e) {
	  // simplify
	  if (e instanceof IntegerConstant) {
		  IntegerConstant ic = (IntegerConstant) e;
		  assert (ic.value != 0);
		  if (ic.value == 1)
			  return this;
		  else
			  return new IntegerConstant(value / ic.value);
	  }
	  if (e == this)
		  return new IntegerConstant(1);

	 return super._div(e);
  }

  public IntegerExpression _mul (int i) {
      //simplify
      if (i == 1)
          return this;
      if (i == 0)
          return new IntegerConstant(0);

    return new IntegerConstant(value * i);
  }

  public IntegerExpression _mul (IntegerExpression e) {
      // simplify
      if (e instanceof IntegerConstant) {
          IntegerConstant ic = (IntegerConstant) e;
          if (ic.value == 1)
              return this;
          if (ic.value == 0)
              return new IntegerConstant(0);
      }

    if (e instanceof IntegerConstant) {
      return new IntegerConstant(value * ((IntegerConstant) e).value);
    } else if (e instanceof LinearIntegerExpression) {
      return new BinaryLinearIntegerExpression(this, MUL, e);
    } else {
      return super._mul(e);
    }
  }


  public IntegerExpression _plus (int i) {
      //simplify
      if (i == 0)
          return this;

    return new IntegerConstant(value + i);
  }

  public IntegerExpression _plus (IntegerExpression e) {
      //simplify
      if (e instanceof IntegerConstant) {
          IntegerConstant ic = (IntegerConstant)e;
          if (ic.value == 0)
              return this;
      }

    if (e instanceof IntegerConstant) {
      return new IntegerConstant(value + ((IntegerConstant) e).value);
    } else {
      return super._plus(e);
    }
  }

	public IntegerExpression _neg ()
	{
		if (value == 0)
			return this;
		else
			return super._neg();
	}


	public IntegerExpression _and (int i) {
	   if (i == 0) {
		   return new IntegerConstant(0);
	   }
	    return new IntegerConstant(value & i);
	}

	public IntegerExpression _and (long i) {
		   if (i == 0) {
			   return new IntegerConstant(0);
		   }
		    return new IntegerConstant(value & ((int) i));
		}

	public IntegerExpression _and (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			if(((IntegerConstant) e).value == 0) {
				return new IntegerConstant(0);
			}
			return new IntegerConstant(value & ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, AND, e);
	}

	public IntegerExpression _or (int i) {
		if (i == 0) {
			return this;
		}
		return new IntegerConstant(value | i);
	}

	public IntegerExpression _or (long i) {
		if (i == 0) {
			return this;
		}
		return new IntegerConstant(value | ((int) i));
	}

	public IntegerExpression _or (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			if(((IntegerConstant) e).value == 0) {
				return this;
			}
			return new IntegerConstant(value | ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, OR, e);
	}

	public IntegerExpression _xor (int i) {
		    return new IntegerConstant(value ^ i);
	}

	public IntegerExpression _xor (long i) {
	    return new IntegerConstant(value ^ ((int) i));
	}

	public IntegerExpression _xor (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			return new IntegerConstant(value ^ ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, XOR, e);
	}


	public IntegerExpression _shiftL (int i) {
	    return new IntegerConstant(value << i);
	}

	public IntegerExpression _shiftL (long i) {
		return new IntegerConstant(value << ((int) i));
	}

	public IntegerExpression _shiftL (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			return new IntegerConstant(value << ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, SHIFTL, e);
	}

	public IntegerExpression _shiftR (int i) {
	    return new IntegerConstant(value >> i);
	}

	public IntegerExpression _shiftR (long i) {
		return new IntegerConstant(value >> ((int) i));
	}

	public IntegerExpression _shiftR (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			return new IntegerConstant(value >> ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, SHIFTR, e);
	}

	public IntegerExpression _shiftUR (int i) {
	    return new IntegerConstant(value >>> i);
	}

	public IntegerExpression _shiftUR (long i) {
		return new IntegerConstant(value >>> ((int) i));
	}

	public IntegerExpression _shiftUR (IntegerExpression e) {
		if (e instanceof IntegerConstant) {
			return new IntegerConstant(value >>> ((IntegerConstant) e).value);
		}
		return new BinaryLinearIntegerExpression(this, SHIFTUR, e);
	}

	@Override
  public boolean equals (Object o) {
    if (!(o instanceof IntegerConstant)) {
      return false;
    }

    return value == ((IntegerConstant) o).value;
  }

	@Override
  public int hashCode() {
	  return value;
  }
 
  public String toString () {
    return "CONST_" + value + "";
  }

  public String stringPC () {
    return "CONST_" + value + "";
  }

  public int value () {
    return value;
  }

  public int solution() {
  		return value;
  }

  public void getVarsVals(Map<String,Object> varsVals) {}
  
	// JacoGeldenhuys
	@Override
	public void accept(ConstraintExpressionVisitor visitor) {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

	@Override
	public int compareTo(Expression expr) {
		if (expr instanceof IntegerConstant) {
			IntegerConstant e = (IntegerConstant) expr;
			int a = value();
			int b = e.value();
			return (a < b) ? -1 : (a > b) ? 1 : 0;
		} else {
			return getClass().getCanonicalName().compareTo(expr.getClass().getCanonicalName());
		}
	}

}
