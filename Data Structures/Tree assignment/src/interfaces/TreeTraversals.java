package interfaces;
import java.util.List;

public interface TreeTraversals<E> {

	/**
	 * PART 1
	 * 
	 * Implement the following methods, which output some useful traversals
	 * of the trees. In all cases, the children of a node should be visited
	 * in the same order in which they appear in the underlying data structure
	 * (do not consider the value contained in the node when deciding the order.)
	 */
	
	public List<E> preOrder();
	// Output the values of a pre-order traversal of the tree

	public List<E> postOrder();
	// Output the values of a post-order traversal of the tree

	public List<E> inOrder();
	// Output the values of a in-order traversal of the tree
	// This operation should only be performed if the tree is a proper binary tree.
	// If it is not, then throw an UnsupportedOperationException instead of returning a value
	// Otherwise, perform the traversal with child 0 on the left, and child 1 on the right.
	
}
