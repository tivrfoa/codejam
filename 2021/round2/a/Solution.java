import java.util.*;
import java.util.regex.*;
import java.io.*;


/**
*/
public class Solution {

	private static boolean debugging = false;

	int query(int i, int j) {
		out.println("M " + i + " " + j);
		out.flush();
		int ans = in.nextInt();
		// System.err.printf("Queried %d %d, got %d\n", i, j, ans);
		if (ans == -1) throw new RuntimeException("Bad query");
		return ans;
	}

	int swap(int i, int j) {
		out.println("S " + i + " " + j);
		out.flush();
		int ans = in.nextInt();
		if (ans == -1) throw new RuntimeException("Bad Swap");
		return ans;
	}

	void done() {
		out.println("D");
		out.flush();
		int ans = in.nextInt();
		if (ans != 1) throw new RuntimeException("Not sorted");
	}

	void solve2(final int test_case, final int N) {
		// System.err.println("Test case: " + test_case);
		for (int i = 1; i < N; ++i) {
			int min = query(i, N);
			if (min != i) swap(i, min);
		}
		done();
	}

	void solve() {
		int T = in.nextInt();
		int N = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			solve2(t, N);
		}
	}
	
	public static void main(String[] args) {
		new Solution().run();
	}

	void readArray(int[] a) {
		for (int i = 0; i < a.length; ++i) a[i] = in.nextInt();
	}

	FastScanner in;
	PrintWriter out;

	void run() {
		in = new FastScanner();
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public String nextToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}
	
	long pow(long a, long b, long mod) {
		long res = 1 % mod;
		while (b > 0) {
			if ((b & 1) == 1) {
				res = res * a % mod;
			}
			a = a * a % mod;
			b >>>= 1;
		}
		return res;
	}
	
	long inv(long a, long mod) {
		return pow(a, mod - 2, mod);
	}
}
