import java.util.ArrayList;

public class Node {
	ArrayList<Node> children;
	Node parent;
	State myState;
	int depth;
	char player;
	int value = -1;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	
	public Node(Node parent, int depth, int maxDepth, State s, char player) {
		this.parent = parent;
		myState = s;
		this.depth = depth;
		this.player = player;
		children = new ArrayList<Node>();
		
		if (utility()) {
			alpha = value;
			beta = value;
			return;
		}
		else if (depth == maxDepth) {
			evaluation();
			alpha = value;
			beta = value;
			return;
		}
		else if (player == 'y') {
			player = 'r';
		}
		else {
			player = 'y';
		}
		
		for (int j = 0; j < 7; j++) {
			if (!myState.isColumnFull(j)) {
				Node child = new Node(this, depth + 1, maxDepth, new State(myState, j, this.player), player);
				children.add(child);
			}
		}
	}
	
	public boolean utility() {
		if (myState.winner == 'r') {
			value = 10000;
			return true;
		}
		else if (myState.winner == 'y') {
			value = -10000;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void evaluation() {
		if (value != -1) {
			return;
		}
		
		myState.calculate();
		value = score('r') - score('y');
	} 
	
	public int score(char player) {
		if (player == 'r') {
			return (myState.redTokens + 10*myState.r_two + 100*myState.r_three + 1000*myState.r_four);
		}
		else {
			return (myState.yellowTokens + 10*myState.y_two + 100*myState.y_three + 1000*myState.y_four);
		}
	}
}
