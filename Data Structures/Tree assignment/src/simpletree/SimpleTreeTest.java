package simpletree;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import interfaces.Position;
import interfaces.Tree;

public class SimpleTreeTest {

	/*
	 * These tests have timeouts set to 1000 milliseconds (1 second)
	 * While using the debugger you will need to comment out the
	 * corresponding timeout
	 */
	
	/*
	 * There are no tests for insert and remove because they are implicitly
	 * tested in the other tests.
	 * 
	 * Note that if we required correct handling of error / edge cases such
	 * as attempting to remove nodes that are NOT in the tree already, then
	 * we would need to add tests for these.
	 */
	
    @Test (timeout = 1000)
    public void testConstruction() {
        Tree<String> tree = new SimpleTree<String>();
        assertNull(tree.root());
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }

    @Test (timeout = 1000)
    public void testRoot() {
        Tree<String> tree = new SimpleTree<String>();
        Position<String> hello = new SimplePosition<String>("hello");
        Position<String> world = new SimplePosition<String>("world");
        tree.setRoot(hello);
        assertSame(hello, tree.root());
        tree.setRoot(world);
        assertSame(world, tree.root());
    }
    
    @Test (timeout = 1000)
    public void testGetSize() {
        Tree<String> tree = new SimpleTree<String>();
        assertEquals(0, tree.size());

        Position<String> a = new SimplePosition<String>("a");
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");

        tree.setRoot(a);
        assertEquals(1, tree.size());
        
        tree.insert(a, b);
        assertEquals(2, tree.size());
        
        tree.insert(a, c);
        assertEquals(3, tree.size());
        
        tree.remove(c);
        assertEquals(2, tree.size());
        
        tree.remove(b);
        assertEquals(1, tree.size());
    }

    @Test (timeout = 1000)
    public void testRemoveRoot() {
        Tree<String> tree = new SimpleTree<String>();
        assertEquals(0, tree.size());

        Position<String> a = new SimplePosition<String>("a");
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");

        tree.setRoot(a);
        tree.insert(a, b);
        tree.insert(a, c);
        assertEquals(3, tree.size());
        
        tree.remove(a);
        assertEquals(0, tree.size());
    }
    
    @Test (timeout = 1000)
    public void testIsEmpty() {
        Tree<String> tree = new SimpleTree<String>();
        assertTrue(tree.isEmpty());

        Position<String> a = new SimplePosition<String>("a");
        Position<String> b = new SimplePosition<String>("b");
        Position<String> c = new SimplePosition<String>("c");

        tree.setRoot(a);
        assertFalse(tree.isEmpty());
        
        tree.insert(a, b);
        assertFalse(tree.isEmpty());
        
        tree.insert(a, c);
        assertFalse(tree.isEmpty());
        
        tree.remove(c);
        assertFalse(tree.isEmpty());
        
        tree.remove(b);
        assertFalse(tree.isEmpty());
         
        tree.remove(a);
        assertTrue(tree.isEmpty());
    }
    
    @Test (timeout = 1000)
    public void testParent() {
	    Tree<String> tree = new SimpleTree<String>();
	    assertEquals(0, tree.size());
	
	    Position<String> a = new SimplePosition<String>("a");
	    Position<String> b = new SimplePosition<String>("b");
	    Position<String> c = new SimplePosition<String>("c");
        tree.setRoot(a);
        tree.insert(a, b);
        tree.insert(b, c);
        
        assertSame(a, tree.parent(b));
        assertSame(b, tree.parent(c));
        assertNull(tree.parent(a));
    }

    @Test (timeout = 1000)
    public void testChildren() {
	    Tree<String> tree = new SimpleTree<String>();
	    assertEquals(0, tree.size());
	
	    Position<String> a = new SimplePosition<String>("a");
	    Position<String> b = new SimplePosition<String>("b");
	    Position<String> c = new SimplePosition<String>("c");
	    Position<String> d = new SimplePosition<String>("d");
        tree.setRoot(a);
        tree.insert(a, b);
        tree.insert(b, c);
        tree.insert(b, d);
        
        ArrayList<Position<String>> expected = new ArrayList<Position<String>>();
        assertEquals(expected, tree.children(c));
        
        expected.add(b);
        assertEquals(expected, tree.children(a));
        
        expected.remove(b);
        expected.add(c);
        expected.add(d);
        assertEquals(expected, tree.children(b));
    }

    @Test (timeout = 1000)
    public void testNumChildren() {
	    Tree<String> tree = new SimpleTree<String>();
	    assertEquals(0, tree.size());
	
	    Position<String> a = new SimplePosition<String>("a");
	    Position<String> b = new SimplePosition<String>("b");
	    Position<String> c = new SimplePosition<String>("c");
	    Position<String> d = new SimplePosition<String>("d");
        tree.setRoot(a);
        tree.insert(a, b);
        tree.insert(b, c);
        tree.insert(b, d);
        
        assertEquals(1, tree.numChildren(a));
        assertEquals(2, tree.numChildren(b));
        assertEquals(0, tree.numChildren(c));
        assertEquals(0, tree.numChildren(d));
    }

}
