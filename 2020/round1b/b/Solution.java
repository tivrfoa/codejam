import java.util.*;
import java.io.*;
import static java.lang.System.out;

/**
 * 
 * 
 */
public class Solution {

	private static int a, b;
	private static final String MISS = "MISS";
	private static final String HIT = "HIT";
	private static final String CENTER = "CENTER";
	private static final String WRONG = "WRONG";
	private static final int LEFT_BORDER = -1_000_000_000;
	private static final int RIGHT_BORDER = 1_000_000_000;
	private static final int UP_BORDER = 1_000_000_000;

	/**
	 * I'll start from upper left and search all Y first
	 * 
	 */
	private static void solve(int t) {
		
		int r2 = a * 2;
		int currX = LEFT_BORDER + r2;
		int currY = UP_BORDER - r2;
		int cnt = 1;
		
		out.println(currX + " " + currY);
		String resp = sc.next();
		
		while (true) {
			if (resp.equals(MISS)) {
				currX += r2;
				if (currX > RIGHT_BORDER - r2) {
					currX = LEFT_BORDER + r2;
					currY -= r2;
				}
			} else if (resp.equals(HIT)) {
				break;
			} else if (resp.equals(CENTER)) {
				return;
			} else {
				System.exit(-1);
			}
			out.println(currX + " " + currY);
			resp = sc.next();
			if (++cnt > 300) {
				System.err.println("More than 300 tries ...");
				System.exit(-1);
			}
		}
		
		// HIT, then try to find center ...
		
		
		int x = 0, y = 0;
		// find Y first
		
		loopY:
		while (true) {			
			// find up boundary
			out.println(currX + " " + (currY+a));
			resp = sc.next();
			if (resp.equals(CENTER)) return;
			if (resp.equals(HIT)) { // currY == Y
				y = currY;
				break;
			}
			out.println(currX + " " + (currY-a));
			resp = sc.next();
			if (resp.equals(CENTER)) return;
			if (resp.equals(MISS)) { // currY == Y
				y = currY;
				break;
			}
			
		}
	}


	private static Scanner sc;
	
	public static void main(String[] args) throws Exception {

		sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		a = sc.nextInt();
		b = sc.nextInt();

		for (int t = 1; t <= T; ++t) {
			solve(t);
		}

		sc.close();

		// tests();
	}
}
