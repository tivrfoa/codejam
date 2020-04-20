import java.util.*;
import java.io.*;
import static java.lang.System.out;

/**
 * !!! INFINITE 2d
 * You can go to negative coordinates.
 */
public class Solution {

	private static Scanner sc;
	
	
	private static int jump(int j) {
		return 1 << (j-1);
	}

	private static void solve(int t) {
		
		int x = sc.nextInt();
		int y = sc.nextInt();

		if ((x % 2 != 0 && y % 2 != 0) ||
		    (x % 2 == 0 && y % 2 == 0)) {
			out.printf("Case #%d: IMPOSSIBLE\n", t);
			return;
		}
		
		LinkedList<State> states = new LinkedList<>();
		states.add(new State("", 0, 0, 0, 0));
		
		while (!states.isEmpty()) {
			State s = states.poll();
			
			if (s.i == x && s.j == y) {
				out.printf("Case #%d: %s\n", t, s.s);
				return;
			}

			if (s.jumps > 0 && s.i % 2 != 0 && s.j % 2 != 0) {
				continue;
			}

			if (s.jumps > 0 && s.i % 2 == 0 && s.j % 2 == 0) {
				continue;
			}

			if (s.jumps > 2 && ((x % 2 == 0 && s.i % 2 != 0) ||
							   (x % 2 != 0 && s.i % 2 == 0) ||
							   (y % 2 == 0 && s.j % 2 != 0) ||
							   (y % 2 != 0 && s.j % 2 == 0))) {
				continue;
			}

			if (s.jumps > 2 && ((x > 0 && s.i > 0 && x < s.i) ||
								(y > 0 && s.j > 0 && y < s.j) ||
								(x < 0 && s.i < 0 && s.i < x) ||
								(y < 0 && s.j < 0 && s.j < y))) {
				continue;
			}
			
			if (s.distance > Math.abs(x) + Math.abs(y)) {
				continue;
			}
			
			int j = jump(s.jumps+1);
			
			int tmp = s.j + j;
			if (!((tmp > 0 && y > 0 && y < tmp) ||
			      (tmp < 0 && y < 0 && tmp < y)))
				states.add(new State(s.s + "N", s.i, s.j + j, s.jumps+1,
						s.distance + jump(s.jumps+1)));
            tmp = s.j - j;
            if (!((tmp > 0 && y > 0 && y < tmp) ||
			      (tmp < 0 && y < 0 && tmp < y)))
				states.add(new State(s.s + "S", s.i, s.j - j, s.jumps+1,
						s.distance + jump(s.jumps+1)));
			tmp = s.i + j;
			if (!((tmp > 0 && x > 0 && x < tmp) ||
			      (tmp < 0 && x < 0 && tmp < x)))
				states.add(new State(s.s + "E", s.i + j, s.j, s.jumps+1,
						s.distance + jump(s.jumps+1)));
			tmp = s.i - j;
			if (!((tmp > 0 && x > 0 && x < tmp) ||
			      (tmp < 0 && x < 0 && tmp < x)))
				states.add(new State(s.s + "W", s.i - j, s.j, s.jumps+1,
						s.distance + jump(s.jumps+1)));
		}
		out.printf("Case #%d: IMPOSSIBLE\n", t);
	}

	public static void main(String[] args) throws Exception {

		sc = new Scanner(System.in);
		
		int T = Integer.parseInt(sc.next());
		// System.err.println(T + " " + b);

		for (int t = 1; t <= T; ++t) {
			solve(t);
		}

		sc.close();

		// tests();
	}
	
	private static class State {
		String s;
		int i, j, jumps, distance;
		
		public State(String s, int i, int j, int jumps, int distance) {
			this.s = s;
			this.i = i;
			this.j = j;
            this.jumps = jumps;
            this.distance = distance;
		}

		public String toString() {
			return s + " i=" + i + " j=" + j + " jumps: " + jumps;
		}
	}
}
