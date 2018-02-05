import static org.junit.Assert.*;

import org.junit.Test;

public class SkipListTest {

	@Test
	public void test() {
		SkipList<Integer, String> l = new SkipList<Integer, String>();
		l.print();
		System.out.println();
		l.put(20, "a");
		l.print();
		System.out.println();
		l.put(15, "a");
		l.print();
		System.out.println();
		l.put(37, "a");
		l.print();
		System.out.println();
		l.put(5, "a");
		l.print();
		assertEquals("a", l.put(20, "b"));
		assertEquals("b", l.get(20));
		System.out.println();
		SkipListNode<Integer, String> node = l.search(20);
		System.out.println(l.searchSteps(37));
		System.out.print(node.getPrev().getKey());
		System.out.println(" prev");
		System.out.print(node.getNext().getKey());
		System.out.println(" next");
		System.out.print(node.getAbove().getKey());
		System.out.println(" above");
		System.out.print(node.getBelow());
		System.out.println(" below");
	}

}
