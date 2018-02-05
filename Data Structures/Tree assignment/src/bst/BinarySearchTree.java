package bst;

public interface BinarySearchTree<E extends Comparable<E>> {

	public int size();
	public boolean isEmpty();

	//methods for navigating the tree
	public BstPosition<E> root();
	public BstPosition<E> parent(BstPosition<E> position);
	public BstPosition<E> left(BstPosition<E> position);
	public BstPosition<E> right(BstPosition<E> position);

	//methods for modifying the tree
	public boolean insert(E value);
	public boolean remove(E value);

	public void setRoot(BstPosition<E> root);
	public void setParent(BstPosition<E> position);
	public void setLeft(BstPosition<E> position);
	public void setRight(BstPosition<E> position);
	
	//does the tree contain the specified value
	public boolean contains(E value);

}