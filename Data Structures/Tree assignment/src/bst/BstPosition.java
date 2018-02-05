package bst;

public interface BstPosition<E extends Comparable<E>> {

	public E getElement();

	public BstPosition<E> getParent();
	public BstPosition<E> getLeft();
	public BstPosition<E> getRight();

	public void setParent(BstPosition<E> parent);
	public void setLeft(BstPosition<E> left);
	public void setRight(BstPosition<E> right);

}