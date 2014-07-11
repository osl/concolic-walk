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

public class TestPolymorphism {
	
	
	public boolean swapAndCheckNode(Node n) {
		if(n != null) {
			if (n.next != null) {
				Node t = n.next;
				n.next = t.next;
				t.next = n;
				return (t instanceof intNode);
			}
			return (n instanceof intNode);
		}
		return false;
	}
	
	public static void main( String [] args) {
		TestPolymorphism tp = new TestPolymorphism();
		Node n = null;
		boolean result = tp.swapAndCheckNode(n);
		System.out.println("the value of result is" + result);
	}
}