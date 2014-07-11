//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

package gov.nasa.jpf.symbc;

import gov.nasa.jpf.vm.Verify;

public class Debug {
    native public static void printPC(String msg);
    native public static String getSolvedPC();

    native public static String getSymbolicIntegerValue(int v);
    native public static String getSymbolicRealValue(double v);
    native public static String getSymbolicBooleanValue(boolean v);
    native public static String getSymbolicStringValue(String v);
    
    native public static boolean checkAccuracy(double v, double err); 
    // check accuracy of floating point computation
    // wrt given error
    
    public static void assume (boolean c) {
    	if(!c)
    		Verify.ignoreIf(true);
    }

    // puts a new symbolic value in the arg attribute
    native public static int makeSymbolicInteger(String name);
    native public static double makeSymbolicReal(String name);
    native public static boolean makeSymbolicBoolean(String name);
    native public static String makeSymbolicString(String name);
    
    // makes v a symbolic object
    public static Object makeSymbolicRef(String name, Object v) {
    	assert (v!=null); // needed for type info
    	if (Verify.randomBool()) {

    		makeFieldsSymbolic(name, v);
    	}
    	else {

    		v = makeSymbolicNull(name);
    	}
    	return v;
    }

    native public static void makeFieldsSymbolic(String name, Object v);
    native public static Object makeSymbolicNull(String name);

    native public static void printSymbolicRef(Object v, String msg);

    native public static void printHeapPC(String msg);


    // performs abstract state matching
    native public static boolean matchAbstractState(Object v);

}
