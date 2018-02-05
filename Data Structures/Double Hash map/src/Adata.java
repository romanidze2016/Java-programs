import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Adata {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// instantiate hash maps
		String pathToFile = "databaseA.txt";
		DoubleHashMap<String, Double> m = new DoubleHashMap<String, Double>(2000, 1, 4271, 647);
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
		try {
			String line = br.readLine();
			while (line != null) {
				String[] pieces = line.trim().split("\\s+");
				if (pieces.length == 4) {
					m.put(pieces[0], Double.parseDouble(pieces[1]));
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		
		System.out.println("number of collisions = " + m.putCollisions());
		System.out.println("number of total collisions = " + m.totalCollisions());
		System.out.println("number of max collisions = " + m.maxCollisions());
	}
	
}
