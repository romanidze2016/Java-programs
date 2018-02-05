import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import interfaces.Position;

import org.junit.Test;

import simpletree.SimplePosition;

public class TreeTraversalsTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

	@Test (timeout=1000)
	public void testPreOrder() {
		
		MyTree<String> tree = new MyTree<String>();
		
		assertEquals(new ArrayList<String>(), tree.preOrder());
		
		tree.setRoot(new SimplePosition<String>("root"));
		assertEquals(Arrays.asList(new String[] {"root"}), tree.preOrder());

		tree.insert(tree.root(), new SimplePosition<String>("a"));
		assertEquals(Arrays.asList(new String[] {"root", "a"}), tree.preOrder());

		tree.insert(tree.root(), new SimplePosition<String>("b"));
		assertEquals(Arrays.asList(new String[] {"root", "a", "b"}), tree.preOrder());
		
	}

	@Test (timeout=1000)
	public void testPostOrder() {
		
		MyTree<String> tree = new MyTree<String>();
		
		assertEquals(new ArrayList<String>(), tree.postOrder());
		
		tree.setRoot(new SimplePosition<String>("root"));
		assertEquals(Arrays.asList(new String[] {"root"}), tree.postOrder());

		tree.insert(tree.root(), new SimplePosition<String>("a"));
		assertEquals(Arrays.asList(new String[] {"a", "root"}), tree.postOrder());

		tree.insert(tree.root(), new SimplePosition<String>("b"));
		assertEquals(Arrays.asList(new String[] {"a", "b", "root"}), tree.postOrder());

	}
	
	@Test (timeout=1000)
	public void testInOrder() {
		
		MyTree<String> tree = new MyTree<String>();
		
		assertEquals(new ArrayList<String>(), tree.inOrder());
		
		tree.setRoot(new SimplePosition<String>("root"));
		assertEquals(Arrays.asList(new String[] {"root"}), tree.inOrder());

		tree.insert(tree.root(), new SimplePosition<String>("a"));
        exception.expect(UnsupportedOperationException.class);
		tree.inOrder();

		tree.insert(tree.root(), new SimplePosition<String>("b"));
		assertEquals(Arrays.asList(new String[] {"a", "root", "b"}), tree.inOrder());

		tree.insert(tree.root(), new SimplePosition<String>("c"));
        exception.expect(UnsupportedOperationException.class);
		tree.inOrder();

	}

}
