import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simpletree.SimplePosition;

public class TreeComparisonTest {

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
    	treeBinaryAbc.insert(treeBinaryAbc.root(), new SimplePosition<String>("b"));
    	
    	treePathAb = new MyTree<String>();
    	treePathAb.setRoot(new SimplePosition<String>("a"));
    	treePathAb.insert(treePathAb.root(), new SimplePosition<String>("b"));

    	treePathAbc = new MyTree<String>();
    	treePathAbc.setRoot(new SimplePosition<String>("a"));
    	treePathAbc.insert(treePathAbc.root(), new SimplePosition<String>("b"));
    	treePathAbc.insert(treePathAbc.root().getChildren().get(0), new SimplePosition<String>("c"));

    }

	@Test (timeout=1000)
	public void testSame() {
		assertEquals(0, treeEmpty.compareTo(treeEmpty));
		assertEquals(0, treeSingleNode.compareTo(treeSingleNode));
		assertEquals(0, treeBinaryAbc.compareTo(treeBinaryAbc));
		assertEquals(0, treePathAb.compareTo(treePathAb));
		assertEquals(0, treePathAbc.compareTo(treePathAbc));
	}
	
	@Test (timeout=1000)
	public void testEmpty() {
		assertEquals(0, treeEmpty.compareTo(treeEmpty));
		assertEquals(-1, treeEmpty.compareTo(treeSingleNode));
		assertEquals(-1, treeEmpty.compareTo(treePathAb));
		assertEquals(1, treeSingleNode.compareTo(treeEmpty));
		assertEquals(1, treePathAb.compareTo(treeEmpty));
	}
	
	@Test (timeout=1000)
	public void testSingleNode() {
		
    	MyTree<String> treeA = new MyTree<String>();
    	treeA.setRoot(new SimplePosition<String>("a"));

    	MyTree<String> treeA2 = new MyTree<String>();
    	treeA2.setRoot(new SimplePosition<String>("a"));

    	MyTree<String> treeD = new MyTree<String>();
    	treeD.setRoot(new SimplePosition<String>("d"));
    	
    	assertEquals(0, treeA.compareTo(treeA2));
    	assertEquals("a".compareTo("d"), treeA.compareTo(treeD));
    	assertEquals("d".compareTo("a"), treeD.compareTo(treeA));
		
	}

}
