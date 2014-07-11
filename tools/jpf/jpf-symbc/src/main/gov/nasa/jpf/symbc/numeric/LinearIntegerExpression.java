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
import static gov.nasa.jpf.symbc.numeric.Operator.*;

abstract class LinearIntegerExpression extends IntegerExpression
{
   public IntegerExpression _minus_reverse (int i)
   {
	return new BinaryLinearIntegerExpression(new IntegerConstant(i), MINUS, this);
   }

   public IntegerExpression _minus_reverse (long i)
   {
	return new BinaryLinearIntegerExpression(new IntegerConstant((int)i), MINUS, this);
   }

    public IntegerExpression _minus (int i) {
		//simplify
		if (i == 0)
			return this;

    	return new BinaryLinearIntegerExpression(this, MINUS, new IntegerConstant(i));
    }

    public IntegerExpression _minus (long i) {
		//simplify
		if (i == 0)
			return this;

		return new BinaryLinearIntegerExpression(this, MINUS, new IntegerConstant((int)i));
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

	if (e instanceof LinearIntegerExpression) {
	    return new BinaryLinearIntegerExpression(this, MINUS, e);
	} else {
	    return super._minus(e);
	}
    }

    public IntegerExpression _mul (int i) {
		//simplify
		if (i == 1)
			return this;
		if (i == 0)
			return new IntegerConstant(0);

	return new BinaryLinearIntegerExpression(this, MUL, new IntegerConstant(i));
    }

    public IntegerExpression _mul (long i) {
		//simplify
		if (i == 1)
			return this;
		if (i == 0)
			return new IntegerConstant(0);

    	return new BinaryLinearIntegerExpression(this, MUL, new IntegerConstant((int)i));
    }

    public IntegerExpression _mul (IntegerExpression e)
    {

		//simplify
		if (e instanceof IntegerConstant) {
			IntegerConstant ic = (IntegerConstant)e;
			if (ic.value == 1)
				return this;
			if (ic.value == 0)
				return new IntegerConstant(0);
		}

	if (e instanceof IntegerConstant)
	    return new BinaryLinearIntegerExpression(this, MUL, e);
	else {
	    return super._mul(e);
	}
    }


	public IntegerExpression _div (int i)
	{
		// simplify
		assert (i != 0);
		if (i == 1)
			return this;
		return new BinaryLinearIntegerExpression(this, DIV, new IntegerConstant(i));
	}

	public IntegerExpression _div (IntegerExpression e)
	{
		//simplify
		if (e instanceof IntegerConstant) {
			IntegerConstant ic = (IntegerConstant)e;
			assert (ic.value != 0);
			if (ic.value == 1)
				return this;
			else
				new BinaryLinearIntegerExpression(this, MUL, e);
		}
		if (e == this)
			return new IntegerConstant(1);

		return super._div(e);
	}

	public IntegerExpression _div_reverse (int i)
	{
		if (i == 0)
			return new IntegerConstant(0);
		return super._div(i);
	}

	public IntegerExpression _div (long i)
	{
		// simplify
		assert (i != 0);
		if (i == 1)
			return this;

		return new BinaryLinearIntegerExpression(this, DIV, new IntegerConstant((int)i));
	}

	public IntegerExpression _div_reverse (long i)
	{
		if (i == 0)
			return new IntegerConstant(0);
		return super._div(i);
	}

    public IntegerExpression _plus (int i) {
		//simplify
		if (i == 0)
			return this;

	return new BinaryLinearIntegerExpression(this, PLUS, new IntegerConstant(i));
    }

    public IntegerExpression _plus (long i) {
		//simplify
		if (i == 0)
			return this;

    	return new BinaryLinearIntegerExpression(this, PLUS, new IntegerConstant((int)i));
    }

    public IntegerExpression _plus (IntegerExpression e) {
		//simplify
		if (e instanceof IntegerConstant) {
			IntegerConstant ic = (IntegerConstant)e;
			if (ic.value == 0)
				return this;
		}

	if (e instanceof LinearIntegerExpression) {
	    return new BinaryLinearIntegerExpression(this, PLUS, e);
	} else {
	    return super._plus(e);
	}
    }

    public IntegerExpression _neg()
    {
	return new BinaryLinearIntegerExpression(new IntegerConstant(0), MINUS, this);
    }

    public IntegerExpression _and(int i) {
    	if(i == 0) {
    		return new IntegerConstant(0);
    	}
    	return new BinaryNonLinearIntegerExpression(this, AND, new IntegerConstant(i));
    }

    public IntegerExpression _and(long i) {
    	if(i == 0) {
    		return new IntegerConstant(0);
    	}
    	return new BinaryNonLinearIntegerExpression(this, AND, new IntegerConstant((int)i));
    }

    public IntegerExpression _and(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		IntegerConstant ic = (IntegerConstant) e;
    		if(ic.value == 0) {
    			return new IntegerConstant(0);
    		}
    	}
    	return new BinaryNonLinearIntegerExpression(this, AND, e);
    }

    public IntegerExpression _or(int i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, OR, new IntegerConstant(i));
    }

    public IntegerExpression _or(long i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, OR, new IntegerConstant((int)i));
    }

    public IntegerExpression _or(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		IntegerConstant ic = (IntegerConstant) e;
    		if(ic.value == 0) {
    			return this;
    		}
    	}
    	return new BinaryNonLinearIntegerExpression(this, OR, e);
    }

    public IntegerExpression _xor(int i) {
    	return new BinaryNonLinearIntegerExpression(this, XOR, new IntegerConstant(i));
    }

    public IntegerExpression _xor(long i) {
    	return new BinaryNonLinearIntegerExpression(this, XOR, new IntegerConstant((int)i));
    }

    public IntegerExpression _xor(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		return new BinaryLinearIntegerExpression(this, XOR, e);
    	}
    	return new BinaryNonLinearIntegerExpression(this, XOR, e);
    }

    public IntegerExpression _shiftR(int i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTR, new IntegerConstant(i));
    }

    public IntegerExpression _shiftR(long i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTR, new IntegerConstant((int)i));
    }

    public IntegerExpression _shiftR(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		IntegerConstant ic = (IntegerConstant) e;
    		if(ic.value == 0) {
    			return this;
    		}
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTR, e);
    }

    public IntegerExpression _shiftUR(int i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTUR, new IntegerConstant(i));
    }

    public IntegerExpression _shiftUR(long i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTUR, new IntegerConstant((int)i));
    }

    public IntegerExpression _shiftUR(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		IntegerConstant ic = (IntegerConstant) e;
    		if(ic.value == 0) {
    			return this;
    		}
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTUR, e);
    }

    public IntegerExpression _shiftL(int i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTL, new IntegerConstant(i));
    }

    public IntegerExpression _shiftL(long i) {
    	if(i == 0) {
    		return this;
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTL, new IntegerConstant((int)i));
    }

    public IntegerExpression _shiftL(IntegerExpression e) {
    	if(e instanceof IntegerConstant) {
    		IntegerConstant ic = (IntegerConstant) e;
    		if(ic.value == 0) {
    			return this;
    		}
    	}
    	return new BinaryNonLinearIntegerExpression(this, SHIFTL, e);
    }
    
    //protected void finalize() throws Throwable {
    //	System.out.println("Finalized LIE" + this);
    //}
    
}
