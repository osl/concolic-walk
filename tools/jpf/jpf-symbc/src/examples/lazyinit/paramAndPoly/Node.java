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

package lazyinit.paramAndPoly;


public class Node {

	Node next;
	
	public boolean isNext(ExtendedNode node) {
		return this.next == node;
		
	}
	
	public boolean isNextObject(Object node) {
		if( this.next == node )
			return true;
		else  {
			nextObjectTypeHierarchy(node);
			return false;
		}
	}
	
	void nextObjectTypeHierarchy(Object node) {
		if(!(node instanceof Node)) {
			if(this.next != node) {
				System.out.println("this.next != node");
			} else{
				System.out.println("===equal====: assert failed");
			}
		}
	}
	
}