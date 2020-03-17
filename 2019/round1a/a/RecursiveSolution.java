import java.util.*;
import java.io.*;

public class RecursiveSolution {

	private static Scanner sc;
	
	private static final String POSSIBLE = "POSSIBLE";
	private static final String IMPOSSIBLE = "IMPOSSIBLE";
	
	private static int R, C, numberOfCells;	
	private static boolean[][] visited;	
	private static Point[] orderCells;
		
	public static void main(String[] args) throws Exception {
		
		//sc = new Scanner(new File(args[0]));
		sc = new Scanner(System.in);		
		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {
		R = sc.nextInt();
		C = sc.nextInt();
		numberOfCells = R * C;
		visited = new boolean[R+1][C+1];
		orderCells = new Point[numberOfCells];
		
		for (int i = 0; i < numberOfCells; i++) {
			orderCells[i] = new Point();
		}
		
		for (int i = 1; i <= R; i++) {
			for (int j = 1; j <= C; j++) {
				if (solve2(i, j, 0)) {
					System.out.println(POSSIBLE);
					for (Point p : orderCells) {						
						System.out.println(p.a + " " + p.b);
					}
					return;
				}
			}
		}
		
		System.out.println(IMPOSSIBLE);
	}
	
	private static boolean solve2(int currentRow, int currentColumn, int position) {
		
		visited[currentRow][currentColumn] = true;
		orderCells[position].a = currentRow;
		orderCells[position].b = currentColumn;
		
		if (position == numberOfCells - 1) {
			return true;
		}
		
		for (int i = 1; i <= R; i++) {
			for (int j = 1; j <= C; j++) {
				if (isJumpValid(currentRow, currentColumn, i, j)) {
					if(solve2(i, j, position + 1)) return true;
				}
			}
		}
		
		// No more jumps, then remove from visited
		visited[currentRow][currentColumn] = false;
		
		return false;
	}
	
	private static boolean isJumpValid(int r1, int c1, int r2, int c2) {
		return !visited[r2][c2] &&
		       r1 != r2 &&
		       c1 != c2 &&
		       r1 - c1 != r2 - c2 &&
		       r1 + c1 != r2 + c2;
	}
	
	static class Point {
		int a, b;
		public Point() {}
		public Point(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public String toString() { return a + " " + b; }
	}

}
