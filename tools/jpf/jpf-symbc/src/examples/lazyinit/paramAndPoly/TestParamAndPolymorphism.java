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

/**
 * @author Neha Rungta
 * @datecreated 01/26/10
 * @description: This example is take from "Efficient Symbolic
 * Execution Algorithms for Program Manipulating Dynamic Heap
 * Objects" By Xianghu Deng, Jooyong Lee, and Robby. The example
 * is used to demonstrate that the lazy initialization algorithm
 * handles the the "Liskov substitution principle" that states
 * a instance of a subtype can be used anywhere an object of a
 * supertype in the class heirarchy of the subtype is used. 
 * 
 * Node n1 and ExtendedNode en are symbolic objects. The pre-
 * condition to the isNext and isNextObject methods in Node
 * class is that the "this" object is not null. 
 * 
 * Additionally this example also tests lazy initialization of
 * objects that are passed as parameters to the methods. 
 * 
 */

package lazyinit.paramAndPoly;

/**
 * Example is taken from a technical report:
 * http://people.cis.ksu.edu/~robby/SAnToS-TR2009-09-25.pdf
 * "Efficient Symbolic Execution Algorithms for Programs
 *  Manipulating Dyanmic Heap Objects" by Xianghua Deng,
 *  Jooyong Lee, and Robby. 
 *  
 *  The example is used to demonstrate that the tool can 
 *  handle non-determinism from initializing subtypes 
 *  and also handle objects that are not a field references.
 *  
 **/

public class TestParamAndPolymorphism {
	
	public void run(Node n1, ExtendedNode en) {
		if(n1 != null) {
			if (n1.isNext(en)) {
				System.out.println("n1.next ==  ExtendedNode en");
			}
			if (n1.isNextObject(en)) {
				System.out.println("n1.next == Object en");
			}
		}
	}
	
	
	public static void main( String [] args) {
		TestParamAndPolymorphism tp = new TestParamAndPolymorphism();
		Node n1 = null;
		ExtendedNode en = null;
		tp.run(n1, en);
	}
}