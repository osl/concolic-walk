package sequences;


import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Preconditions;

class StackNode {
  public int value;
  public StackNode next;

  public StackNode(int x) {
    value = x;
    next = null;
	}
}

/**
 *
 * @author Mithun Acharya
 *
 */
public class Stack {
  private StackNode top;

  public Stack( ) {
    top = null;
  }

  public boolean isEmpty( ) {
    return top == null;
  }

  @Preconditions("i>0")
  public void push(int i) {
    StackNode newStackNode = new StackNode(i);
    if (i == 0) System.out.println("pushed " + 0);
    else System.out.println("pushed " + "!0");
    newStackNode.next = top;
    top = newStackNode;
  }

  @Preconditions("dummy>0")
  public int pop(int dummy) {
    if(isEmpty( ))
    	throw new RuntimeException("empty stack");
    else{
    	int value = top.value;
    	top = top.next;
    	System.out.println("popping ");
    	return value;
    }
  }
}



