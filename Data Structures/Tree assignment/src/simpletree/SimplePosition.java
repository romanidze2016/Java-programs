package simpletree;
import java.util.ArrayList;
import java.util.List;

import interfaces.Position;

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

public class SimplePosition<E> implements Position<E> {
	
	private E element; //the value stored in the position
	private Position<E> parent; //the parent position
	private ArrayList<Position<E>> children; //the list of children
	
	//constructor
	public SimplePosition(E element) {
		//initialise the list of children as an empty list
		children = new ArrayList<Position<E>>();
		//initialise the parent as null (unknown)
		parent = null;
		//initialise the stored value with the supplied argument
		this.element = element;
	}

	@Override
	public E getElement() {
		return element;
	}

	@Override
	public void setElement(E element) {
		this.element = element;
	}

	@Override
	public Position<E> getParent() {
		return parent;
	}

	@Override
	public void setParent(Position<E> parent) {
		this.parent = parent;
	}

	@Override
	public List<Position<E>> getChildren() {
		return children;
	}

	@Override
	public void addChild(Position<E> child) {
		children.add(child);
	}

	@Override
	public void removeChild(Position<E> child) {
		children.remove(child);
	}
}
