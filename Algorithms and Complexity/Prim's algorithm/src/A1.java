import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class A1 {

	public static void main(String[] args) {
		
		Scanner main = new Scanner(System.in);
		String line = main.nextLine();
		Scanner minor = new Scanner(line);
		int n = minor.nextInt();
		
		line = main.nextLine();
		minor.close();
		minor = new Scanner(line);
		int m = minor.nextInt();
		
		Graph g = new Graph(n);
		g.setM(m);
		
		line = main.nextLine();
		minor.close();
		minor = new Scanner(line);
		while (minor.hasNextInt()) {
			g.addA(minor.nextInt());
		}
		
		for (int i = 0; i < m; i++) {
			int first = main.nextInt();
			int second = main.nextInt();
			float weight = main.nextFloat();
			g.getVertex(first).setEdge(second, weight);
			g.getVertex(second).setEdge(first, weight);
		}
		
		float d = g.calculateMST();
		d = d*100;
		d = Math.round(d);
		d = d/100;
		System.out.println(d);
	}

}
