import java.util.*;
import java.io.*;


/*
 * 
 * 1) Should place token or inspect ?
 * 
 * 2) If place token, then which token.
 * 	- Same token always ?
 *  - Choose randomly ?
 * 
 * 
 */ 
public class Solution {

	private static Scanner sc;
	private static PrintWriter out;
	
	/**
	 * The V and N magic values were taken from the problem analysis.
	 * So the competitor would have to make tests to try to reach
	 * these values.
	 */ 
	private static final int V = 14;
	private static final int N = 60;
	
	private static int[] qtTokensVase = new int[21];
	
		
	public static void main(String[] args) throws Exception {
		
		sc = new Scanner(System.in);
		out = new PrintWriter(System.out);	
		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {
		Arrays.fill(qtTokensVase, 0);
		
		int vase = 1;
		for (int d = 1; d < N; ++d) { // sabotaging V vases evenly
			sc.nextInt();
			out.println(vase + " " + d);
			out.flush();
			++vase;
			if (vase == V + 1) vase = 1;
		}
		
		int candidateVase = 1;
		for (int v = 1; v <= 20; ++v) { // inspect
			sc.nextInt();
			out.println(v + " " + 0);
			out.flush();
			int n = sc.nextInt();
			qtTokensVase[v] = n;
			if (n < qtTokensVase[candidateVase]) {
				candidateVase = v;
			}
			for (int j = 0; j < n; ++j) {	
				sc.nextInt();
			}
		}
		
		//System.err.printf("Candidate vase is %d and has %d tokens.\n",
		//		candidateVase, qtTokensVase[candidateVase]);
		
		for (int i = 0; i < 100 - N - 20; i++) {
			sc.nextInt();

			int minV = 1;
			if (minV == candidateVase) minV = 2;
			for (int v = 1; v <= 20; ++v) if (v != candidateVase) {
				if (qtTokensVase[v] < qtTokensVase[minV]) {
					minV = v;					
				}
			}
			
			out.println(minV + " " + 1);
			out.flush();
			++qtTokensVase[minV];
		}
		
		sc.nextInt();
		out.println(candidateVase + " " + 100);
		out.flush();
	}

}
