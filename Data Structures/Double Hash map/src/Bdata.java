import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bdata {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DoubleHashMap<Long, List<String>> map = new DoubleHashMap<Long, List<String>>(50000, 1, 1, 1);
		PasswordManager spm = new PasswordManager();
		BufferedReader br = new BufferedReader(new FileReader("databaseB.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String password = line.trim();
				Long passwordHash = spm.hash(password);
				if (map.get(passwordHash) != null) {
					map.get(passwordHash).add(password);
				}
				else {
					List<String> list = new ArrayList<String>();
					list.add(password);
					map.put(passwordHash, list);
				}
				// TODO: if passwordHash is in a, add password to its list value
				// else, instantiate a new ArrayList and add password to it
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		List<Long> hashes = map.keys();
		for (Long hash : hashes) {
			List<String> passwords = map.get(hash);
			if (passwords.size() > 1) {
				System.out.print(hash + ": ");
				for (int counter = 0; counter < passwords.size(); counter++) {
					System.out.print(passwords.get(counter) + ", ");
				}
				System.out.println();
				// all passwords in this list have the same hash representation
			}
		}
	}

}
