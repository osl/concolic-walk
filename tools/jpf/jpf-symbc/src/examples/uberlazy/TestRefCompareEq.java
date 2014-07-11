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
package uberlazy;

import gov.nasa.jpf.symbc.Symbolic;

public class TestRefCompareEq {
	
	@Symbolic("true")
	Node n;
	@Symbolic("true")
	Node m;
	
	public void run () {
		if(n != null) {
			if(m != null) {
			//   the bytecode sequences that the following code is
				//   5:		getfield	#3; //Field m:Luberlazy/Node;
			  	//   8:		if_acmpeq	22
				//   11:	getstatic	#5; //Field java/lang/System.out:Ljava/io/PrintStream;
				//   14:	ldc	#6; //String the objects are equal
				// hence this example really is comparing equality

				if(n != m) {
					System.out.println("the objects are not equal");
				} else {
					System.out.println("They are equal");
				}
			}
		}
	}
	
	public void testEquality() {
		//   the bytecode sequences that the following code is
		//   5:		getfield	#3; //Field m:Luberlazy/Node;
	  	//   8:		if_acmpeq	22
		//   11:	getstatic	#5; //Field java/lang/System.out:Ljava/io/PrintStream;
		//   14:	ldc	#6; //String the objects are equal
		// hence this example really is comparing equality

		if(n != m) {
			System.out.println("the objects are not equal");
		} else {
			System.out.println("They are equal");
		}
	}
	
	
	public static void main (String[] args) {
		TestRefCompareEq test = new TestRefCompareEq();
		test.run();
	}
	
}