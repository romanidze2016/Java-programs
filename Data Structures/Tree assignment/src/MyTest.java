import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import interfaces.Position;
import simpletree.SimplePosition;

public class MyTest {

	@Test
	public void testBasicMethods() {
		MyTree<Integer> mine = new MyTree<Integer>();
		assertEquals(-1, mine.height());
		assertEquals(-1, mine.height(-1));
		assertEquals(0, mine.numLeaves());
		assertEquals(0, mine.numLeaves(10));
		assertEquals(0, mine.numPositions(2));

		mine.setRoot(new SimplePosition<Integer>(10));
		assertEquals(Arrays.asList(10), mine.preOrder());
		assertEquals(Arrays.asList(10), mine.postOrder());
		assertEquals(Arrays.asList(10), mine.inOrder());

		assertEquals(0, mine.height());
		assertEquals(0, mine.height(0));
		assertEquals(1, mine.numLeaves());
		assertEquals(1, mine.numLeaves(0));
		assertEquals(1, mine.numPositions(0));

		Position<Integer> node = new SimplePosition<Integer>(5);
		mine.insert(mine.root(), node);
		assertEquals(Arrays.asList(10, 5), mine.preOrder());
		assertEquals(Arrays.asList(5, 10), mine.postOrder());
		// Tree is not proper
		// assertEquals(new UnsupportedOperationException(), mine.inOrder());
		assertEquals(1, mine.height());
		assertEquals(1, mine.height(1));
		assertEquals(0, mine.height(0));
		assertEquals(1, mine.numLeaves());
		assertEquals(0, mine.numLeaves(0));
		assertEquals(1, mine.numLeaves(1));
		assertEquals(1, mine.numPositions(0));
		assertEquals(1, mine.numPositions(1));

		mine.insert(node, new SimplePosition<Integer>(3));
		assertEquals(Arrays.asList(10, 5, 3), mine.preOrder());
		assertEquals(Arrays.asList(3, 5, 10), mine.postOrder());
		//
		assertEquals(2, mine.height());
		assertEquals(2, mine.height(2));
		assertEquals(1, mine.height(1));
		assertEquals(1, mine.numLeaves());
		assertEquals(1, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(0, mine.numLeaves(0));
		assertEquals(1, mine.numPositions(0));
		assertEquals(1, mine.numPositions(1));
		assertEquals(1, mine.numPositions(2));

		mine.insert(node, new SimplePosition<Integer>(2));
		assertEquals(Arrays.asList(10, 5, 3, 2), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 10), mine.postOrder());
		//
		assertEquals(2, mine.height());
		assertEquals(2, mine.height(2));
		assertEquals(1, mine.height(1));
		assertEquals(2, mine.numLeaves());
		assertEquals(2, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(0, mine.numLeaves(0));
		assertEquals(1, mine.numPositions(0));
		assertEquals(1, mine.numPositions(1));
		assertEquals(2, mine.numPositions(2));

		node = new SimplePosition<Integer>(4);
		mine.insert(mine.root(), node);
		assertEquals(Arrays.asList(10, 5, 3, 2, 4), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 4, 10), mine.postOrder());
		assertEquals(Arrays.asList(3, 5, 2, 10, 4), mine.inOrder());
		//
		assertEquals(2, mine.height());
		assertEquals(2, mine.height(2));
		assertEquals(1, mine.height(1));
		assertEquals(3, mine.numLeaves());
		assertEquals(2, mine.numLeaves(2));
		assertEquals(1, mine.numLeaves(1));
		assertEquals(0, mine.numLeaves(0));
		assertEquals(1, mine.numPositions(0));
		assertEquals(2, mine.numPositions(1));
		assertEquals(2, mine.numPositions(2));

		Position<Integer> child = new SimplePosition<Integer>(1);
		mine.insert(node, child);
		assertEquals(Arrays.asList(10, 5, 3, 2, 4, 1), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 1, 4, 10), mine.postOrder());
		// Tree is not proper. Error occurs
		// assertEquals(Arrays.asList(3, 5, 2, 10, 4), mine.inOrder());
		assertEquals(2, mine.height());
		assertEquals(2, mine.height(2));
		assertEquals(1, mine.height(1));
		assertEquals(3, mine.numLeaves());
		assertEquals(3, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(0, mine.numLeaves(0));
		assertEquals(1, mine.numPositions(0));
		assertEquals(2, mine.numPositions(1));
		assertEquals(3, mine.numPositions(2));

		mine.insert(child, new SimplePosition<Integer>(8));
		assertEquals(Arrays.asList(10, 5, 3, 2, 4, 1, 8), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 8, 1, 4, 10), mine.postOrder());
		//
		assertEquals(3, mine.height());
		assertEquals(3, mine.height(3));
		assertEquals(1, mine.height(1));
		assertEquals(3, mine.numLeaves());
		assertEquals(2, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(1, mine.numLeaves(3));
		assertEquals(1, mine.numPositions(0));
		assertEquals(2, mine.numPositions(1));
		assertEquals(3, mine.numPositions(2));
		assertEquals(1, mine.numPositions(3));
		assertEquals(0, mine.numPositions(5));

		mine.insert(node, new SimplePosition<Integer>(11));
		assertEquals(Arrays.asList(10, 5, 3, 2, 4, 1, 8, 11), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 8, 1, 11, 4, 10), mine.postOrder());
		//
		assertEquals(3, mine.height());
		assertEquals(3, mine.height(3));
		assertEquals(1, mine.height(1));
		assertEquals(4, mine.numLeaves());
		assertEquals(3, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(1, mine.numLeaves(3));
		assertEquals(1, mine.numPositions(0));
		assertEquals(2, mine.numPositions(1));
		assertEquals(4, mine.numPositions(2));
		assertEquals(1, mine.numPositions(3));

		mine.insert(child, new SimplePosition<Integer>(20));
		assertEquals(Arrays.asList(10, 5, 3, 2, 4, 1, 8, 20, 11), mine.preOrder());
		assertEquals(Arrays.asList(3, 2, 5, 8, 20, 1, 11, 4, 10), mine.postOrder());
		assertEquals(Arrays.asList(3, 5, 2, 10, 8, 1, 20, 4, 11), mine.inOrder());
		//
		assertEquals(3, mine.height());
		assertEquals(3, mine.height(3));
		assertEquals(1, mine.height(1));
		assertEquals(5, mine.numLeaves());
		assertEquals(3, mine.numLeaves(2));
		assertEquals(0, mine.numLeaves(1));
		assertEquals(2, mine.numLeaves(3));
		assertEquals(1, mine.numPositions(0));
		assertEquals(2, mine.numPositions(1));
		assertEquals(4, mine.numPositions(2));
		assertEquals(2, mine.numPositions(3));
	}
	
