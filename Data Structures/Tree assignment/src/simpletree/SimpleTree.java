package simpletree;
import java.util.List;

import interfaces.Position;
import interfaces.Tree;

/**
 * @author jgod5665
 *
 * This SimpleTree class is provided as base code.
 * You should not need to modify this class
 * 
 * Your assignment solution should be implemented in MyTree.java in the 
 * (default package), which should extend this class and implement
 * all the required interfaces.
 */

public class SimpleTree<E> implements Tree<E> {
	
	private Position<E> root;
	
	//constructor
	public SimpleTree() {
		this.root = null;
	}
	
	//returns the number of positions in the tree
	@Override
	public int size() {
		//handle edge case: empty tree
		if(root == null) {
			return 0;
		}
		//otherwise return the size of the subtree rooted at the root
		return size(root);
	}
	
	//return the size of the subtree rooted at position
	public int size(Position<E> position) {
		//keep a running total of the size, initially 1 (for the position itself)
		int size = 1;
		//for each child, recursively calculate its size and add it to the total
		for(Position<E> child : position.getChildren()) {
			size += size(child);
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		//the tree is empty if and only if the root is null
		return root == null;
	}

	@Override
	public Position<E> root() {
		return root;
	}

	@Override
	public void setRoot(Position<E> root) {
		this.root = root;
	}

	@Override
	public Position<E> parent(Position<E> position) {
		return position.getParent();
	}

	@Override
	public List<Position<E>> children(Position<E> position) {
		return position.getChildren();
	}

	@Override
	public int numChildren(Position<E> position) {
		return position.getChildren().size();
	}

	//insert the position 'child' under the position 'parent'
	@Override
	public void insert(Position<E> parent, Position<E> child) {
		parent.addChild(child);
		child.setParent(parent);
	}

	//remove the position from the tree
	@Override
	public void remove(Position<E> position) {
		//if we needed to do anything extra when removing each node, such as
		//keeping track of the size of the tree, then we would need to
		//recursively call remove on all the child nodes first, like this:
		//for(Position<E> child : position.getChildren()) {
		//	remove(child);
		//}
		
		//handle edge case: removing the root
		if(position.equals(root)) {
			root = null;
		}
		
		//if the position has a parent, remove it from the parent
		Position<E> parent = position.getParent();
		if(parent != null) {
			parent.removeChild(position);
			position.setParent(null);
		}
	}
}
