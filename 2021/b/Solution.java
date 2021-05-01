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
		expect(solve3(2021), 2122);
		expect(solve3(68000), 78910); 
		expect(solve3(101), 123);
	}

	static void expect(long n, long target) {
		assert n == target: "Got: " + n + " Expected: " + target;
	}

	long solve3(long y) {
		return nextRoaringNumber(y);
	}

	boolean isRoaringNumber(long y) {
		String n = "" + y;
		boolean odd = y % 2 != 0;
		int len = n.length();
		int max_digits = len / 2;
		// if (odd) max_digits += 1;
		outer:
		for (int d = 1; d <= max_digits; ++d) {
			for (int p = 0, inc = d, prev_inc = d; p < len; p += prev_inc) {
				prev_inc = inc;
				String sa = n.substring(p, p + inc);
				if (NINES.matcher(sa).matches()) ++inc;
				int a = Integer.parseInt(sa);
				if (p + prev_inc + inc > len) continue outer;
				int b = Integer.parseInt(n.substring(p + prev_inc, p + prev_inc + inc));

				if (b - a != 1) continue outer;
				if (p + prev_inc + inc == len) break;
			}
			return true;
		}
		return false;
	}

	/* long nextRoaringNumber(long y) {
		if (y == 1) return 12;
		long lo = y + 1;
		long hi = (long) 1e18; // TODO maybe lo * 10 ?
		while (lo <= hi) {
			long mid = (lo + hi) / 2;
			if (isRoaringNumber(mid)) {
				hi = mid - 1;
			} else {

			}
		}
		return lo;
	} */

	long nextRoaringNumber(long y) {
		if (y == 1) return 12;
		for (long n = y + 1; n < 1e15; ++n)
			if (isRoaringNumber(n))
				return n;
		return -1;
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
