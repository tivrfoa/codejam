import java.util.*;
import java.io.*;

// a string of between 1 and 500 characters that is guaranteed to win!
public class Solution {
	
	String solve2() {
		boolean possible = true;
		final int A = in.nextInt();
		final String[] a = new String[A];
		int max_length = 0;
		List<String> remaining = new ArrayList<>();
		for (int i = 0; i < A; ++i) {
			a[i] = in.nextToken();
			remaining.add(a[i]);
			if (a[i].length() > max_length) max_length = a[i].length();
		}

		String ans = "";
		for (int i = 0; true; ++i) {
			int qt_r = 0;
			int qt_p = 0;
			int qt_s = 0;
			for (String s : remaining) {
				char c = s.charAt(i%s.length());
				if (c == 'R') ++qt_r;
				else if (c == 'P') ++qt_p;
				else ++qt_s;

				if (qt_r != 0 && qt_p != 0 && qt_s != 0) {
					return "IMPOSSIBLE";
				}
			}
			char new_char = ' ';
			if (qt_r > 0) {
				if (qt_s == 0) {
					new_char = 'P';
				} else {
					new_char = 'R';
				}
			} else if (qt_p > 0) {
				new_char = 'S';
			} else {
				new_char = 'R';
			}
			ans += new_char;
			for (int r = 0; r < remaining.size(); ++r) {
				String s = remaining.get(r);
				if (s.charAt(i%s.length()) != new_char) {
					remaining.remove(r--);
				}
			}
			if (remaining.isEmpty()) {
				return new String(ans);
			}
		}
	}

	void solve() {
		int T = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			out.printf("Case #%d: %s\n", t, solve2());
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
