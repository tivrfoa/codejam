import java.util.*;
import java.io.*;

/**

A[i] is positive
*/
public class Solution {

	int find_length_backwards(int[] A, int from_index, int diff) {
		int len = 1;
		int prev = A[from_index] - diff;
		for (int j = from_index - 2; j >= 0; --j) {
			if (prev - A[j] != diff) break;
			++len;
			prev = A[j];
		}
		return len;
	}

	int find_length_forward(int[] A, int i, int diff) {
		int len = 1;
		int prev = A[i - 1] + diff;
		for (int j = i + 1; j < A.length; ++j) {
			if (A[j] - prev != diff) break;
			++len;
			prev = A[j];
		}
		return len;
	}

	int solve2() {
		final int N = in.nextInt();
		final int[] A = new int[N];
		readArray(A);
		if (N <= 3) return N;
		int diff = A[1] - A[0];
		int from_index = 0;
		int ans = 3, current_length = 1;
		for (int i = 1; i < N; ++i) {
			int tmpDiff = A[i] - A[i - 1];
			if (tmpDiff == diff) {
				++current_length;
				if (i == N - 1) {
					if (from_index > 0) {
						current_length += find_length_backwards(A, from_index, diff);
					}
					if (current_length > ans) ans = current_length;
				}
			} else {
				// try backwards and forward
				int back = current_length;
				int front = current_length;

				// backwards
				if (from_index > 0) {
					back += find_length_backwards(A, from_index, diff);
				}

				// forward
				front += find_length_forward(A, i, diff);

				if (back > ans || front > ans) {
					ans = (back > front) ? back : front;
				}

				if (i == N - 1) break;
				current_length = 1;
				from_index = i - 1;
				diff = A[i] - A[i - 1];
				--i;
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
