package symbolicheap;

/**
 * @author pcorina
 *
 */

public class MainLazy {
	
	//	from TACAS'03 paper "Generalized Symbolic Execution ..."
	static Node swapNode (Node l) {
		if (l.next != null)
			if (l.elem > l.next.elem) {
				Node t = l.next;
		        l.next = t.next;
		        t.next = l;
		        return t;
			}
		return l;
	}
	
	
	
	public static void main(String[] args) {
		Node l = Node._get_Node();
		
		if(l!=null) {
			Node t = l.swapNodeSymbolic();
		}
		System.out.println(">>>>");
		Node.printOriginalListCycles(l);
	}
}

