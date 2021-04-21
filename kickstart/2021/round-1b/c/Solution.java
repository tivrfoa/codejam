import java.util.*;
import java.io.*;

/*

I'll try a solution to work only for test sets 1 and 2
*/
public class Solution {

	static final int N = 30000;
	static final long[] primes = new long[N];

	static void find_primes() {
		primes[0] = 2;
		primes[1] = 3;
		int p_len = 2;
		outer:
		for (int p = 4; p_len < N; ++p) {
			for (int j = 0; j < p_len; ++j) {
				if (p % primes[j] == 0) continue outer;
			}
			primes[p_len++] = p;
		}
	}

	void printArray(long[] a) {
		out.print("[");
		for (long l : a) out.print(l + ", ");
		out.println("]");
	}

	long find_prime_at_most_n(int n) {
		for (int i = 0; i < primes.length; ++i) {
			if (primes[i] > n) return primes[i - 1];
		}
		throw new RuntimeException("Invalid number: " + n);
	}

	int find_prime_index_at_most_n(int n) {
		for (int i = 0; i < primes.length; ++i) {
			if (primes[i] > n) return i - 1;
		}
		throw new RuntimeException("Invalid number: " + n);
	}

	boolean is_prime(int n) {
		for (long i : primes) if (n == i) return true;
		return false;
	}

	long solve2() {
		// printArray(primes);
		/*long tmp = primes[N - 2] * primes[N - 1];
		out.println(tmp);
		out.println(Long.MAX_VALUE);
		out.println(tmp > Long.MAX_VALUE);*/
		//out.println(primes[N-1]);

		final long Z = in.nextLong();
		int p2 = find_prime_index_at_most_n((int) Z / 2);
		int p1 = p2 - 1;
		// out.printf("---> primes[%d] = %d\n", p2, primes[p2]);
		long product = 0;
		while (true) {
			long tmp = primes[p1] * primes[p2];

			if (tmp <= Z) {
				product = tmp;
				break;
			}
			--p1;
			--p2;
		}
		return product;
	}

	void readArray(int[] a) {
		for (int i = 0; i < a.length; ++i) a[i] = in.nextInt();
	}

	void solve() {
		find_primes();
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
