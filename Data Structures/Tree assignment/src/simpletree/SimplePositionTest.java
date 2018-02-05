package simpletree;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import interfaces.Position;

public class SimplePositionTest {
	
	/*
	 * These tests have timeouts set to 1000 milliseconds (1 second)
	 * While using the debugger you will need to comment out the
	 * corresponding timeout
	 */

	//test to make sure that everything is initialised correctly for a new object
	@Test (timeout = 1000)
	public void testConstruction() {
		Position<String> position = new SimplePosition<String>("I'm a tree");
        assertEquals(null, position.getParent());
        assertEquals(new ArrayList<Position<Integer>>(), position.getChildren());
        assertEquals("I'm a tree", position.getElement());
	}
	
	//test getting and setting the parent of a position
	@Test (timeout = 1000)
	public void testParent() {
		Position<String> positionA = new SimplePosition<String>("a");
		Position<String> positionB = new SimplePosition<String>("b");

		//new Positions should not have parents yet
		assertNull(positionA.getParent());
		assertNull(positionB.getParent());
		
		//make sure that setting B as the parent of A works correctly
		positionA.setParent(positionB);
		assertSame(positionB, positionA.getParent());
		assertNull(positionB.getParent());
	}

	//test getting and setting the element at a position
	@Test (timeout = 1000)
	public void testSetElement() {
		Position<String> position = new SimplePosition<String>("a");

		//test it constructed correctly
		assertEquals("a", position.getElement());
		
		//test is changes correctly
        position.setElement("b");
        assertEquals("b", position.getElement());
	}

	//test adding and removing children
	@Test (timeout = 1000)
	public void testSetChildren() {
	
		//some positions to add/remove
		Position<String> positionA = new SimplePosition<String>("a");
		Position<String> positionB = new SimplePosition<String>("b");
		Position<String> positionC = new SimplePosition<String>("c");
		Position<String> positionD = new SimplePosition<String>("d");

		//lists to compare against
		ArrayList<Position<String>> expectedChildren = new ArrayList<Position<String>>();
		final ArrayList<Position<String>> emptyList = new ArrayList<Position<String>>();
		
		//no children to start with
        assertEquals(expectedChildren, positionA.getChildren());
        
        //add a child to A (B)
        positionA.addChild(positionB);
        expectedChildren.add(positionB);
        assertEquals(expectedChildren, positionA.getChildren());
        
        //make sure this didn't effect B
        assertEquals(emptyList, positionB.getChildren());
        
        //add another child
        positionA.addChild(positionC);
        expectedChildren.add(positionC);
        assertEquals(expectedChildren, positionA.getChildren());

        //add a child to one of the children of A (should not affect A)
        positionB.addChild(positionD);
        assertEquals(expectedChildren, positionA.getChildren());

        //remove a child from A
        positionA.removeChild(positionB);
        expectedChildren.remove(positionB);
        assertEquals(expectedChildren, positionA.getChildren());
	}
	
}
