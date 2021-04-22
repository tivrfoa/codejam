import java.util.*;
import java.io.*;

public class Solution {

	City[] cities;
	ArrayList<Connection>[] connections;
	boolean[] visited;

	long charge(long a, int w, int l) {
		return w >= l ? a : 0;
	}

	long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static class City {
		Connection up;
	}

	static class Connection {
		final int X, Y, L;
		final long A;
		public Connection(int x, int y, int l, long a) {
			this.X = x;
			this.Y = y;
			this.L = l;
			this.A = a;
		}
	}

	/**
	* @return gcd of all the toll charges that Charles pays for that day.
	*/
	long toll_charges(int city_index, int w) {
		if (city_index == 1) return 0;
		City c = cities[city_index];
		Connection up = c.up;
		int city_dest = up.X != city_index ? up.X : up.Y;
		long pay = charge(up.A, w, up.L);
		long tmp1 = toll_charges(city_dest, w);
		return tmp1 == 0 ? pay : gcd(pay, tmp1);
	}

	/**
	*  DFS
	*/
	void set_up_connections(int city_destination) {
		visited[city_destination] = true;
		for (Connection c : connections[city_destination]) {
			int city_src = c.X != city_destination ? c.X : c.Y;
			if (visited[city_src]) continue;
			if (cities[city_src] == null) cities[city_src] = new City();
			cities[city_src].up = c;
			set_up_connections(city_src);
		}
	}

	void solve2() {
		final int N = in.nextInt();
		final int Q = in.nextInt();
		cities = new City[N+1];
		cities[1] = new City();
		connections = new ArrayList[N+1];
		for (int i = 0; i < connections.length; ++i)
			connections[i] = new ArrayList<Connection>();
		visited = new boolean[N+1];
		for (int i = 0; i < N - 1; ++i) {
			int x = in.nextInt();
			int y = in.nextInt();
			int l = in.nextInt();
			long a = in.nextLong();
			Connection c = new Connection(x, y, l, a);
			connections[x].add(c);
			connections[y].add(c);
		}

		// create a structure that I can easily query up the tree
		set_up_connections(1);

		long[] ans = new long[Q];
		for (int i = 0; i < Q; ++i) {
			int c = in.nextInt();
			int w = in.nextInt();
			ans[i] = toll_charges(c, w);
		}

		int i;
		for (i = 0; i < ans.length - 1; ++i)
			out.print(ans[i] + " ");
		out.println(ans[i]);
	}

	void solve() {
		int T = in.nextInt();
		for (int t = 1; t <= T; ++t) {
			out.printf("Case #%d: ", t);
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

	void readArray(int[] a) {
		for (int i = 0; i < a.length; ++i) a[i] = in.nextInt();
	}

}
