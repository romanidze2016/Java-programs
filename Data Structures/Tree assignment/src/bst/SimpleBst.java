package bst;

/**
 * @author jgod5665
 *
 * This package is for the INFO1905 assignment only
 * INFO1105 students can ignore this package completely.
 *
 * The SimpleBst class is provided as base code.
 * You should not need to modify this class
 * 
 * Your balanced binary tree should be implemented as MyBalancedBst.java, in
 * this bst package (skeleton code is provided,) which should extend this class
 * and implement the insert and remove methods.
 */

public class SimpleBst<E extends Comparable<E>> implements BinarySearchTree<E> {
	
	private BstPosition<E> root;

	public SimpleBst() {
		root = null;
	}
	
	@Override
	public int size() {
		if(root == null) {
			return 0;
		}
		return size(root);
	}

	public int size(BstPosition<E> root) {
		int size = 1;
		if(root.getLeft() != null) {
			size += size(root.getLeft());
		}
		if(root.getRight() != null) {
			size += size(root.getRight());
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (root == null);
	}

	@Override
	public BstPosition<E> root() {
		return root;
	}

	@Override
	public BstPosition<E> parent(BstPosition<E> position) {
		return position.getParent();
	}

	@Override
	public BstPosition<E> left(BstPosition<E> position) {
		return position.getLeft();
	}

	@Override
	public BstPosition<E> right(BstPosition<E> position) {
		return position.getRight();
	}

	@Override
	public void setRoot(BstPosition<E> root) {
		this.root = root;
	}

	@Override
	public void setParent(BstPosition<E> position) {
		position.setParent(position);
	}

	@Override
	public void setLeft(BstPosition<E> position) {
		position.setLeft(position);
	}

	@Override
	public void setRight(BstPosition<E> position) {
		position.setRight(position);
	}

	@Override
	public boolean contains(E value) {
		BstPosition<E> current = root;
		while(current != null) {
			int comparison = value.compareTo(current.getElement());
			if(comparison < 0) {
				current = current.getLeft();
			} else if(comparison > 0) {
				current.getRight();
			} else {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean insert(E value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(E value) {
		throw new UnsupportedOperationException();
	}

}
