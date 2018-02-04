import java.util.ArrayList;

public class Minimax {
	Node root;
	char player;
	int maxDepth;
	String algorithmUsed;
	
	public Minimax(String startState, String playerString, String pruning, String depth) {
		if (playerString.equals("red")) {
			this.player = 'r';
		}
		else {
			this.player = 'y';
		}
		algorithmUsed = pruning;
		maxDepth = Integer.parseInt(depth);
		State start = new State(startState);
		root = new Node(null, 0, maxDepth, start, player);
	}
	
	public Minimax(String startState, String playerString) {
		if (playerString.equals("red")) {
			this.player = 'r';
		}
		else {
			this.player = 'y';
		}
		algorithmUsed = "A";
		maxDepth = 6;
		State start = new State(startState);
		root = new Node(null, 0, maxDepth, start, player);
	}
	
	public void runMiniMax() {
		if (algorithmUsed.equals("M")) {
			ArrayList<Integer> result = newDFS(root);
			System.out.println(result.get(0));
			//System.out.println(result.get(1));
			//return result;
		}
		else {
			ArrayList<Integer> result = alphaBeta(root);
			System.out.println(result.get(0));
			//System.out.println(result.get(1));
			//return result;
		}
	}
	
	public ArrayList<Integer> alphaBeta(Node n) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int nodesVisited = 1;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int targetDepth = Integer.MAX_VALUE;
		int targetIndex = 0;
		
		if (n.player == 'r') {
			for (int i = 0; i < n.children.size(); i++) {
				ArrayList<Integer> childReturn = min_value(n.children.get(i), alpha, beta, targetDepth);
				nodesVisited += childReturn.get(2);
				if (childReturn.get(0) > alpha) {
					alpha = childReturn.get(0);
					targetIndex = i;
					targetDepth = childReturn.get(1);
				}
				else if (childReturn.get(0) == alpha && childReturn.get(1) < targetDepth) {
					targetIndex = i;
					targetDepth = childReturn.get(1);
				}
			}
		}
		else {
			for (int i = 0; i < n.children.size(); i++) {
				ArrayList<Integer> childReturn = max_value(n.children.get(i), alpha, beta, targetDepth);
				nodesVisited += childReturn.get(2);
				if (childReturn.get(0) < beta) {
					beta = childReturn.get(0);
					targetIndex = i;
					targetDepth = childReturn.get(1);
				}
				else if (childReturn.get(0) == alpha && childReturn.get(1) < targetDepth) {
					targetIndex = i;
					targetDepth = childReturn.get(1);
				}
			}
		}
		
