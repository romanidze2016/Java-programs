import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DoubleHashMapTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		String pathToFile = "databaseA.txt";
		SkipList<String, Double> map = new SkipList<String, Double>();
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
		try {
			String line = br.readLine();
			while (line != null) {
				String[] pieces = line.trim().split("\\s+");
				if (pieces.length == 4) {
					map.put(pieces[0], Double.parseDouble(pieces[1]));
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		assertEquals((Double) 0.014013102, map.get("65.54.164.51"));
		assertEquals((Double) 0.014013102, map.put("65.54.164.51", 0.001));
		assertEquals((Double) 0.001, map.get("65.54.164.51"));
		
	}

}
