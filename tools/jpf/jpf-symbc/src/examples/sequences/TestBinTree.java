package sequences;


/**
 *
 * @author Mithun Acharya
 * A small program to show the bug in issta2006.BinTree.BinTree.java
 * exposed through OSM construction.
 *
 */
public class TestBinTree {
	public static void main(String[] args){
		BinTree t = new BinTree();
		if(t.find(9) == true)
			System.out.println("Not possible for sure!!");
		t.add(9);
		t.remove(9);
		if(t.find(9) == true){
			System.out.println("BUG");
		}
	}
}
