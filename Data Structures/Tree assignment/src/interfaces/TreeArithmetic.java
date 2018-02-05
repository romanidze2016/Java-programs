package interfaces;

public interface TreeArithmetic {

	/**
	 * PART 4
	 * 
	 * Implement an Arithmetic Expression display and evaluator.
	 * 
	 * For all methods except isArithmetic, you may assume that the input is
	 * valid arithmetic
	 */
	
	public boolean isArithmetic();
	// is this tree a valid arithmetic tree
	// every leaf in an arithmetic tree is a numeric value, for example: "1", "2.5", or "-0.234"
	// every internal node is a binary operation: "+", "-", "*", "/"
	// binary operations must act on exactly two sub-expressions (i.e. two children)
	// note: all the values and operations are stored as String objects
	
	public double evaluateArithmetic();
	// evaluate an arithmetic tree to get the solution
	// if a position is a numeric value, then it has that value
	// if a position is a binary operation, then apply that operation on the value of its children
	// use floating point maths for all calculations, not integer maths
	// if a position contains "/", its left subtree evaluated to 1.0, and the right to 2.0, then it is 0.5

	public String getArithmeticString();
	// Output a String showing the expression in normal (infix) mathematical notation 
	// For example: "(1+2)+3"
	// You must put brackets around every binary expression
	// You may omit the brackets from the outermost binary expression

}
