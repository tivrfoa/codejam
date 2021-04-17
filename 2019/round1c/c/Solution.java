import java.util.*;
import java.io.*;

public class Solution {
	
	static HashMap<State, Integer> mem = new HashMap<>();

	private static class State {
		int num_radioactive = 0;
		char[][] matrix;
		int first_player_winning_moves = 0;

		public int set_num_radioactive() {
			for (int i = 0; i < this.matrix.length; ++i) {
				for (int j = 0; j < this.matrix.length; ++j) {
					if (this.matrix[i][j] == '#') ++this.num_radioactive;
				}
			}
			return this.num_radioactive;
		}

		public static State read(int R, int C) {
			State s = new State();
			s.matrix = new int[R][C];
			for (int i = 0; i < R; ++i) {
				s.matrix[i] = in.nextToken().toCharArray();
				for (int j = 0; j < C; ++j) {
					if (s.matrix[i][j] == '#') ++s.num_radioactive;
				}
			}
			return s;
		}
		
		public State() {}

		public State(char[][] m) {
			this.matrix = m;
			this.set_num_radioactive();
		}

		public State(int n, int[][] m) {
			this.num_radioactive = n;
			this.matrix = m;
		}

		public boolean equals(Object other) {
			State that = (State) other;
			if (this.num_radioactive != that.num_radioactive) return false;
			if (this.m.length != that.m.length || this.m[0].length != that.m[0].length) return false;
			for (int i = 0; i < m.length; ++i) {
				for (int j = 0; j < m[i].length; ++j) {
					if (m[i][j] != that.m[i][j]) return false;
				}
			}
			return true;
		}

		public State copy(int r1, int r2, int c1, int c2) {
			State s = new State();
			s.matrix = new char[r2 - r1 + 1][c2 - c1 + 1];
			for (int i = 0, r = r1; r <= r2; ++i, ++r)
				for (int j = 0, c = c1; c <= c2; ++j, ++c)
					s.matrix[i][j] = this.matrix[r][c];

			return s;
		}
	}

	// If there is at least one move where next player won, then set to 0, otherwise sum victories.
	int find_winning_moves(State s, boolean first_to_move) {
		Integer wn = mem.get(s);
		if (wn != null) return wn;
		/* if (wn != null) {
			if (first_to_move) return wn;
			return 0;
		}*/

		int ans = 0;
		char[][] m = s.matrix;
		int R = m.length, C = m[0].length;

		// Try horizontal
		for (int r = 0; r < R; ++r) {
			boolean ok = true;
			for (int c = 0; c < C; ++c) {
				if (m[r][c] == '#') {
					ok = false;
					break;
				}
				if (ok) {
					boolean bad = false;
					// split two sub-problems
					if (r == 0) {
						if (r < R - 1) {
							State ns = s.copy(r + 1, R - 1, 0, C - 1);
							int tmp = find_winning_moves(ns, !first_to_move);
							if (first_to_move && tmp > 0) bad = true;
						}
					} else if (r == R - 1) {
						State ns = s.copy(0, R - 1, 0, C - 1);
						int tmp = find_winning_moves(ns, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;
					} else {
						State ns1 = s.copy(0, r - 1, 0, C - 1);
						int tmp = find_winning_moves(ns1, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;

						State ns2 = s.copy(r + 1, R - 1, 0, C - 1);
						tmp = find_winning_moves(ns2, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;
					}

					if (!bad) ans += 1;
				}
			}
		}

		// Try vertical
		for (int c = 0; c < C; ++c) {
			boolean ok = true;
			for (int r = 0; r < R; ++r) {
				if (m[c][r] == '#') {
					ok = false;
					break;
				}
				if (ok) {
					boolean bad = false;
					// split two sub-problems
					if (c == 0) {
						if (c < C - 1) {
							// right
							State ns = s.copy(0, R - 1, c + 1, C - 1);
							int tmp = find_winning_moves(ns, !first_to_move);
							if (first_to_move && tmp > 0) bad = true;
						}
					} else if (c == C - 1) {
						// left
						State ns = s.copy(0, R - 1, 0, c - 1);
						int tmp = find_winning_moves(ns, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;
					} else {
						// left
						State ns1 = s.copy(0, R - 1, 0, c - 1);
						int tmp = find_winning_moves(ns1, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;

						// right
						State ns = s.copy(0, R - 1, c + 1, C - 1);
						tmp = find_winning_moves(ns2, !first_to_move);
						if (first_to_move && tmp > 0) bad = true;
					}

					if (!bad) ans += 1;
				}
			}
		}

		mem.put(s, ans);
		return ans;
	}

	
	int solve2() {
		int ans = 0;
		int R = in.nextInt();
		int C = in.nextInt();
		State s = State.read(R, C);
		return find_winning_moves(s);
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
