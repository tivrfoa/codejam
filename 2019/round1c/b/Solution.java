import java.util.*;
import java.io.*;

public class Solution {


	private static final char[] A_TO_E = new char[]{'A', 'B', 'C', 'D', 'E'};

	char[] shelf = new char[596];

	char query(int pos) {
		out.println(pos);
		out.flush();
		char c = in.nextToken().charAt(0);
		if (c == 'N') throw new RuntimeException("fix the program");
		return c;
	}

	void printShelf() {
		boolean p = true;
		for (int x = 1; x < shelf.length; ++x) {
			char c = shelf[x];
			if (p) {
				System.err.print("[");
				p = false;
			}
			System.err.print(c + " ");
			if (x % 5 == 0) {
				System.err.println("]");
				p = true;
			}
		}
		System.err.println();
	}

	char findFirst() {
		int[] qt = new int[5];
		int i;
		for (i = 1; i <= shelf.length - 4; i += 5) {
			char c = query(i);
			shelf[i] = c;
			qt[c - 'A']++;
		}
		for (i = 0; i < qt.length; ++i)
			if (qt[i] == 23) return (char) (i + 'A');
		for (i = 0; i < qt.length; ++i)
			System.err.printf("qt[%d] = %d\n", i, qt[i]);
		printShelf();
		throw new IllegalStateException("it didn't find missing 1st");
	}

	char findSecond(char first) {
		int[] qt = new int[5];
		for (int i = 1; i < shelf.length - 4; i += 5) {
			if (shelf[i] == first) {
				char c = query(i + 1);
				shelf[i + 1] = c;
				qt[c - 'A']++;
			}
		}
		for (int i = 0; i < qt.length; ++i)
			if (qt[i] == 5) return (char) (i + 'A');
		for (int i = 0; i < qt.length; ++i)
			System.err.printf("qt[%d] = %d\n", i, qt[i]);
		throw new IllegalStateException("it didn't find missing 2nd");
	}

	char findThird(char first, char second) {
		int[] qt = new int[5];
		for (int i = 1; i < shelf.length - 4; i += 5) {
			if (shelf[i] == first && shelf[i + 1] == second) {
				char c = query(i + 2);
				shelf[i + 2] = c;
				qt[c - 'A']++;
			}
		}
		for (int i = 0; i < qt.length; ++i)
			if (qt[i] == 1) return (char) (i + 'A');
		for (int i = 0; i < qt.length; ++i)
			System.err.printf("qt[%d] = %d\n", i, qt[i]);
		throw new IllegalStateException("it didn't find missing 3rd");
	}

	char findFourth(char first, char second, char third) {
		char c = ' ' ;
		for (int i = 1; i < shelf.length - 4; i += 5) {
			if (shelf[i] == first && shelf[i + 1] == second &&
					shelf[i + 2] == third) {
				c = query(i + 3);
				shelf[i + 3] = c;
			}
		}

		for (char l : A_TO_E) {
			if (l != c && l != first && l != second && l != third)
				return l;
		}
		throw new IllegalStateException("it didn't find missing 4th");
	}
	
	void solve2() {
		char[] ans = new char[5];
		ans[0] = findFirst(); // System.err.println("First: " + ans[0]);
		ans[1] = findSecond(ans[0]); // System.err.println("Second: " + ans[1]);
		ans[2] = findThird(ans[0], ans[1]); // System.err.println("Third: " + ans[2]);
		ans[3] = findFourth(ans[0], ans[1], ans[2]);

		outer:
		for (char c : A_TO_E) {
			for (char a : ans) {
				if (c == a) continue outer;
			}
			ans[4] = c;
			break;
		}

		out.println(new String(ans));
		out.flush();
		in.nextToken(); // read verdict
	}

	void solve() {
		int T = in.nextInt();
		int F = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			solve2();
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
