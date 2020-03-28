import java.util.*;
import java.io.*;

import java.lang.System.out;


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
	// private static PrintWriter out;
	
	private static final int V = 14;
	private static final int N = 60;
	
	private static int[] qtTokensVase = new int[21];
	
		
	public static void main(String[] args) throws Exception {
		
		sc = new Scanner(System.in);
		// out = new PrintWriter(System.out);	
		
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
			int day = sc.nextInt();
			System.err.println("Day " + day);
			out.println(vase + " " + d);
			if (vase == V + 1) vase = 1;
		}
		
		vase = 1;
		int lower = 101;
		int candidateVase = 0;
		System.err.println("Inspecting ...");
		for (int i = 0; i < 20; i++) { // inspect
			int day = sc.nextInt();
			System.err.println("Day " + day);
			System.err.println("Inspecting vase " + vase);
			System.err.println(vase + " " + 0);
			out.println(vase + " " + 0);
			int n = sc.nextInt();
			qtTokensVase[vase] = n;
			if (n < lower) {
				lower = n;
				candidateVase = vase;
			}
			// System.err.println("n = " + n);
			for (int j = 0; j < n; ++j) {	
				sc.nextInt();			
				// int token = sc.nextInt();				
				// System.err.println(token);
			}
			++vase;
		}
		
		System.err.printf("Candidate vase is %d and has %d tokens.\n",
				candidateVase, qtTokensVase[candidateVase]);
		
		for (int i = 0; i < 100 - N - 20; i++) {
			int day = sc.nextInt();
			System.err.println("Day " + day);

			int minV = 1;
			if (minV == candidateVase) minV = 2;
			for (int v = 1; v <= 20; ++v) if (v != candidateVase) {
				if (qtTokensVase[v] < qtTokensVase[minV]) {
					minV = v;					
				}
			}
			
			out.println(minV + " " + 1);
			++qtTokensVase[minV];
		}
		
		int day = sc.nextInt();
		System.err.println("Day " + day);
		out.println(candidateVase + " " + 100);
		out.flush();
	}

}