		result.add(targetIndex);
		result.add(nodesVisited);
		return result;
	}
	
	public ArrayList<Integer> max_value(Node n, int alpha, int beta, int depth) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int nodesVisited = 1;
		
		if (n.children.size() == 0) {
			result.add(n.value);
			result.add(n.depth);
		}
		else {
			for (int i = 0; i < n.children.size(); i++) {
				ArrayList<Integer> childReturn = min_value(n.children.get(i), alpha, beta, depth);
				nodesVisited += childReturn.get(2);
				
				if (childReturn.get(0) > alpha) {
					alpha = childReturn.get(0);
					depth = childReturn.get(1);
				}
				else if (childReturn.get(0) == alpha && childReturn.get(1) < depth) {
					depth = childReturn.get(1);
				}
				
				if (beta < alpha || (beta == alpha && depth <= n.depth)) {
					break;
				}
			}
			result.add(alpha);
			result.add(depth);
		}
		
		result.add(nodesVisited);
		return result;
	}
	
	public ArrayList<Integer> min_value(Node n, int alpha, int beta, int depth) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int nodesVisited = 1;
		
		if (n.children.size() == 0) {
			result.add(n.value);
			result.add(n.depth);
		}
		else {
			for (int i = 0; i < n.children.size(); i++) {
				ArrayList<Integer> childReturn = max_value(n.children.get(i), alpha, beta, depth);
				nodesVisited += childReturn.get(2);
				
				if (childReturn.get(0) < beta) {
					beta = childReturn.get(0);
					depth = childReturn.get(1);
				}
				else if (childReturn.get(0) == beta && childReturn.get(1) < depth) {
					depth = childReturn.get(1);
				}
				
				if (beta < alpha || (beta == alpha && depth <= n.depth)) {
					break;
				}
			}
			result.add(beta);
			result.add(depth);
		}
		
		result.add(nodesVisited);
		return result;
	}
	
	public int alphaBetaPruning(Node n) {
		int nodesVisited = 1;
		
		if (n.children.size() == 0) {
			return nodesVisited;
		}
		else {
			if (n.parent == null || (n.player == 'r' && n.parent.beta == Integer.MAX_VALUE) || (n.player == 'y' && n.parent.alpha == Integer.MIN_VALUE)) {
				for (int i = 0; i < n.children.size(); i++) {
					Node child = n.children.get(i);
					
					//here
					if (child.children.size() != 0) {
						child.alpha = n.alpha;
						child.beta = n.beta;
					}
					
					nodesVisited += alphaBetaPruning(child);
					if (n.player == 'r') {
						if (child.beta > n.alpha) {
							n.alpha = child.beta;
							child.myState.printState();
							System.out.println("value = " + child.value);
							if (n.parent == null) {
								System.out.println(n.alpha + " - alpha and index - " + i + " and nodesVisited - " + nodesVisited);
							}
						}
					}
					else {
						if (child.alpha < n.beta) {
							n.beta = child.alpha;
							if (n.parent == null) {
								System.out.println(n.beta + " - beta and index - " + i + " and nodesVisited - " + nodesVisited);
							}
						}
					}
				}
			}
			else {
				//here
				if (n.children.get(0).children.size() != 0) {
					n.children.get(0).alpha = n.alpha;
					n.children.get(0).beta = n.beta;
				}
				
				nodesVisited += alphaBetaPruning(n.children.get(0));
				if (n.player == 'r') {
					n.alpha = n.children.get(0).beta;
				}
				else {
					n.beta = n.children.get(0).alpha;
				}
				
				for (int i = 1; i < n.children.size(); i++) {
					if (n.player == 'r') {
						if (n.alpha >= n.parent.beta) {
							break;
						}
						else {
							Node child = n.children.get(i);
							
							//here
							if (child.children.size() != 0) {
								child.alpha = n.alpha;
								child.beta = n.beta;
							}
							
							nodesVisited += alphaBetaPruning(child);
							if (child.beta > n.alpha) {
								n.alpha = child.beta;
							}
						}
					}
					else {
						if (n.beta <= n.parent.alpha) {
							break;
						}
						else {
							Node child = n.children.get(i);
							
							//here
							if (child.children.size() != 0) {
								child.alpha = n.alpha;
								child.beta = n.beta;
							}
							
							nodesVisited += alphaBetaPruning(child);
							if (child.alpha < n.beta) {
								n.beta = child.alpha;
							}
						}
					}
				}
			}
		}
		
		return nodesVisited;
	}
	
	public ArrayList<Integer> newDFS(Node n) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (n.children.size() == 0) {
			result.add(n.value);
			result.add(1);
			return result;
		}
		else {
			int nodesVisited = 1;
			if (n.player == 'r') {
				int maxValue = Integer.MIN_VALUE;
				int maxIndex = -1;
				for (int i = 0; i < n.children.size(); i++) {
					ArrayList<Integer> childValues = newDFS(n.children.get(i));
					nodesVisited += childValues.get(1);
					if (childValues.get(0) > maxValue) {
						maxValue = childValues.get(0);
						maxIndex = i;
					}
				}
				if (n.parent == null) {
					result.add(maxIndex);
				}
				else {
					result.add(maxValue);
				}
			}
			else {
				int minValue = Integer.MAX_VALUE;
				int minIndex = -1;
				for (int i = 0; i < n.children.size(); i++) {
					ArrayList<Integer> childValues = newDFS(n.children.get(i));
					nodesVisited += childValues.get(1);
					if (childValues.get(0) < minValue) {
						minValue = childValues.get(0);
						minIndex = i;
					}
				}
				if (n.parent == null) {
					result.add(minIndex);
				}
				else {
					result.add(minValue);
				}
			}
			result.add(nodesVisited);
		}
		return result;
	}
	
	public ArrayList<Integer> DFS(Node n) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if (n.parent == null) {
			int nodesVisited = 1;
			if (n.player == 'r') {
				int maxValue = Integer.MIN_VALUE;
				int maxIndex = -1;
				for (int i = 0; i < n.children.size(); i++) {
					ArrayList<Integer> childValues = DFS(n.children.get(i));
					nodesVisited += childValues.get(1);
					if (childValues.get(0) > maxValue) {
						maxValue = childValues.get(0);
						maxIndex = i;
					}
				}
				result.add(maxIndex);
			}
			else {
				int minValue = Integer.MAX_VALUE;
				int minIndex = -1;
				for (int i = 0; i < n.children.size(); i++) {
					ArrayList<Integer> childValues = DFS(n.children.get(i));
					nodesVisited += childValues.get(1);
					if (childValues.get(0) < minValue) {
						minValue = childValues.get(0);
						minIndex = i;
					}
				}
				result.add(minIndex);
			}
			result.add(nodesVisited);
		}
		else {
			if (n.children.size() == 0) {
				result.add(n.value);
				result.add(1);
				return result;
			}
			else {
				int nodesVisited = 1;
				//find MAX (red player's turn)
				if (n.player == 'r') {
					int maxValue = Integer.MIN_VALUE;
					for (int i = 0; i < n.children.size(); i++) {
						ArrayList<Integer> childValues = DFS(n.children.get(i));
						nodesVisited += childValues.get(1);
						if (childValues.get(0) > maxValue) {
							maxValue = childValues.get(0);
						}
					}
					result.add(maxValue);
				}
				//find MIN (yellow player's turn)
				else {
					int minValue = Integer.MAX_VALUE;
					for (int i = 0; i < n.children.size(); i++) {
						ArrayList<Integer> childValues = DFS(n.children.get(i));
						nodesVisited += childValues.get(1);
						if (childValues.get(0) < minValue) {
							minValue = childValues.get(0);
						}
					}
					result.add(minValue);
				}
				result.add(nodesVisited);
			}
		}
		return result;
	}
}