	@Test
	public void testBinaryMethods() {
		MyTree<Integer> tree = new MyTree<Integer>();
		
		assertTrue(tree.isBinary());
		assertTrue(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.setRoot(new SimplePosition<Integer>(1));
		assertTrue(tree.isBinary());
		assertTrue(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root(), new SimplePosition<Integer>(2));
		assertTrue(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(3));
		assertTrue(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertFalse(tree.isCompleteBinary());
		assertFalse(tree.isBalancedBinary());
		
		tree.insert(tree.root(), new SimplePosition<Integer>(4));
		assertTrue(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(5));
		assertTrue(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertFalse(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(6));
		assertTrue(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(7));
		assertTrue(tree.isBinary());
		assertTrue(tree.isProperBinary());
		assertTrue(tree.isCompleteBinary());
		assertTrue(tree.isBalancedBinary());
		
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(8));
		assertFalse(tree.isBinary());
		assertFalse(tree.isProperBinary());
		assertFalse(tree.isCompleteBinary());
		assertFalse(tree.isBalancedBinary());
	}

	@Test
	public void testIsHeap() {
		MyTree<Integer> tree = new MyTree<Integer>();
		tree.setRoot(new SimplePosition<Integer>(2));
		tree.insert(tree.root(), new SimplePosition(5));
		tree.insert(tree.root(), new SimplePosition<Integer>(8));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(9));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(6));
		assertTrue(tree.isHeap(true));
		assertFalse(tree.isHeap(false));
		
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(1));
		assertFalse(tree.isHeap(true));
		assertFalse(tree.isHeap(false));
		
		tree = new MyTree<Integer>();
		tree.setRoot(new SimplePosition<Integer>(9));
		tree.insert(tree.root(), new SimplePosition<Integer>(7));
		tree.insert(tree.root(), new SimplePosition<Integer>(5));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(4));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(2));
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(1));
		assertTrue(tree.isHeap(false));
		assertFalse(tree.isHeap(true));
		
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(6));
		assertFalse(tree.isHeap(false));
		assertFalse(tree.isHeap(true));
		
	}
	
	@Test
	public void testBinarySearchTree() {
		MyTree<Integer> tree = new MyTree<Integer>();
		assertTrue(tree.isBinarySearchTree());
		tree.setRoot(new SimplePosition<Integer>(5));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root(), new SimplePosition<Integer>(2));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root(), new SimplePosition<Integer>(21));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(1));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<Integer>(3));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(19));
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<Integer>(25));
		assertTrue(tree.isBinarySearchTree());
		
		Position<Integer> node = new SimplePosition<Integer>(28);
		tree.insert(tree.root().getChildren().get(1), node);
		assertFalse(tree.isBinarySearchTree());
		tree.remove(node);
		assertTrue(tree.isBinarySearchTree());
		tree.insert(tree.root().getChildren().get(1).getChildren().get(0), new SimplePosition<Integer>(30));
		assertFalse(tree.isBinarySearchTree());
		
		tree = new MyTree<Integer>();
		tree.setRoot(new SimplePosition<Integer>(5));
		tree.insert(tree.root(), new SimplePosition<Integer>(21));
		tree.insert(tree.root(), new SimplePosition<Integer>(2));
		assertFalse(tree.isBinarySearchTree());
	}
	
	@Test
	public void testArithmetic() {
		MyTree<String> tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("+"));
		assertFalse(tree.isArithmetic());
		tree.insert(tree.root(), new SimplePosition<String>("*"));
		tree.insert(tree.root(), new SimplePosition<String>("-"));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<String>("4"));
		tree.insert(tree.root().getChildren().get(0), new SimplePosition<String>("2"));
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<String>("1"));
		assertFalse(tree.isArithmetic());
		tree.insert(tree.root().getChildren().get(1), new SimplePosition<String>("6"));
		
		assertTrue(tree.isArithmetic());
		Double result = 3.0;
		int answer;
		answer = result.compareTo(tree.evaluateArithmetic());
		assertEquals(0, answer);
		assertEquals((String) "(4*2)+(1-6)", tree.getArithmeticString());
	}
}
