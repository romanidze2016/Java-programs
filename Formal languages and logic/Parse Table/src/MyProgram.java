import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class MyProgram {

	public static void main(String[] args) throws FileNotFoundException{
		
		String s = "";
		
		try {
			Scanner fileScanner = new Scanner(new File(args[0]));
			while (fileScanner.hasNextLine()){
				s = s.concat(fileScanner.nextLine().trim());
			} 
			fileScanner.close();
			}
		catch (Exception e) {
			System.out.println(args[0] + " file does not exist");
			return;
		}
		
		ParseTable p = new ParseTable();
		EParseTable ep = new EParseTable();
		
		s =  s.replaceAll("\\s+","");
		if (args.length == 2 && args[1].equals("-e")) {
			ep.processString(s);
		}
		else {
			p.processString(s);
		}
	}

}
