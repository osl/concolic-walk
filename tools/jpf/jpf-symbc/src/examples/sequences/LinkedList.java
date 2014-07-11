package sequences;



class LinkedListNode{
	public int data;
	public LinkedListNode next;
	public LinkedListNode(int x){
		data = x;
		next = null;
	}
}

/**
 *
 * @author Mithun Acharya
 *
 */
public class LinkedList {
	private LinkedListNode head;
	private LinkedListNode tail;

	public LinkedList(){
		head = null;
		tail = null;
	}

	boolean insertFront(int x){
		LinkedListNode newNode = null;
		newNode = new LinkedListNode(x);
		if (newNode == null){
			return false;
		}
		newNode.next = head;
		head = newNode;
		return true;
	}

	boolean find(int x){
		LinkedListNode current = head;
		while(current!=null && current.data != x)
			current = current.next;
		if (current == null) return false;
		return true;
	}

	boolean deleteElement(LinkedListNode deleteMe){
		LinkedListNode current = head;

		if (head == deleteMe){
			head = head.next;
			return true;
		}

		while(current!=null){
			if (current.next == deleteMe){
				current.next = deleteMe.next;
				return true;
			}
			current = current.next;
		}
		return false;
	}
}
