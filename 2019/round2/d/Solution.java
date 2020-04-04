import java.util.*;
import java.io.*;
import static java.lang.System.out;

/**
 * lead -> metal #1 periodic table
 * 
 * 
 * It is UNBOUNDED if the output contains the input
 * 
 */ 
public class Solution {

	private static Scanner sc;
	
	private static final int PRIME = 1000000007;
	private static final int LEAD = 1;
	private static final String UNBOUNDED = "UNBOUNDED";
	
	private static final int TEN_TO_THE_5 = 100000;
	
	private static int m = 0;
	private static int[] r1 = new int[TEN_TO_THE_5];
	private static int[] r2 = new int[TEN_TO_THE_5];
	private static int[] g = new int[TEN_TO_THE_5];
	private static boolean[] leadRows = new boolean[TEN_TO_THE_5];
	private static boolean containsLead = false;

	public static void main(String[] args) throws Exception {
		
		sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {
		
		Arrays.fill(r1, 0);
		Arrays.fill(r2, 0);
		Arrays.fill(g, 0);
		Arrays.fill(leadRows, false);
		containsLead = false;
		
		m = sc.nextInt();
		
		for (int i = 0; i < m; i++) {
			r1[i] = sc.nextInt();
			r2[i] = sc.nextInt();
			if (r1[i] == 1 || r2[i] == 1) {
				leadRows[i] = true;
				containsLead = true;
			}
		}
		
		for (int i = 0; i < m; i++) {
			g[i] = sc.nextInt();
		}
		
		if (!containsLead) {
			out.printf("Case #%d: %d\n", t, g[0]%PRIME); // ? / PRIME ?
			return;
		}
		
		if (isUnbounded()) {
			out.printf("Case #%d: %s\n", t, UNBOUNDED);
			return;
		}
		
		
		/**
		 * I can do transformations and each time check if there is
		 * lead. I must have a flag indicating that some transformation
		 * happend.
		 * When no transformation happend, then I can return g[0].
		 * Must take care to not enter an infinite loop.
		 * I must only try to transform some metal that contains lead,
		 * and then some metal that contain that metal recursively.
		 * If it's not possible to get that metal, then try the next one.
		 */ 
		
		
		boolean inifite = transform(0, false);
		
		if (inifite)
			out.printf("Case #%d: %s\n", t, UNBOUNDED);
		else		
			out.printf("Case #%d: %d\n", t, g[0]%PRIME);
	}
	
	private static boolean transform(int i, boolean changed) {
		if (g[i] > 0 && r1[i] == 1) {			
			--g[i];
			++g[0];

			int secondMetal = r2[i];
			++g[secondMetal - 1];

			if (isUnbounded()) return true;
			if (doesMetalATransformsToMetalB(secondMetal, i + 1)) return true;
			changed = true;
		}
		
		if (changed) {
			if (i == m - 1)
				return transform(0, false);
			else
				return transform(i + 1, true);
		} else if (i < m - 1) {
			return transform(i + 1, false);
		} else {
			return false;
		}
	}

	private static boolean doesMetalATransformsToMetalB(int a, int b) {
		return r1[a-1] == b || r2[a-1] == b;
	}

	/**
	 * I think there must be a cycle where some other product produces lead and
	 * itself.
	 * 
	 */  
	private static boolean isUnbounded() {
		
		for (int i = 1; i < m; i++) {
			if (g[i] > 0 && r1[i] == 1 && r2[i] == i + 1)
				return true;
		}
		
		return false;
	}
	
	/**
	 * If no material produces lead (r1[i] != 1 && r2[i] != 1 for all i),
	 * then it can't be destroyed.
	 * 
	 * I must reduce from g everytime it is destroyed to produce other
	 * materials.
	 * I must also inscrease from g the other materials that are produced.
	 * 
	 * It can then become unbounded after the transformation.
	 */
	/*private static boolean canDestory() {
		
		for (int i = 0; i < m; i++) {
			if (g[i] > 0 && (r1[i] == 1 || r2[i] == 1))
				return true;
		}
		
		return false;
	}*/

}
