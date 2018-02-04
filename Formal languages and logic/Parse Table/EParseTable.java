import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class EParseTable {
	private char[] alphabet = {'u', 'i', 'd', ';', 'f', 't', 'h', 'e', 'n', 'l', 's', 'w', 'o', 'c', '<', '=', '!', ':', '+', '-'};
	private String[] terminals = {"uid:=", ";", "if", "then", "fi", "else", "while", "done", "do", "c", "uid", "<", "=", "!=", "+", "-"};
	private Variable[] variables;
	
	public EParseTable() {
		variables = new Variable[15];
		
		//Initialize each variable and its parse table
		variables[0] = new Variable("S");
		variables[0].setTransition("uid:=", new String[]{"L"});
		variables[0].setTransition("if", new String[]{"L"});
		variables[0].setTransition("while", new String[]{"L"});
		
		variables[1] = new Variable("L");
		variables[1].setTransition("uid:=", new String[]{"I", "R"});
		variables[1].setTransition("if", new String[]{"I", "R"});
		variables[1].setTransition("while", new String[]{"I", "R"});
		
		variables[2] = new Variable("R");
		variables[2].setTransition("uid:=", new String[]{"L"});
		variables[2].setTransition("if", new String[]{"L"});
		variables[2].setTransition("while", new String[]{"L"});
		variables[2].setTransition("$", new String[]{"e"});
		variables[2].setTransition("done", new String[]{"e"});
		variables[2].setTransition("else", new String[]{"e"});
		variables[2].setTransition("fi", new String[]{"e"});
		
		variables[3] = new Variable("I");
		variables[3].setTransition("uid:=", new String[]{"A"});
		variables[3].setTransition("if", new String[]{"C"});
		variables[3].setTransition("while", new String[]{"W"});
		
		variables[4] = new Variable("A");
		variables[4].setTransition("uid:=", new String[]{"uid:=", "E", ";"});
		
		variables[5] = new Variable("C");
		variables[5].setTransition("if", new String[]{"if", "E", "then", "L", "O", "fi"});
		
		variables[6] = new Variable("O");
		variables[6].setTransition("fi", new String[]{"e"});
		variables[6].setTransition("else", new String[]{"else", "L"});
		
		variables[7] = new Variable("W");
		variables[7].setTransition("while", new String[]{"while", "E", "do", "L", "done"});
		
		variables[8] = new Variable("E");
		variables[8].setTransition("c", new String[]{"E2", "M"});
		variables[8].setTransition("uid", new String[]{"E2", "M"});
		
		variables[9] = new Variable("M");
		variables[9].setTransition(";", new String[]{"e"});
		variables[9].setTransition("then", new String[]{"e"});
		variables[9].setTransition("do", new String[]{"e"});
		variables[9].setTransition("<", new String[]{"Op1", "E2", "M"});
		variables[9].setTransition("=", new String[]{"Op1", "E2", "M"});
		variables[9].setTransition("!=", new String[]{"Op1", "E2", "M"});
		
		variables[10] = new Variable("E2");
		variables[10].setTransition("c", new String[]{"T", "K"});
		variables[10].setTransition("uid", new String[]{"T", "K"});
		
		variables[11] = new Variable("K");
		variables[11].setTransition(";", new String[]{"e"});
		variables[11].setTransition("then", new String[]{"e"});
		variables[11].setTransition("do", new String[]{"e"});
		variables[11].setTransition("<", new String[]{"e"});
		variables[11].setTransition("=", new String[]{"e"});
		variables[11].setTransition("!=", new String[]{"e"});
		variables[11].setTransition("+", new String[]{"Op2", "E2"});
		variables[11].setTransition("-", new String[]{"Op2", "E2"});
		
		variables[12] = new Variable("T");
		variables[12].setTransition("c", new String[]{"c"});
		variables[12].setTransition("uid", new String[]{"uid"});
		
		variables[13] = new Variable("Op1");
		variables[13].setTransition("<", new String[]{"<"});
		variables[13].setTransition("=", new String[]{"="});
		variables[13].setTransition("!=", new String[]{"!="});
		
		variables[14] = new Variable("Op2");
		variables[14].setTransition("+", new String[]{"+"});
		variables[14].setTransition("-", new String[]{"-"});
	}
	
	public void processString(String inputString) {
		String updatedString = "The input string '" + inputString + "' has been changed to '";
		int stringSize = inputString.length(); //this is needed to format the output nicely
		
		//Initialize a stack for the inputed string and a stack which will 
		//contain variables and will also adjust to match the inputed string
		Stack<String> variableStack = new Stack<String>();
		variableStack.push("$");
		variableStack.push("S");
		Stack<String> inputStack = new Stack<String>();
		inputStack.push("$");
		
		inputString = removeInvalidCharacters(inputString);
		
		List<String> tokenizedString = getTokenizedString(inputString);
		if (tokenizedString == null) {
			inputString = removeInvalidTerminals(inputString);
			tokenizedString = getTokenizedString(inputString);
		}
		
		for (int i = tokenizedString.size() - 1; i >= 0; i--) {
			inputStack.push(tokenizedString.get(i));
		}
		
		while(!inputStack.isEmpty()) {
			
			printStack(inputStack, stringSize + 5);
			printStack(variableStack, 0);
			System.out.println();
			
			//Pop first terminals from both stacks if they match
			if (inputStack.peek().equals(variableStack.peek())) {
				if (!inputStack.peek().equals("$")) {
					updatedString += inputStack.pop();
				}
				else {
					inputStack.pop();
				}
				variableStack.pop();
				continue;
			}
			//Terminals on top of the stacks are not equal
			else if (getVariable(variableStack.peek()) == null) {
				System.out.println();
				System.out.println("Expected one of the following terminals: '" + variableStack.peek() + "'");
				System.out.println("Instead of: " + "'" + inputStack.peek() + "'");
				System.out.println("The string has been updated");
				System.out.println();
				
				//Relpace the invalid terminal with the expected terminal
				if (!inputStack.peek().equals("$")) {
					inputStack.pop();
				}
				if (!variableStack.peek().equals("$")) {
					inputStack.push(variableStack.peek());
				}
				continue;
			}
			//Last element on variable stack is a variable ==> remove it
			//and push new elements according to its parse table
			else {
				Stack<String> variableStackCopy = new Stack<String>();
				variableStackCopy.addAll(variableStack);
				
				Variable next = getVariable(variableStack.pop());
				String[] newString = next.getTransitionString(inputStack.peek());
				
				//There is no rule for the given terminal in the variable's
				//parse table ==> reject string
				if (newString == null) {
					List<String> expectedTerminals = new ArrayList<String>();
					
					//Determine which terminals were expected
					String expected = "";
					while (next != null) {
						if (next.canBeEpsilon()) {
							expected = variableStack.pop();
							next = getVariable(expected);
							expectedTerminals = new ArrayList<String>();
							expectedTerminals.add(expected);
							expected = "'" + expected + "'";
						}
						else {
							expected = "";
							for (String terminal : next.getFirstSet()) {
								expectedTerminals.add(terminal);
								expected += "'" + terminal + "'" + " ";
							}
							next = null;
						}
					}
					
					System.out.println();
					System.out.println("Expected one of the following terminals: " + expected);
					System.out.println("Instead of: " + "'" + inputStack.peek() + "'");
					
					String newTerminal = "";
					//More than one terminal expected ==> ask user to choose
					if (expectedTerminals.size() > 1) {
						Scanner reader = new Scanner(System.in);
						if (inputStack.peek().equals("$")) {
							System.out.println("Enter which terminal you would like to add to the string: ");
						}
						else {
							System.out.println("Enter which terminal you would like '" + inputStack.peek() + "' to be replaced with:");
						}
						
						newTerminal = reader.next();
						while (expectedTerminals.indexOf(newTerminal) == -1) {
							System.out.println("Invalid terminal. Choose one of the expected ones: ");
							newTerminal = reader.next();
						}
					}
					//Only one terminal is expected
					else {
						newTerminal = expectedTerminals.get(0);
					}
					
					System.out.println("The string has been updated");
					System.out.println();
					
					//Replace a terminal on top of inputStack with the expected one or 
					//add it to the stack, if there are no terminals left
					if (!inputStack.peek().equals("$")) {
						inputStack.pop();
					}
					inputStack.push(newTerminal);
					variableStack = variableStackCopy;
					
					continue;
				}
				
				//Push new rule on top of variableStack
				for (int i = newString.length - 1; i >= 0; i--) {
					if (!newString[i].equals("e")) {	
						variableStack.push(newString[i]);
					}
				}
			}
		}
		System.out.println("ACCEPTED");
		System.out.println();
		System.out.println(updatedString + "'");
	}
	
	//prints out all the values that are stored on the given stack
	public void printStack(Stack<String> s, int printingLength) {
		String[] copy = new String[s.size()];
		s.copyInto(copy);
		
		for (int i = copy.length - 1; i >= 0; i--) {
			System.out.print(copy[i]);
			printingLength -= copy[i].length();
		}
		
		for (int i = 0; i < printingLength; i++) {
			System.out.print(" ");
		}
	}
	
	//Returns a Variable object with the given name
	public Variable getVariable(String name) {
		for (int i = 0; i < variables.length; i++) {
			if (variables[i].getName().equals(name)) {
				return variables[i];
			}
		}
		return null;
	}
	
	//Converts a string into a list of terminals. This makes processing
	//of a string easier. Returns null if a string is invalid.
	public List<String> getTokenizedString(String s) {
		List<String> newString = new ArrayList<String>();
		
		if (s.length() == 0) {
			return newString;
		}
		
		while (s.length() > 0) {
			 boolean exists = false;
			 for (int counter = 0; counter < terminals.length; counter++) {
				 if (s.startsWith(terminals[counter])) {
					 exists = true;
					 newString.add(terminals[counter]);
					 s = s.substring(terminals[counter].length());
					 break;
				 }
			 }
			 if (!exists) {
				 return null;
			 }
		}
		return newString;
	}
	
	//Finds all invalid terminals in the given string, prints them out and removes them from the given string
	public String removeInvalidTerminals(String s) {
		System.out.print("Inputted string contains invalid terminals: ");
		String validString = "";
		String invalidTerminal = "";
		
		while (s.length() > 0) {
			boolean exists = false;
			 for (int counter = 0; counter < terminals.length; counter++) {
				 if (s.startsWith(terminals[counter])) {
					 validString += terminals[counter];
					 exists = true;
					 s = s.substring(terminals[counter].length());
					 if (!invalidTerminal.equals("")) {
						 System.out.print("'" + invalidTerminal + "' ");
						 invalidTerminal = "";
					 }
					 break;
				 }
			 }
			 if (!exists) {
				 invalidTerminal += s.charAt(0);
				 s = s.substring(1);
			 }
		}
		if (!invalidTerminal.equals("")) {
			System.out.println("'" + invalidTerminal + "'");
		}
		else {
			System.out.println();
		}
		if (!validString.equals(s)) {
			System.out.println("All of them have been removed and a new string is '" + validString + "'");
			System.out.println();
		}
		
		return validString;
	}
	
	//Removes all invalid characters from a string
	public String removeInvalidCharacters(String s) {
		String validString = "";
		List<Character> invalidChars = new ArrayList<Character>();
		
		for (int i = 0; i < s.length(); i++) {
			boolean validChar = false;
			for (int j = 0; j < alphabet.length; j++) {
				if (s.charAt(i) == alphabet[j]) {
					validChar = true;
					validString += s.charAt(i);
					break;
				}
			}
			if (!validChar) {
				if (invalidChars.size() == 0) {
					System.out.print("Invalid characters entered: ");
				}
				if (invalidChars.indexOf(s.charAt(i)) == -1) { 
					System.out.print("'" + s.charAt(i) + "' ");
					invalidChars.add(s.charAt(i));
				}
			}
		}
		if (invalidChars.size() > 0) {
			System.out.println();
			System.out.println("All of them have been removed and a new string is '" + validString + "'");
			System.out.println();
		}
		return validString;
	}
}
