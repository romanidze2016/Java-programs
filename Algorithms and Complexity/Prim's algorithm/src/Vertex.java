import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	private int index;
	private boolean belongsToA;
	private boolean visited;
	private List<Integer> edges;
	private List<Float> weights;
	
	public Vertex(int index) {
		this.index = index;
		belongsToA = false;
		edges = new ArrayList<Integer>();
		weights = new ArrayList<Float>();
		visited = true;
	}
	
	public void visit() {
		visited = true;
	}
	
	public void unVisit() {
		visited = false;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setEdge(int v, float weight) {
		edges.add(v);
		weights.add(weight);
	}
	
	public void assignToA() {
		belongsToA = true;
	}
	
	public List<Integer> getEdges() {
		return edges;
	}
	
	public List<Float> getWeights() {
		return weights;
	}
	
	public boolean isInA() {
		return belongsToA;
	}
	
	public int getIndex() {
		return index;
	}
}
