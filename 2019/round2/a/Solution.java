import java.util.*;
import java.io.*;

public class Solution {

	private static Scanner sc;
		
	public static void main(String[] args) throws Exception {
		
		sc = new Scanner(System.in);		
		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {
		
		int N = sc.nextInt();
		int[] C = new int[N];
		int[] J = new int[N];
		
		for (int i = 0; i < N; ++i) {
			C[i] = sc.nextInt();
			J[i] = sc.nextInt();
		}
		
		
		
		System.out.printf("Case #%d: %d\n", t, a);
	}
	
	
	private static int molecularWeight() {
		return 0;
	}

}
