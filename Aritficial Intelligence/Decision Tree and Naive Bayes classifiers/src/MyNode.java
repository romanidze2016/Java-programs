import java.util.ArrayList;
import java.util.HashMap;

public class MyNode {
	
	MyNode parent;
	int depth;
	boolean leaf = false;
	boolean status = false;
	int attribute = -1;
	HashMap<Degree, MyNode> children;
	
	public MyNode(MyNode parent) {
		this.parent = parent;
		if (parent == null) {
			depth = 0;
		}
		else {
			depth = parent.depth + 1;
		}
		children = new HashMap<Degree, MyNode>();
	}
	
	public void addChild(Degree d, MyNode child) {
		children.put(d, child);
	}
	
	public MyNode getChild(Degree d) {
		return children.get(d);
	}
	
	public void setStatus(boolean status) {
		this.status = status;
		leaf = true;
	}
	
	public void setAttribute(int a) {
		attribute = a;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public int getAttribute() {
		return attribute;
	}
	
	public boolean getStatus() {
		return status;
	}
}
