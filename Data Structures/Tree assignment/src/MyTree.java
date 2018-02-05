import java.util.ArrayList;
import java.util.List;

import interfaces.Position;
import interfaces.Tree;
import interfaces.TreeArithmetic;
import interfaces.TreeProperties;
import interfaces.TreeTraversals;
import simpletree.SimpleTree;


/**
 * @rpaz2539 put your unikey here
 * @author and your partner's unikey, if working in a pair
 * 
 * This class, MyTree, should be your solution to the assignment
 * It should remain in the (default package)
 * 
 * Implement as many of the required methods as you can.
 */

public class MyTree<E extends Comparable<E>> extends SimpleTree<E> implements
				TreeTraversals<E>,      //PART 1
				TreeProperties,         //PART 2
				Comparable<Tree<E>>,    //PART 3 (only if enrolled in INFO1105)
				TreeArithmetic          //PART 4
{

	//constructor
	public MyTree() {
		super(); //call the constructor of SimpleTree with no arguments
	}

	@Override
	public int compareTo(Tree<E> other) {
		//TODO: implement this method if enrolled in INFO1105
		// compare the tree with another tree
		// check the structure and values of the trees:
		// check the positions left-to-right, top to bottom (i.e. root, then depth 1, then depth 2, etc.)
		// - If this tree has a position that the other tree does not, return 1.
		// - If the other tree has a position that this one does not, return -1.
		// - If the position is in both trees, then compare the values (if they are different, return the difference)
		// If the two trees are identical, return 0
		return 0;
	}

	@Override
	public boolean isArithmetic() {
		return isArithmetic((Position<String>) this.root());
	}
	
	public boolean isArithmetic(Position<String> node) {
		if (node == null) {
			return false;
		}
		
		//If node does not have any children, it means that it is an 
		//external node and the value of this node should be a floating point number 
		if (node.getChildren().isEmpty()) {
			try {
				double num = Double.parseDouble(node.getElement());
			}
			catch (NumberFormatException nfe) {
				return false;
			}
			//If an error occurs while converting the value stored in the node
			//the method will return false. If conversion is successful, it returns true
			return true;
		}
		//The program checks if the given node is a valid arithmetic operation. This means
		//it has exactly two children and it belongs to the following group of operations: +, -, *, /.
		//Then if the node is a valid operation, the program calls isArithmetic method to check 
		//its subtrees for validity
		else if (node.getChildren().size() == 2) {
			String operation = node.getElement();
			if (!(operation.equals("+") || operation.equals("-") || operation.equals("/") || operation.equals("*"))) {
				return false;
			}
			else {
				return isArithmetic(node.getChildren().get(0)) && isArithmetic(node.getChildren().get(1));
			}
		}
		//If a node has 1 or more than two children, the tree it belongs to is 
		//not arithmetic and therefore false is returned
		else {
			return false;
		}
	}

	@Override
	public double evaluateArithmetic() {
		return evaluateArithmetic((Position<String>) this.root());
	}
	
	public double evaluateArithmetic(Position<String> node) {
		//If a node has no children it is a number, otherwise it is an arithmetic operation
		if (node.getChildren().isEmpty()) {
			//returns a converted double for later calculations
			return Double.parseDouble(node.getElement());
		}
		else {
			//According to the received operation, appropriate action is performed.
			//The method also calls itself on the node's subtrees to obtain the values needed for further 
			//calculations
			if (node.getElement().equals("+")) {
				return evaluateArithmetic(node.getChildren().get(0)) + evaluateArithmetic(node.getChildren().get(1));
			}
			else if (node.getElement().equals("-")) {
				return evaluateArithmetic(node.getChildren().get(0)) - evaluateArithmetic(node.getChildren().get(1));
			}
			else if (node.getElement().equals("*")) {
				return evaluateArithmetic(node.getChildren().get(0)) * evaluateArithmetic(node.getChildren().get(1));
			}
			else {
				return evaluateArithmetic(node.getChildren().get(0)) / evaluateArithmetic(node.getChildren().get(1));
			}
		}
	}

	@Override
	public String getArithmeticString() {
		return getArithmeticString((Position<String>) this.root());
	}

	public String getArithmeticString(Position<String> node) {
		if (node.getChildren().isEmpty()) {
			return node.getElement();
		}
		else {
			String firstArithmetic;
			String secondArithmetic;
			
			//If node's first child does not have children it means that it is a number and 
			//brackets should not be added to it
			if (node.getChildren().get(0).getChildren().isEmpty()) {
				firstArithmetic = getArithmeticString(node.getChildren().get(0));
			}
			//Brackets are added if the node's first child is an operation and the method is called on it 
			//to receive its arithmetic string
			else {
				firstArithmetic = "(" + getArithmeticString(node.getChildren().get(0)) + ")";
			}
			
			//Same thing is done here only with the second child
			if (node.getChildren().get(1).getChildren().isEmpty()) {
				secondArithmetic = getArithmeticString(node.getChildren().get(1));
			}
			else {
				secondArithmetic = "(" + getArithmeticString(node.getChildren().get(1)) + ")";
			}
			
			return firstArithmetic + node.getElement() + secondArithmetic;
		}
	}
	
	@Override
	public int height() {
		if (this.root() == null) {
			return -1;
		}
		else {
			return height(this.root());
		}
	}
	
	public int height(Position<E> node) {
		if (node.getChildren().isEmpty()) {
			return 0;
		}
		
		int max = 0;
		
		//The method calculates the height of the node's each subtree and then
		//picks the largest of them and adds it to the height of the whole tree
		for (Position<E> child : node.getChildren()) {
			int childHeight = height(child);
			if (childHeight > max) {
				max = childHeight;
			}
		}
		return 1 + max;
	}

	@Override
	public int height(int maxDepth) {
		if (this.root() == null) {
			return -1;
		}
		else {
			return height(0, maxDepth, this.root());
		}
	}
	
	public int height(int nodeDepth, int maxDepth, Position<E> node) {
		//The difference between this method and the previous one is that
		//it does not calculate the height of a tree beyond a specified depth.
		if (node.getChildren().isEmpty() || nodeDepth >= maxDepth) {
			return 0;
		}
		
		int max = 0;
		for (Position<E> child : node.getChildren()) {
			if (height(nodeDepth + 1, maxDepth, child) > max) {
				max = height(nodeDepth + 1, maxDepth, child);
			}
		}
		return 1 + max;
	}

	@Override
	public int numLeaves() {
		return numLeaves(this.root());
	}
	
	public int numLeaves(Position<E> node) {
		//Tree is empty
		if (node == null) {
			return 0;
		}
		//Node is a leaf, so return one
		else if (node.getChildren().isEmpty()) {
			return 1;
		}
		//Node is an internal node, hence, the method calls itself  
		//on node's children to calculate the number of leaves that are deeper in the tree
		else {
			int total = 0;
			for (Position<E> child : node.getChildren()) {
				total += numLeaves(child);
			}
			return total;
		}
	}

	@Override
	public int numLeaves(int depth) {
		return numLeaves(0, depth, this.root());
	}
	
	public int numLeaves(int nodeDepth, int depth, Position<E> node) {
		//Tree is empty
		if (node == null) {
			return 0;
		}
		//Node is a leaf which is positioned at the specified depth, therefore, 1 is returned
		else if (node.getChildren().isEmpty() && nodeDepth == depth) {
			return 1;
		}
		//Node is an internal node whose depth is lower than the specified one, so the method goes further 
		//down the tree via its children
		else if (!node.getChildren().isEmpty() && nodeDepth < depth) {
			int total = 0;
			for (Position<E> child : node.getChildren()) {
				total += numLeaves(nodeDepth + 1, depth, child);
			}
			return total;
		}
		//All other cases
		else {
			return 0;
		}
	}

	@Override
	public int numPositions(int depth) {
		return numPositions(0, depth, this.root());
	}
	
	public int numPositions(int nodeDepth, int depth, Position<E> node) {
		int total = 0;
		//Tree is empty
		if (node == null) {
			return total;
		}
		//Node is at the specified depth, return 1
		else if (nodeDepth == depth){
			total = 1;
		}
		//If a given node is not external and its depth is less then the specified one, go deeper 
		//down the tree
		else if (nodeDepth < depth && !node.getChildren().isEmpty()) {
			for (Position<E> child : node.getChildren()) {
				total += numPositions(nodeDepth + 1, depth, child);
			}
		}
		return total;
	}

	@Override
	public boolean isBinary() {
		return isBinary(this.root());
	}
	
	public boolean isBinary(Position<E> node) {
		//Tree is empty or node is an external node, return true
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		//In a binary tree a node can not have more than 2 children, therefore, return false
		else if (node.getChildren().size() > 2) {
			return false;
		}
		//Check if the internal node's subtrees satisfy the Binary tree condition
		else {
			boolean binary = true;
			for (Position<E> child : node.getChildren()) {
				binary = binary && isBinary(child);
			}
			return binary;
		}
	}

	@Override
	public boolean isProperBinary() {
		return isProperBinary(this.root());
	}
	
	private boolean isProperBinary(Position<E> node) {
		//Tree is empty
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		//The number of node's children does not violate the condition of a proper tree,
		//therefore, the method is called on those children to check deeper subtrees
		else if(node.getChildren().size() == 2){
			return isProperBinary(node.getChildren().get(0))&&isProperBinary(node.getChildren().get(1)); 
		}
		//Node has either 1 or more than 2 children. This violates 
		//the proper binary tree condition, return false
		else {
			return false;
		}
	}

	@Override
	public boolean isCompleteBinary() {
		if (this.height() < 2) {
			return true;
		}
		//The next upper level of the tree after the last one is not full, hence, return false
		else if (this.numPositions(this.height() - 1) != Math.pow(2, this.height() - 1) || !this.isBinary()) {
			return false;
		}
		//This part of the code checks if the last level of the tree is filled from left to 
		//right and there are no gaps.
		else {
			//First, the program gets a list of nodes at the level which is before the last one.
			List<Position<E>> listOfNodes = getNodesAtDepth(0, this.height() - 1, this.root());
			
			//If a node has at least one child, the node before that should have two children, otherwise
			//the tree is not complete and the program returns false.
			for (int counter = 1; counter < listOfNodes.size(); counter++) {
				int currentNodeSize = listOfNodes.get(counter).getChildren().size();
				int previousNodeSize = listOfNodes.get(counter - 1).getChildren().size();
				if (currentNodeSize != 0 && previousNodeSize != 2) {
					return false;
				}
			}
			return true;
		}
	}
	
	//This method returns a list of nodes which are positioned at the specified depth.
	public List<Position<E>> getNodesAtDepth(int currentDepth, int depth, Position<E> node) {
		List<Position<E>> list = new ArrayList<Position<E>>();
		if (currentDepth == depth) {
			list.add(node);
			return list;
		}
		else {
			list.addAll(getNodesAtDepth(currentDepth + 1, depth, node.getChildren().get(0)));
			list.addAll(getNodesAtDepth(currentDepth + 1, depth, node.getChildren().get(1)));
			return list;
		}
	}
	
	@Override
	public boolean isBalancedBinary() {
		if (!this.isBinary()) {
			return false;
		}
		else {
			return isBalancedBinary(this.root());
		}
	}
	
	public boolean isBalancedBinary(Position<E> node) {
		//Tree is empty or node is external
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		//Node has two children 
		else if (node.getChildren().size() == 2) {
			int leftHeight = height(node.getChildren().get(0));
			int rightHeight = height(node.getChildren().get(1));
			int difference = leftHeight - rightHeight;
			
			//If the difference between the heights of its children is more than one,
			//the tree is not balanced. 
			if (difference != 0 && difference != 1 && difference != -1) {
				return false;
			}
			//Otherwise it checks if the subtrees of that node are balanced
			else {
				return isBalancedBinary(node.getChildren().get(0)) && isBalancedBinary(node.getChildren().get(1));
			}
		}
		//Node has one child, which means that if that child has at least one child
		//the tree is not balanced
		else if (node.getChildren().size() == 1){
			if (height(node.getChildren().get(0)) > 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isHeap(boolean min) {
		if (!this.isCompleteBinary()) {
			return false;
		}
		else if (min) {
			return isMinHeap(this.root());
		}
		else {
			return isMaxHeap(this.root());
		}
	}
	
	public boolean isMinHeap(Position<E> node) {
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		else if (node.getChildren().size() == 1) {
			//Returns false, if node is larger than its left child. Otherwise the method goes further down the tree
			if (node.getElement().compareTo(node.getChildren().get(0).getElement()) > 0) {
				return false;
			}
			else {
				return isMinHeap(node.getChildren().get(0));
			}
		}
		else {
			//Same as above, but only with two children
			if (node.getElement().compareTo(node.getChildren().get(0).getElement()) > 0 || node.getElement().compareTo(node.getChildren().get(1).getElement()) > 0) {
				return false;
			}
			else {
				return isMinHeap(node.getChildren().get(0)) && isMinHeap(node.getChildren().get(1));
			}
		}
	}

	//This method does the opposite of isMeanHeap method
	public boolean isMaxHeap(Position<E> node) {
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		else if (node.getChildren().size() == 1) {
			if (node.getElement().compareTo(node.getChildren().get(0).getElement()) < 0) {
				return false;
			}
			else {
				return isMaxHeap(node.getChildren().get(0));
			}
		}
		else {
			if (node.getElement().compareTo(node.getChildren().get(0).getElement()) < 0 || node.getElement().compareTo(node.getChildren().get(1).getElement()) < 0) {
				return false;
			}
			else {
				return isMaxHeap(node.getChildren().get(0)) && isMaxHeap(node.getChildren().get(1));
			}
		}
	}
	
	@Override
	public boolean isBinarySearchTree() {
		return isBinarySearchTree(this.root());
	}
	
	public boolean isBinarySearchTree(Position<E> node) {
		//Tree is empty or node is external
		if (node == null || node.getChildren().isEmpty()) {
			return true;
		}
		//The method checks if node's left child is less than that node itself and 
		//goes further down the tree.
		else if (node.getChildren().size() == 1) {
			if (node.getChildren().get(0).getElement().compareTo(node.getElement()) > 0) {
				return false;
			}
			else {
				return isBinarySearchTree(node.getChildren().get(0));
			}
		}
		//The method checks if node's left child is smaller and its right child is larger than 
		//that node. Afterwards calls itself to check node's subtrees.
		else if (node.getChildren().size() == 2) {
			if (node.getChildren().get(0).getElement().compareTo(node.getElement()) > 0 || node.getChildren().get(1).getElement().compareTo(node.getElement()) < 0) {
				return false;
			}
			else {
				return isBinarySearchTree(node.getChildren().get(0)) && isBinarySearchTree(node.getChildren().get(1));
			}
		}
		//A node in a binary search tree can not have more than two children.
		else {
			return false;
		}
	}

	@Override
	public List<E> preOrder() {
		return preOrder(this.root());
	}

	public List<E> preOrder(Position<E> parent) {
		List<E> values = new ArrayList<E>();
		//Tree is empty, return an empty list;
		if (parent == null) {
			return values;
		}
		
		//First, parent is added to the list. Afterwards the method 
		//adds preOrder lists of its subtrees one by one.
		values.add(parent.getElement());
		if (!parent.getChildren().isEmpty()) {
			for (Position<E> child : parent.getChildren()) {
				values.addAll(preOrder(child));
			}
		}
		return values;
	}

	@Override
	public List<E> postOrder() {
		return postOrder(this.root());
	}
	
	public List<E> postOrder(Position<E> parent) {
		//Same trick as in the method above except for 
		//parent being added at the end.
		List<E> values = new ArrayList<E>();
		if (parent == null) {
			return values;
		}
		
		if (!parent.getChildren().isEmpty()) {
			for (Position<E> child : parent.getChildren()) {
				values.addAll(postOrder(child));
			}
		}
		values.add(parent.getElement());
		return values;
	}

	@Override
	public List<E> inOrder() {
		if (!isProperBinary()) {
			throw new UnsupportedOperationException();
		}
		return inOrder(this.root());
	}
	
	private List<E> inOrder(Position<E> parent) {
		//This method does almost the same thing as the previous two.
		//Instead, it adds parent between its left and right subtrees' inOrder lists.
		List<E> values = new ArrayList<E>();
		if (parent == null) {
			return values;
		}
		List<Position<E>> children = parent.getChildren();
		if (children.isEmpty()) {
			values.add(parent.getElement());
			return values;
		}
		else {
			values.addAll(inOrder(children.get(0)));
			values.add(parent.getElement());
			values.addAll(inOrder(children.get(1)));
			return values;
		}
	}
}
