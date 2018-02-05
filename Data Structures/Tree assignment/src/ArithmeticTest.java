import static org.junit.Assert.*;

import org.junit.Test;

import simpletree.SimplePosition;

public class ArithmeticTest {

	@Test (timeout=1000)
	public void testIsArithmetic() {
		
		MyTree<String> tree = new MyTree<String>();
		
		assertFalse(tree.isArithmetic());

		tree.setRoot(new SimplePosition<String>("+"));
		assertFalse(tree.isArithmetic());
		
		tree.insert(tree.root(), new SimplePosition<String>("1"));
		assertFalse(tree.isArithmetic());

		tree.insert(tree.root(), new SimplePosition<String>("1.2"));
		assertTrue(tree.isArithmetic());
		
	}

	@Test (timeout=1000)
	public void testEvaluateArithmetic() {

		MyTree<String> tree;
		
		// test 1 + 1.2 = 1.2
		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("+"));
		tree.insert(tree.root(), new SimplePosition<String>("1"));
		tree.insert(tree.root(), new SimplePosition<String>("1.2"));
		assertEquals(2.2, tree.evaluateArithmetic(), 0.00001);
		
		// test 2.5 = 2.5
		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("2.5"));
		assertEquals(2.5, tree.evaluateArithmetic(), 0.00001);

		// test 1+(1+1) = 3
		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("+"));
		tree.insert(tree.root(), new SimplePosition<String>("1"));
		SimplePosition<String> position = new SimplePosition<String>("+");
		tree.insert(tree.root(), position);
		tree.insert(position, new SimplePosition<String>("1"));
		tree.insert(position, new SimplePosition<String>("1"));
		assertEquals(3.0, tree.evaluateArithmetic(), 0.00001);
		
	}

	@Test (timeout=1000)
	public void testGetArithmeticString() {

		MyTree<String> tree;
		String arithmeticString;
		
		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("+"));
		tree.insert(tree.root(), new SimplePosition<String>("1"));
		tree.insert(tree.root(), new SimplePosition<String>("1.2"));
		arithmeticString = tree.getArithmeticString();
		//the outermost brackets are optional, both these cases will be accepted:
		assertTrue(arithmeticString.equals("1+1.2") || arithmeticString.equals("(1+1.2)"));

		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("2.5"));
		arithmeticString = tree.getArithmeticString();
		//the outermost brackets are optional, both these cases will be accepted:
		assertTrue(arithmeticString.equals("2.5") || arithmeticString.equals("(2.5)"));

		tree = new MyTree<String>();
		tree.setRoot(new SimplePosition<String>("+"));
		tree.insert(tree.root(), new SimplePosition<String>("1"));
		SimplePosition<String> position = new SimplePosition<String>("+");
		tree.insert(tree.root(), position);
		tree.insert(position, new SimplePosition<String>("1"));
		tree.insert(position, new SimplePosition<String>("1"));
		arithmeticString = tree.getArithmeticString();
		//the outermost brackets are optional, both these cases will be accepted:
		assertTrue(arithmeticString.equals("1+(1+1)") || arithmeticString.equals("(1+(1+1))"));
		
	}

}
