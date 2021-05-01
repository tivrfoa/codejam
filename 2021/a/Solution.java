import java.util.*;
import java.io.*;


/**
The probability of some integer c being chosen
is c/k where k is the maximum possible integer.

I can sort the numbers. Then I check the two biggest
intervals. I then chose the first number of each interval.
1st interval: qnt win interval_size/2 + number itself.
*/
public class Solution {

	static boolean debugging = false;

	void testApp() {
		assert solve3(3, 10, new int[]{1, 3, 7}) == 0.5;
		assert solve3(4, 10, new int[]{4, 1, 7, 3}) == 0.4;
		assert solve3(4, 3, new int[]{1, 2, 3, 2}) == 0.0;
		assert solve3(4, 4, new int[]{1, 2, 4, 2}) == 0.25;
		assert solve3(1, 1, new int[]{1}) == 0.0;
		assert solve3(1, 2, new int[]{1}) == 0.5;
		assert solve3(5, 10, new int[]{4, 1, 7, 3, 10}) == 0.2;
		assert solve3(2, 2, new int[]{1, 1}) == 0.5;
		assert solve3(2, 2, new int[]{1, 2}) == 0.0;
		assert solve3(2, 2, new int[]{2, 2}) == 0.5;
		assert solve3(2, 3, new int[]{1, 2}) == 0.1;
		assert solve3(1, 30, new int[]{15}) == 0.966666;
	}

	double solve3(int N, double K, int[] p) {
		double ans = 0;
		int diff1 = 0;
		int diff2 = 0;
		int f_len = 0;
		int s_len = 0;
		int first_start = 0;
		int first_finish = 0;
		int second_start = 0;
		int second_finish = 0;

		Arrays.sort(p);

		diff1 = p[0] - 1;
		if (diff1 > 0) {
			first_start = 1;
			first_finish = p[0] - 1;
			//f_len = first_finish - first_start + 1;
			diff1 = (diff1 + 1) * 2;
		}
		for (int i = 1; i < p.length; ++i) {
			int tmp = p[i] - p[i - 1];
			if (diff1 <= diff2) {
				if (tmp > diff1) {
					diff1 = tmp;
					first_start = p[i - 1] + 1;
					first_finish = first_start + (tmp / 2 - 1);
				}
			} else {
				if (tmp > diff2) {
					diff2 = tmp;
					second_start = p[i - 1] + 1;
					second_finish = second_start + (tmp / 2 - 1);
				}
			}
		}
		int tmp = (int) K - p[p.length - 1];
		if (diff1 <= diff2) {
			if (tmp > diff1) {
				diff1 = tmp;
				first_start = p[p.length - 1] + 1;
				first_finish = (int) K;
			}
		} else if (tmp > diff2) {
			diff2 = tmp;
			second_start = p[p.length - 1] + 1;
			second_finish = (int) K;
		}
		

		if (diff1 == 0) ans = 0.0;
		else if (diff2 == 0) {
			ans = (first_finish - first_start + 1) / K;
		} else {
			ans = (first_finish - first_start + 1 +
			        second_finish - second_start + 1) / K;
		}
		// System.err.println("ans = " + ans);
		return ans;
	}


	void solve2(int test_case) {
		int N = in.nextInt();
		int K = in.nextInt();
		int[] p = new int[N];
		readArray(p);
		out.printf("%.16f\n", solve3(N, K, p));
		/* String ans = solve3(N, K, p) + "";
		if (ans.length() > 7)
			out.printf("%s\n", ans.substring(0, 8));
		else
			out.printf("%s\n", ans); */
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
