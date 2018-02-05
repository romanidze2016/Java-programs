import java.util.Scanner;

public class A3 {
	
	public static int find_profit(int[] M, int[][] jobs, int i) {
		
		if (i < 0) {
			return 0;
		}
		else if (i == 0) {
			if (M[0] == -1) {
				if (jobs[0][0] > jobs[0][1]) {
					M[0] = jobs[0][0];
				}
				else {
					M[0] = jobs[0][1];
				}
			}
			return M[0];
		}
		else {
			
			int case1;
			int case2;
			
			if (M[i - 1] == -1) {
				M[i - 1] = find_profit(M, jobs, i - 1);
			}
			case1 = jobs[i][0] + M[i - 1];
			
			if (i - 2 >= 0) {
				if (M[i - 2] == -1) {
					M[i - 2] = find_profit(M, jobs, i - 2);
				}
				case2 = jobs[i][1] + M[i - 2];
			}
			else {
				case2 = jobs[i][1];
			}
			
			
			if (case1 > case2) {
				M[i] = case1;
				return case1;
			}
			else {
				M[i] = case2;
				return case2;
			}
		}
	}

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int[][] jobs = new int[n][2];
		int[] profits = new int[n];
		profits[0] = -1;
		for (int i = 0; i < n; i++) {
			jobs[i][0] = s.nextInt();
			jobs[i][1] = s.nextInt();
			profits[i] = find_profit(profits, jobs, i);
			
		}
		
		System.out.println(profits[n - 1]);
	}

}
