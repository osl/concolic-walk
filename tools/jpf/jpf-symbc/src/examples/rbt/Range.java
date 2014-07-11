package rbt;

//package intkey;
/*
 * Range
 *
 *  - Feb 1, 2006
 *
 * Copyright (c) 2003 Kansas State University, Laboratory for the Specification,
 * Analysis, and Transformation of Software
 *
 * This software is licensed under the SAnToS Laboratory Open Academic License.  You
 * should have received a copy of the license with the distribution.  A copy can be
 * found at:
 * http://www.cis.ksu.edu/santos/license.shtml
 * or you can contact the lab at:
 * SAnToS Laboratory
 * 234 Nichols Hall
 * Manhattan, KS 66506, USA
 */

public class Range {
    public final int lower;
    public final int upper;
    public final boolean isPositiveInfinity;
    public final boolean isNegativeInfinity;
    public Range() {
        this(0,0,true,true);
    }
    private Range(int u,int l, boolean ip, boolean in) {
        this.upper = u;
        this.lower = l;
        this.isPositiveInfinity=ip;
        this.isNegativeInfinity=in;
    }
    public boolean inRange(int value) {
        boolean ret=true;
        if(!isPositiveInfinity) {
            ret = value < upper;
        }
        if(!isNegativeInfinity) {
            ret = ret && (value > lower);
        }
        return ret;
    }
    public Range setLower(int l) {
        //assert isNegativeInfinity || (l>lower);
        return new Range(upper,l,isPositiveInfinity,false);
    }
    public Range setUpper(int u) {
        //assert isPositiveInfinity || (u<upper);
        return new Range(u,lower,false,isNegativeInfinity);
    }
}
