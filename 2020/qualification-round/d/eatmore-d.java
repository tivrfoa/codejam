import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Solution {

	// Discuss this round on Codeforces: https://codeforces.com/blog/entry/71545

	static int b, s;

	static boolean query(int p) throws IOException {
		++s;
		out.println(p + 1);
		out.flush();
		return scanInt() != 0;
	}

	static void solve() throws Exception {
		s = 0;
		boolean a[] = new boolean[b];
		int p = 0;
		int pSym = -1, pAsym = -1;
		while (p < b / 2) {
			if (s > 0 && s % 10 == 0) {
				if (pSym < 0 || pAsym < 0) {
					if (query(0) != a[0]) {
						for (int i = 0; i < b; i++) {
							a[i] = !a[i];
						}
					}
					query(0);
				} else {
					if (query(pSym) != a[pSym]) {
						for (int i = 0; i < b; i++) {
							a[i] = !a[i];
						}
					}
					if (query(pAsym) != a[pAsym]) {
						for (int i = 0, j = b - 1; i < j; i++, j--) {
							boolean t = a[i];
							a[i] = a[j];
							a[j] = t;
						}
					}
				}
			}
			a[p] = query(p);
			a[b - p - 1] = query(b - p - 1);
			if (a[p] == a[b - p - 1]) {
				pSym = p;
			} else {
				pAsym = p;
			}
			++p;
		}
		for (int i = 0; i < b; i++) {
			out.print(a[i] ? '1' : '0');
		}
		out.println();
		out.flush();
		if (!"Y".equals(scanString())) {
			exit(0);
		}
	}

	static int scanInt() throws IOException {
		return parseInt(scanString());
	}

	static long scanLong() throws IOException {
		return parseLong(scanString());
	}

	static String scanString() throws IOException {
		while (tok == null || !tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	static void printCase() {
		out.print("Case #" + test + ": ");
	}

	static void printlnCase() {
		out.println("Case #" + test + ":");
	}

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
	static int test;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			int tests = scanInt();
			b = scanInt();
			for (test = 1; test <= tests; test++) {
				solve();
			}
			in.close();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			exit(1);
		}
	}
}