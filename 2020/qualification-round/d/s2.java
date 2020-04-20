import java.util.*;
import java.io.*;
import static java.lang.System.out;

/**
 * 
 * ESAb ATAd
 * 
 * 
 * 150 maximum queries
 * 
 */
public class Solution {

	private static Scanner sc;
	
	private static int b;
	private static int samepair = -1;
	private static int diffpair = -1;
	
	private static int[] ans = new int[100];
	
	private static int queryCount = 0;
	
	private static int query(int c) {
		out.println(c+1);
		//++queryCount;
		return sc.nextInt();
	}
	
	private static void findpair(int i) {
		ans[i] = query(i);
		ans[b-1-i] = query(b-1-i);
		if (samepair == -1 && ans[i] == ans[b-1-i])
			samepair = i;
		if (diffpair == -1 && ans[i] != ans[b-1-i])
			diffpair = i;
	}
	
	private static void findchange() {
		boolean comp = false;
		if (samepair != -1 && ans[samepair] != query(samepair))
			comp = true;
		if (samepair == -1) query(0);
		boolean rev = comp;
		if (diffpair != -1 && ans[diffpair] != query(diffpair))
			rev = !comp; // TODO why?
		if (diffpair == -1) query(0);
		
		if (comp)
			for (int i = 0; i < b; i++)
				ans[i] = ans[i] == 0 ? 1 : 0;
		if (rev)
			for (int i = 0; i < b/2; i++) {
				int tmp = ans[i];
				ans[i] = ans[b-i-1];
				ans[b-i-1] = tmp;
			}
	}

	private static void solve(int t) {
		samepair = -1;
		diffpair = -1;

		int c = 0;
		for (; c < 5; ++c) {
			findpair(c);
		}
		findchange();
		while (c < b/2) {
			int i = 0;
			for (; i < 4 && c < b/2; ++i, ++c)
				findpair(c);
			if (i == 4)
					findchange();			
		}
		
		for (int i = 0; i < b; i++)
			out.print(ans[i]);

		out.println();
		
		String ok = sc.next();
		if (ok.equals("N"))
			System.exit(-1);
	}

	public static void main(String[] args) throws Exception {

		sc = new Scanner(System.in);
		
		int T = Integer.parseInt(sc.next());
		b = Integer.parseInt(sc.next());
		// System.err.println(T + " " + b);

		for (int t = 1; t <= T; ++t) {
			solve(t);
		}

		sc.close();
	}
}
