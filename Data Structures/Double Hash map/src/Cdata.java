import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Cdata {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		SkipListPasswordManager spm = new SkipListPasswordManager();
		BufferedReader br = new BufferedReader(new FileReader("databaseC.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String username = line.trim();
				spm.addNewUser(username, "hello");
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		System.out.println(spm.searchSteps("SMITH"));
		System.out.println(spm.searchSteps("JOHNSON"));
		System.out.println(spm.searchSteps("WILLIAMS"));
		System.out.println(spm.searchSteps("BROWN"));
		System.out.println(spm.searchSteps("JONES"));
		System.out.println(spm.searchSteps("MILLER"));
		System.out.println(spm.searchSteps("DAVIS"));
		System.out.println();
		System.out.println(spm.numberUsers());
		
	}

}
