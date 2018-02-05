package interfaces;
import java.util.List;

public interface Tree<E> {

	public int size();
	public boolean isEmpty();

	public Position<E> root();
	public Position<E> parent(Position<E> position);

	public List<Position<E>> children(Position<E> position);
	public int numChildren(Position<E> position);

	// some additional methods (not specified in the original tree ADT)
	// which provide some ways to modify the tree
	public void setRoot(Position<E> root);
	public void insert(Position<E> parent, Position<E> child);
	public void remove(Position<E> position);

}