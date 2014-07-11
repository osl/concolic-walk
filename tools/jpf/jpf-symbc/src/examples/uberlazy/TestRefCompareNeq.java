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

public class TestRefCompareNeq {
	
	@Symbolic("true")
	Node n;
	@Symbolic("true")
	Node m;
	
	public void run () {
		if(n != null) {
			if(m != null) {
			//   the bytecode sequences that the following code is
			//	 15:	getfield	#2; //Field n:Luberlazy/Node;
			//   18:	aload_0
			//   19:	getfield	#3; //Field m:Luberlazy/Node;
			//   22:	if_acmpne	36

			// hence this example really is comparing inequality

				if(n == m) {
					System.out.println("the objects are equal");
				} else {
					System.out.println("They are not equal");
				}
			}
		}
	}

	
	public static void main (String[] args) {
		TestRefCompareNeq test = new TestRefCompareNeq();
		test.run();
	}
	
}