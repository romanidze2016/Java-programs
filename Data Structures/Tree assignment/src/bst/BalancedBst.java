package bst;
/**
 * This section is for INFO1905 students only.
 * 
 * @rpaz2539 put your unikey here
 * @author and your partner's unikey, if working in a pair
 * 
 * This class, BalanacedBst, should be your solution to part
 * 3 of the INFO1905 assignment.
 * 
 * It should remain in the "bst" package.
 */

public class BalancedBst<E extends Comparable<E>> extends SimpleBst<E>
{

	//constructor
	public BalancedBst() {
		super(); //call the constructor of SimpleTree with no arguments
	}

	/**
	 * PART 3 (INFO1905)
	 * 
	 * If you are enrolled in INFO1905, implement the following methods, which
	 * allow balanced insertion and deletion to a binary search tree. You may
	 * assume that the tree is a balanced binary search tree before either of
	 * these methods are called.
	 * 
	 */
	
	//Both insert and remove methods work as they are supposed to, however, the only problem 
	//I had is implementing the self-balancing of a tree. I could not figure out how to make
	//a tree balance itself.
	@Override
	public boolean insert(E value) {
		//Tree is empty. Set value as the root
		if (this.root() == null) {
			this.setRoot(new SimpleBstPosition<E>(value));
			return true;
		}
		
		//Here the program is looking for the right spot to insert the given value.
		//It starts from the tree's root and then goes further down the tree.
		BstPosition<E> current = this.root();
		while (true) {
			//If the value is smaller than current node and that node does not have a left child,
			//the value becomes its left child. Otherwise the program repeats the loop on current node's left child
			if (value.compareTo(current.getElement()) < 0) {
				if (current.getLeft() != null) {
					current = current.getLeft();
				}
				else {
					BstPosition<E> newNode = new SimpleBstPosition<E>(value);
					current.setLeft(newNode);
					newNode.setParent(current);
					return true;
				}
			}
			//This part does the opposite. If the value is larger than current node and the node does not 
			//have a right child, the value becomes its right child. Otherwise the program loops on current node's right child.
			else if (value.compareTo(current.getElement()) > 0) {
				if (current.getRight() != null) {
					current = current.getRight();
				}
				else {
					BstPosition<E> newNode = new SimpleBstPosition<E>(value);
					current.setRight(newNode);
					newNode.setParent(current);
					return true;
				}
			}
			//One of the nodes stored in the tree already has the same value.
			//Nothing is inserted and the program returns false.
			else {
				break;
			}
		}
		return false;
	}

	@Override
	public boolean remove(E value) {
		if (this.root() == null) {
			return false;
		}
		
		//Find a node with the same value, otherwise return false
		BstPosition<E> current = this.root();
		while (true) {
			if (current.getElement().compareTo(value) == 0) {
				break;
			}
			else if (current.getElement().compareTo(value) > 0) {
				if (current.getLeft() != null) {
					current = current.getLeft();
				}
				else {
					return false;
				}
			}
			else {
				if (current.getRight() != null) {
					current = current.getRight();
				}
				else {
					return false;
				}
			}
		}
		
		//Current node has no children.
		if (current.getLeft() == null && current.getRight() == null) {
			BstPosition<E> parent = current.getParent();
			//If it also does not have a parent, it means it is a root and the tree's root 
			//needs to be set to null.
			if (parent == null) {
				this.setRoot(null);
			}
			//Current node is a left node. Therefore, its parent's left node is set to null.
			else if (parent.getElement().compareTo(current.getElement()) > 0) {
				parent.setLeft(null);
			}
			//Current node is a right node. Its parent's right child is set to null.
			else {
				parent.setRight(null);
			}
		}
		//Current node's right child is null and left one is not.
		else if (current.getLeft() != null && current.getRight() == null) {
			BstPosition<E> parent = current.getParent();
			//Current node is a root. Since its right child is null, its left child becomes the root.
			if (parent == null) {
				this.setRoot(current.getLeft());
				current.getLeft().setParent(null);
			}
			//Current node is a left child. Its left becomes its parent's left child, instead.
			else if (parent.getElement().compareTo(current.getElement()) > 0) {
				parent.setLeft(current.getLeft());
				current.getLeft().setParent(parent);
			}
			//Current node is a right child. Its left child becomes its parent's right child.
			else {
				parent.setRight(current.getLeft());
				current.getLeft().setParent(parent);
			}
		}
		//Same thing happens here. The only difference is that current node's children are swapped.
		else if (current.getLeft() == null && current.getRight() != null) {
			BstPosition<E> parent = current.getParent();
			if (parent == null) {
				this.setRoot(current.getRight());
				current.getRight().setParent(null);
			}
			else if (parent.getElement().compareTo(current.getElement()) > 0) {
				parent.setLeft(current.getRight());
				current.getRight().setParent(parent);
			}
			else {
				parent.setRight(current.getRight());
				current.getRight().setParent(parent);
			}
		}
		//Current node has two children. 
		else {
			//Get the left most node from current node's right subtree.
			BstPosition<E> newNode = getNextNode(current.getRight());
			
			//The left most node from the right subtree of the current node becomes 
			//the parent of the current node's left child
			newNode.setLeft(current.getLeft());
			current.getLeft().setParent(newNode);
			
			//If current node's right child is not the newNode, which is the left most node of its right
			//subtree, it becomes the right child of the newNode. Otherwise, nothing happens, because when newNode
			//is returned, it is automatically removed from its parent in getNextNode.
			if (current.getRight() != null) {
				newNode.setRight(current.getRight());
				current.getRight().setParent(newNode);
			}
			
			//Current node was a root, so newNode becomes a root instead of the current node
			if (current.getParent() == null) {
				this.setRoot(newNode);
			}
			//Current node is a right child. newNode becomes its parent's right child now
			else if (current.getElement().compareTo(current.getParent().getElement()) > 0) {
				current.getParent().setRight(newNode);
			}
			//Current node is a left child
			else {
				current.getParent().setLeft(newNode);
			}
			newNode.setParent(current.getParent());
		}
		
		
		return true;
	}
	
	//This method finds the left most node in the tree whose root is passed when the method is called
	//from the insert method. Then it returns this node.
	public BstPosition<E> getNextNode(BstPosition<E> node) {
		//Node is the left most node
		if (node.getLeft() == null) {
			//Node is a right child. Its parent's right child becomes either null
			//or the node's right child
			if (node.getElement().compareTo(node.getParent().getElement()) > 0) {
				node.getParent().setRight(node.getRight());
				
			}
			//Node is a left child. Node's right child takes its place no matter if it is null or not
			else {
				node.getParent().setLeft(node.getRight());
			}
			
			//If node has a right child the method links it to a new parent which is the node's parent
			if (node.getRight() != null) {
				node.getRight().setParent(node.getParent());
				node.setRight(null);
			}
			node.setParent(null);
			return node;
		}
		//Node is not the left most node, hence, go deeper down to the left.
		else {
			return getNextNode(node.getLeft());
		}
	}
}
	

	