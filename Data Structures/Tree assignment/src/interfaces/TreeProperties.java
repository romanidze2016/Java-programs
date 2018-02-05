package interfaces;

public interface TreeProperties {

	/**
	 * PART 2
	 * 
	 * Implement the following methods, which test or output certain properties
	 * that the tree might have.
	 */

	public int height();
	// calculate the height of the tree (the maximum depth of any position in the tree.)
	// a tree with only one position has height 0.
	// a tree where the root has children, but no grandchildren has height 1.
	// a tree where the root has grandchildren, but no great-grandchildren has height 2.
	// we will define an empty tree to have height -1.

	public int height(int maxDepth);
	// calculate the height of the tree, but do not descend deeper than 'depth' edges into the tree
	// do not visit any nodes deeper than maxDepth while calculating this
	// do not call your height() method
	// (some trees are very, very, very big!)

	public int numLeaves();
	// calculate the number of leaves of the tree (positions with no children)

	public int numLeaves(int depth);
	// calculate the number of leaves of the tree at exactly depth depth.
	// the root is at depth 0. The children of the root are at depth 1.

	public int numPositions(int depth);
	// calculate the number of positions at exactly depth depth.

	public boolean isBinary();
	// is the tree a binary tree?
	// every position in a binary tree has no more than 2 children

	public boolean isProperBinary();
	// is the tree a proper binary tree?
	// every position in a proper binary tree has either zero or two children

	public boolean isCompleteBinary();
	// is the tree a complete binary tree?
	// 1) all the levels except the last must be full 
	// 2) all leaves in the last level are filled from left to right (no gaps)

	public boolean isBalancedBinary();
	// is the tree a balanced binary tree?
	// a balanced tree is one where for every position in the tree, the
	// subtrees rooted at each of the children of the that position have
	// heights that differ by no more than one.
	// NOTE: if a node has only one child, the other child is considered to be a subtree of height -1 

	public boolean isHeap(boolean min);
	// is the tree a min-heap (if min is True), or is the tree a max-heap (if min is False)
	// heaps are trees which are both complete and have the heap property:
	// in a min-heap, the value of a node is less than or equal to the value of each of its children
	// similarly, in a max-heap the value of a node is greater than or equal to the value of each child

	public boolean isBinarySearchTree();
	// is the tree a binary search tree?
	// a binary search tree is a binary tree such that for any node with value v:
	// - if there is a left child (child 0 is not null), it contains a value strictly less than v.
	// - if there is a right child (child 1 is not null), it contains a value strictly greater than v.
	// if there is only one child, you may assume that it is a left child.
}
