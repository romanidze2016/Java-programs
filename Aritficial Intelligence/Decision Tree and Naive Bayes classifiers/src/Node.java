import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node<T> implements Iterable<Node<T>> {
	Node<T> parent;
	boolean leaf=false;
	boolean status=false;
	T data;
	List<Node<T>> children;

	public Node(T rootdata){
		data=rootdata;
		children= new ArrayList<Node<T>>();

	}
	public Node<T> addChild(T child) {
		Node<T> childNode = new Node<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
	@Override
	public Iterator<Node<T>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
