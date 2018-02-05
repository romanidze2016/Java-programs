import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private Vertex[] vertices;
	private List<Integer> A;
	private int n;
	private int m;
	
	public Graph(int n) {
		this.n = n;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++) {
			vertices[i] = new Vertex(i);
		}
		A = new ArrayList<Integer>();
	}
	
	public void setM(int m) {
		this.m = m;
	}
	
	public void addA(int x) {
		A.add(x);
		vertices[x].assignToA();
	}
	
	public Vertex getVertex(int x) {
		return vertices[x];
	}
	
	public float calculateMST() {
		
		float totalSum = 0;
		
		//determine which edge of each special node will be in MST
		//and calculate the sum of those edges
		for (int i = 0; i < A.size(); i++) {
			int index = A.get(i);
			int v = -1;
			float weight = -1;
			for (int j = 0; j < vertices[index].getEdges().size(); j++) {
				if (!vertices[vertices[index].getEdges().get(j)].isInA() && (v == -1 || vertices[index].getWeights().get(j) < weight)) {
					v = vertices[index].getEdges().get(j);
					weight = vertices[index].getWeights().get(j);
				}
			}
			totalSum += weight;
		}
		
		//arrays that are needed for Prim's algorithm
		List<Integer> queueOfVertices = new ArrayList<Integer>();
		List<Integer> closestNeighbours = new ArrayList<Integer>();
		List<Float> minWeights = new ArrayList<Float>();
		
		//Initialization of those arrays
		//nodes that are in A subset are omitted
		int counter = 0;
		for (int i = 0; i < n; i++) {
			if (!vertices[i].isInA()) {
				queueOfVertices.add(i);
				closestNeighbours.add(-1);
				
				vertices[i].unVisit();
				
				if (counter == 0) {
					minWeights.add((float)0);
				}
				else {
					minWeights.add((float)-1);
				}
				
				counter++;
			}
		}
		
		//Beginning of Prim's algorithm
		while (!queueOfVertices.isEmpty()) {
			
			int currentIndex = -1;
			float currentMinWeight = -1;
			
			//find the node that is the closest to the subset S
			for (int i = 0; i < queueOfVertices.size(); i++) {
				if (currentMinWeight == -1.0 && minWeights.get(i) != -1) {
					currentIndex = i;
					currentMinWeight = minWeights.get(i);
					counter++;
				}
				else if (minWeights.get(i) != -1.0 && minWeights.get(i) < currentMinWeight) {
					currentIndex = i;
					currentMinWeight = minWeights.get(i);
				}
			}
			
			//update values of nodes that are adjacent to the chosen node
			for (int i = 0; i < vertices[queueOfVertices.get(currentIndex)].getEdges().size(); i++) {
				int adjacentIndex = vertices[queueOfVertices.get(currentIndex)].getEdges().get(i);
				if (vertices[adjacentIndex].isInA() || vertices[adjacentIndex].isVisited()) {
					continue;
				}
				else if (queueOfVertices.size() < 2) {
					break;
				}
				
				int temp = queueOfVertices.indexOf(adjacentIndex);
				float adjacentWeight = minWeights.get(temp);
				float newW = vertices[queueOfVertices.get(currentIndex)].getWeights().get(i);
				if (adjacentWeight == -1.0 || newW < adjacentWeight) {
					minWeights.set(temp, newW);
					closestNeighbours.set(temp, queueOfVertices.get(currentIndex));
				} 
			}
			
			//update total sum of edges in MST
			if (closestNeighbours.get(currentIndex) != -1) {
				totalSum +=  minWeights.get(currentIndex);
			}
			
			//remove the chosen node from the queue
			vertices[queueOfVertices.get(currentIndex)].visit();
			queueOfVertices.remove(currentIndex);
			closestNeighbours.remove(currentIndex);
			minWeights.remove(currentIndex);
		}
		
		return totalSum;
	}
	
	public void print() {
		for (int i = 0; i < n; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < vertices[i].getEdges().size(); j++) {
				System.out.print("[" + vertices[i].getEdges().get(j) + ", " + vertices[i].getWeights().get(j) + "] ");
			}
			System.out.println();
		}
	}
}
