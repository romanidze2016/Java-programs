import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ConnectFour {

	//private static long startTime = System.currentTimeMillis();
	
	public static void main(String[] args) throws FileNotFoundException {
		/*Scanner in = new Scanner(new FileReader("Connect4Tc2.in"));
		Scanner out = new Scanner(new FileReader("Connect4Tc2.out"));
		
		while (in.hasNextLine()) {
			String[] input = in.nextLine().split(" ");
			Minimax n = new Minimax(input[0], input[1], input[2], input[3]);
			ArrayList<Integer> result = n.runMiniMax();
			int index = out.nextInt();
			int nodes = out.nextInt();
			if (result.get(0) != index || result.get(1) != nodes) {
				System.out.println(result.get(0) + " != " + index);
				System.out.println(result.get(1) + " != " + nodes);
				break;
			}
			else {
				System.out.println();
				System.out.println(result.get(0) + " == " + index);
				System.out.println(result.get(1) + " == " + nodes);
			}
		}*/
	    
		
		//Minimax m = new Minimax(args[0], args[1], args[2], args[3]);
		Minimax m = new Minimax(args[0], args[1]);
		m.runMiniMax();
		
		/*long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");*/
	}

}
