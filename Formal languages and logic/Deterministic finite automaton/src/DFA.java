import java.util.List;

public class DFA {
	private State[] states;
	private String[] alphabet;
	private State startState;
	
	public DFA(List<String> input) {
		
		String[] listOfStates = input.get(0).split(",");
		states = new State[listOfStates.length];
		alphabet = input.get(1).split(",");
		String[] finalStates = input.get(3).split(",");
		
		//initialize all the states
		for(int i = 0; i < listOfStates.length; i++) {
			states[i] = new State(listOfStates[i], input.get(i + 4).split(","));
		}
		
		//make all final states acceptable
		for (int i = 0; i < finalStates.length; i++) {
			getState(finalStates[i]).setAccepted();
		}
		
		startState = getState(input.get(2));
	}
	
	public boolean processString(String word) {
		
		if (!isValid(word)) {
			return false;
		}
		
		State currentState = startState;
		
		for (int counter = 0; counter < word.length(); counter++) {
			
			char currentChar = word.charAt(counter);
			
			System.out.print(word.substring(0, counter));
			for (int i = counter; i < word.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(currentState.getName() + " -- " + currentChar + " --> ");
			
			//get the name of the next state and make that state current
			String nextState = currentState.getTransition(getCharacterIndex(currentChar));
			currentState = getState(nextState);
			
			System.out.println(currentState.getName() + " " + word.substring(counter + 1, word.length()));
		}
		
		if (currentState.isAccepted()) {
			System.out.println("accepted");
		}
		else {
			System.out.println("rejected");
		}
		
		return true;
	}
	
	//returns a state object which corresponds to the passed name
	public State getState(String name) {
		for (int counter = 0; counter < states.length; counter++) {
			if (states[counter].getName().equals(name)) {
				return states[counter];
			}
		}
		
		return null;
	}
	
	//returns an index that corresponds to a character in the alphabet
	public int getCharacterIndex(char c) {
		for (int counter = 0; counter < alphabet.length; counter++) {
			char tmp = alphabet[counter].charAt(0);
			if (tmp == c) {
				return counter;
			}
		}
		
		return -1;
	}
	
	//checks if the string that is to be processed is a valid string
	public boolean isValid(String word) {
		for (int counter = 0; counter < word.length(); counter++) {
			if (getCharacterIndex(word.charAt(counter)) == -1) {
				return false;
			}
		}
		return true;
	}
}
