package bst;

public class SimpleBstPosition<E extends Comparable<E>> implements BstPosition<E> {
	
	private E element;
	BstPosition<E> parent;
	BstPosition<E> left;
	BstPosition<E> right;
	
	public SimpleBstPosition(E element) {
		this.element = element;
		this.parent = null;
		this.left = null;
		this.right = null;
	}

	@Override
	public E getElement() {
		return element;
	}

	@Override
	public BstPosition<E> getParent() {
		return parent;
	}

	@Override
	public BstPosition<E> getLeft() {
		return left;
	}

	@Override
	public BstPosition<E> getRight() {
		return right;
	}

	@Override
	public void setParent(BstPosition<E> parent) {
		this.parent = parent;
	}

	@Override
	public void setLeft(BstPosition<E> left) {
		this.left = left;
	}

	@Override
	public void setRight(BstPosition<E> right) {
		this.right = right;
	}

}
