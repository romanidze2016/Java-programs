
public class State {
	char[][] grid;
	int redTokens;
	int yellowTokens;
	char winner;
	
	int r_two;
	int r_three;
	int r_four;
	int y_two;
	int y_three;
	int y_four;
	
	public State(String contents) {
		grid = new char[6][7];
		redTokens = 0;
		yellowTokens = 0;
		winner = '.';
		String[] rows = contents.split(",");
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				grid[5 - i][j] = rows[i].charAt(j);
				if (grid[5 - i][j] == 'r') {
					redTokens++;
				}
				else if (grid[5 - i][j] == 'y') {
					yellowTokens++;
				}
			}
		}
	}
	
	public State(State other, int column, char player) {
		grid = new char[6][7];
		for (int i = 0; i < 6; i++) {
			grid[i] = other.grid[i].clone();
		}
		redTokens = other.redTokens;
		yellowTokens = other.yellowTokens;
		
		if (player == 'r') {
			redTokens++;
		}
		else {
			yellowTokens++;
		}
		
		winner = '.';
		int row = 0;
		
		for (int i = 5; i >= 0; i--) {
			if (grid[i][column] == '.') {
				grid[i][column] = player;
				row = i;
				break;
			}
		}
		
		//check whether this move is a winning move
		int counter = 1;
		int currentRow = row + 1;
		int currentColumn = column;
		//check vertically
		while (currentRow < 6) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentRow++;
			}
		}
		
		if (counter >= 4) {
			winner = player;
			return;
		}
		
		//check horizontally
		counter = 1;
		currentRow = row;
		currentColumn = column - 1;
		while (currentColumn >= 0) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn--;
			}
		}
		currentColumn = column + 1;
		while (currentColumn < 7) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn++;
			}
		}
		if (counter >= 4) {
			winner = player;
			return;
		}
		
		//check left diagonal
		counter = 1;
		currentRow = row - 1;
		currentColumn = column - 1;
		while (currentRow >= 0 && currentColumn >= 0) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn--;
				currentRow--;
			}
		}
		currentRow = row + 1;
		currentColumn = column + 1;
		while (currentRow < 6 && currentColumn < 7) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn++;
				currentRow++;
			}
		}
		if (counter >= 4) {
			winner = player;
			return;
		}
		
		//check right diagonal
		counter = 1;
		currentRow = row + 1;
		currentColumn = column - 1;
		while (currentRow < 6 && currentColumn >= 0) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn--;
				currentRow++;
			}
		}
		currentRow = row - 1;
		currentColumn = column + 1;
		while (currentRow >= 0 && currentColumn < 7) {
			if (grid[currentRow][currentColumn] != player) {
				break;
			}
			else {
				counter++;
				currentColumn++;
				currentRow--;
			}
		}
		if (counter >= 4) {
			winner = player;
			return;
		}
	}
	
	public boolean isColumnFull(int column) {
		if (grid[0][column] == '.') {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void calculate() {
		r_two = 0;
		r_three = 0;
		r_four = 0;
		y_two = 0;
		y_three = 0;
		y_four = 0;
		//check horizontal lines
		char currentColor = '.';
		int counter = 0;
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				if (grid[row][column] == currentColor) {
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[row][column];
					counter = 1;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
		
		//check vertical lines
		currentColor = '.';
		counter = 0;
		for (int column = 0; column < 7; column++) {
			for (int row = 0; row < 6; row++) {
				if (grid[row][column] == currentColor) {
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[row][column];
					counter = 1;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
		
		//check left diagonal line
		//first lower half
		for (int row = 4; row >= 0; row--) {
			int currentRow = row;
			int currentColumn = 0;
			while (currentRow < 6 && currentColumn < 7) {
				if (grid[currentRow][currentColumn] == currentColor) {
					currentRow++;
					currentColumn++;
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[currentRow][currentColumn];
					counter = 1;
					currentRow++;
					currentColumn++;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
		//second upper half
		for (int column = 1; column < 6; column++) {
			int currentRow = 0;
			int currentColumn = column;
			while (currentRow < 6 && currentColumn < 7) {
				if (grid[currentRow][currentColumn] == currentColor) {
					currentRow++;
					currentColumn++;
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[currentRow][currentColumn];
					counter = 1;
					currentRow++;
					currentColumn++;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
		
		//check right diagonal line
		//upper half
		for (int column = 1; column < 7; column++) {
			int currentRow = 0;
			int currentColumn = column;
			while (currentRow < 6 && currentColumn >= 0) {
				if (grid[currentRow][currentColumn] == currentColor) {
					currentRow++;
					currentColumn--;
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[currentRow][currentColumn];
					counter = 1;
					currentRow++;
					currentColumn--;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
		//lower half
		for (int row = 1; row < 5; row++) {
			int currentRow = row;
			int currentColumn = 6;
			while (currentRow < 6 && currentColumn >= 0) {
				if (grid[currentRow][currentColumn] == currentColor) {
					currentRow++;
					currentColumn--;
					counter++;
				}
				else {
					updateValues(currentColor, counter);
					currentColor = grid[currentRow][currentColumn];
					counter = 1;
					currentRow++;
					currentColumn--;
				}
			}
			updateValues(currentColor, counter);
			currentColor = '.';
			counter = 0;
		}
	}
	
	public void updateValues(char color, int count) {
		if (color == 'r') {
			if (count == 2) {
				r_two++;
			}
			else if (count == 3) {
				r_three++;
			}
			else if (count == 4) {
				r_four++;
			}
		}
		else if (color == 'y'){
			if (count == 2) {
				y_two++;
			}
			else if (count == 3) {
				y_three++;
			}
			else if (count == 4) {
				y_four++;
			}
		}
	}
	
	public void printState() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
}
