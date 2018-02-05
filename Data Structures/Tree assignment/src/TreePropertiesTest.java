  import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simpletree.SimplePosition;

public class TreePropertiesTest {
	
	private MyTree<String> treeEmpty;
	private MyTree<String> treeSingleNode;
	private MyTree<String> treeBinaryAbc;
	private MyTree<String> treePathAb;
	private MyTree<String> treePathAbc;
	
    @Before
    public void setUp() {

    	treeEmpty = new MyTree<String>();
    	
    	treeSingleNode = new MyTree<String>();
    	treeSingleNode.setRoot(new SimplePosition<String>("root"));
    	
    	treeBinaryAbc = new MyTree<String>();
    	treeBinaryAbc.setRoot(new SimplePosition<String>("b"));
    	treeBinaryAbc.insert(treeBinaryAbc.root(), new SimplePosition<String>("a"));
    	treeBinaryAbc.insert(treeBinaryAbc.root(), new SimplePosition<String>("c"));
    	
    	treePathAb = new MyTree<String>();
    	treePathAb.setRoot(new SimplePosition<String>("a"));
    	treePathAb.insert(treePathAb.root(), new SimplePosition<String>("b"));

    	treePathAbc = new MyTree<String>();
    	treePathAbc.setRoot(new SimplePosition<String>("a"));
    	treePathAbc.insert(treePathAbc.root(), new SimplePosition<String>("b"));
    	treePathAbc.insert(treePathAbc.root().getChildren().get(0), new SimplePosition<String>("c"));

    }

	@Test (timeout=1000)
	public void testHeight() {

		assertEquals(-1, treeEmpty.height());
		assertEquals(0, treeSingleNode.height());
		assertEquals(1, treeBinaryAbc.height());
		assertEquals(1, treePathAb.height());
		assertEquals(2, treePathAbc.height());
		
	}

	@Test (timeout=1000)
	public void testHeightLimited() {

		assertEquals(-1, treeEmpty.height(0));
		assertEquals(0, treePathAbc.height(0));
		assertEquals(1, treePathAbc.height(1));
		assertEquals(2, treePathAbc.height(2));
		assertEquals(2, treePathAbc.height(3));
	}

	@Test (timeout=1000)
	public void testNumLeaves() {
		
		assertEquals(0, treeEmpty.numLeaves());
		assertEquals(1, treeSingleNode.numLeaves());
		assertEquals(2, treeBinaryAbc.numLeaves());
		assertEquals(1, treePathAb.numLeaves());
		assertEquals(1, treePathAbc.numLeaves());
		
	}

	@Test (timeout=1000)
	public void testNumLeavesAtDepth() {
		
		assertEquals(0, treeBinaryAbc.numLeaves(0));
		assertEquals(2, treeBinaryAbc.numLeaves(1));
		assertEquals(0, treeBinaryAbc.numLeaves(2));
		
	}

	@Test (timeout=1000)
	public void testNumPositionsAtDepth() {

		assertEquals(1, treeBinaryAbc.numPositions(0));
		assertEquals(2, treeBinaryAbc.numPositions(1));
		assertEquals(0, treeBinaryAbc.numPositions(2));
		
	}

	@Test (timeout=1000)
	public void testIsBinary() {
		
		assertTrue(treeEmpty.isBinary());
		assertTrue(treeSingleNode.isBinary());
		assertTrue(treeBinaryAbc.isBinary());
		assertTrue(treePathAb.isBinary());
		assertTrue(treePathAbc.isBinary());

	}

	@Test (timeout=1000)
	public void testIsProperBinary() {
		
		assertTrue(treeEmpty.isProperBinary());
		assertTrue(treeSingleNode.isProperBinary());
		assertTrue(treeBinaryAbc.isProperBinary());
		assertFalse(treePathAb.isProperBinary());
		assertFalse(treePathAbc.isProperBinary());

	}

	@Test (timeout=1000)
	public void testIsCompleteBinary() {
		
		assertTrue(treeEmpty.isCompleteBinary());
		assertTrue(treeSingleNode.isCompleteBinary());
		assertTrue(treeBinaryAbc.isCompleteBinary());
		assertTrue(treePathAb.isCompleteBinary());
		assertFalse(treePathAbc.isCompleteBinary());

	}

	@Test (timeout=1000)
	public void testIsBalancedBinary() {
		
		assertTrue(treeEmpty.isBalancedBinary());
		assertTrue(treeSingleNode.isBalancedBinary());
		assertTrue(treeBinaryAbc.isBalancedBinary());
		assertTrue(treePathAb.isBalancedBinary());
		assertFalse(treePathAbc.isBalancedBinary());

	}

	@Test (timeout=1000)
	public void testIsHeap() {
		
		assertTrue(treeEmpty.isHeap(true));
		assertTrue(treeSingleNode.isHeap(true));
		assertFalse(treeBinaryAbc.isHeap(true));
		assertTrue(treePathAb.isHeap(true));
		assertFalse(treePathAbc.isHeap(true)); //not balanced

		assertTrue(treeEmpty.isHeap(false));
		assertTrue(treeSingleNode.isHeap(false));
		assertFalse(treeBinaryAbc.isHeap(false));
		assertFalse(treePathAb.isHeap(false));
		assertFalse(treePathAbc.isHeap(false));
	}
	
	@Test (timeout=1000)
	public void testIsBinarySearchTree() {

		assertTrue(treeEmpty.isBinarySearchTree());
		assertTrue(treeSingleNode.isBinarySearchTree());
		assertTrue(treeBinaryAbc.isBinarySearchTree());
		assertFalse(treePathAb.isBinarySearchTree()); //b is left child of a
		assertFalse(treePathAbc.isBinarySearchTree());
		
	}

}
