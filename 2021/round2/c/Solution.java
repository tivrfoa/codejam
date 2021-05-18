import java.util.*;
import java.util.regex.*;
import java.io.*;


/**
*/
public class Solution {

	private static boolean debugging = false;
	private static final Pattern NINES = Pattern.compile("9+");

	void testApp() {
		expect(solve3(2020), 2021);
	}

	static void expect(long n, long target) {
		assert n == target: "Got: " + n + " Expected: " + target;
	}

	long solve3(long y) {
		return TODO(y);
	}

	void solve2(int test_case) {
		long y = in.nextLong();

		out.printf("%d\n", solve3(y));
	}

	void readArray(int[] a) {
		for (int i = 0; i < a.length; ++i) a[i] = in.nextInt();
	}

	void solve() {
		int T = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			out.printf("Case #%d: ", t);
			solve2(t);
		}
	}
	
	public static void main(String[] args) {
		assert debugging = true;
		if (debugging) new Solution().testApp();
		else new Solution().run();
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
