import java.util.*;
import java.io.*;

/**

A[i] is positive
*/
public class Solution {


	
	int solve2() {
		int ans = 0;
		final int N = in.nextInt();
		final int[] A = new int[N];
		readArray(A);
		if (N <= 3) return N;
		int diff = A[1] - A[0];
		int from_index = 0;
		int current_length = 2;
		for (int i = 2; i < N /*&& current_length + (N - i) > ans*/; ++i) {
			int tmpDiff = A[i] - A[i - 1];
			if (tmpDiff == diff) {
				++current_length;
				if (i == N - 1) {
					if (current_length > ans) {
						ans = current_length;
					}
					if (from_index > 0) {
						int prev = A[from_index] - diff;
						++current_length;
						for (int j = from_index - 2; j > 0; --j) {
							if (prev - A[j] != diff) break;
							++current_length;
							prev = A[j];
						}
						if (current_length > ans) ans = current_length;
					}
				}
			} else {
				// try backwards and forward
				int back = current_length;
				int front = current_length;
				int j = 0;

				// backwards
				if (from_index > 0) {
					int prev = A[from_index] - diff;
					++back;
					for (j = from_index - 2; j > 0; --j) {
						if (prev - A[j] != diff) break;
						++back;
						prev = A[j];
					}
				}

				// forward
				int prev = A[i - 1] + diff;
				++front;
				for (j = i + 1; j < N; ++j) {
					if (A[j] - prev != diff) break;
					++front;
					prev = A[j];
				}

				if (back > ans || front > ans) {
					ans = (back > front) ? back : front;
					// if (j == N) break;
				}

				if (i == N - 1) break;
				current_length = 1;
				from_index = i;
				diff = A[i + 1] - A[i];
			}
		}
		return ans;
	}

	void readArray(int[] a) {
		for (int i = 0; i < a.length; ++i) a[i] = in.nextInt();
	}

	void solve() {
		int T = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			out.printf("Case #%d: %d\n", t, solve2());
		}
	}
	
	public static void main(String[] args) {
		new Solution().run();
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
