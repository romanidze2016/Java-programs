import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyProgram {

	public static void main(String[] args) throws FileNotFoundException{
		
		List<String> lines = new ArrayList<String>();
		
		//user passes a string and a name of the DFA file
		if (args.length == 2) {
			Scanner fileScanner = new Scanner(new File(args[1]));
			while (fileScanner.hasNextLine()){
			   lines.add(fileScanner.nextLine().trim());
			} 
			fileScanner.close();
		}
		//user only passes a string
		else if (args.length == 1){
			lines.add("q1,q2,q3,q4,q5");
			lines.add("a,b");
			lines.add("q1");
			lines.add("q1,q2,q3");
			lines.add("q2,q3");
			lines.add("q3,q4");
			lines.add("q3,q5");
			lines.add("q1,q5");
			lines.add("q3,q5");
		}
		//invalid input
		else {
			System.out.println("Invalid number of arguments is passed to the program");
			return;
		}
		
		DFA d = new DFA(lines);
		boolean validEntry = d.processString(args[0]);
		if (!validEntry) {
			System.out.println("The entered string is invalid");
		}
	}

}
