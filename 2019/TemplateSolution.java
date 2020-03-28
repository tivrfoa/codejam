import java.util.*;
import java.io.*;

public class Solution {

	private static Scanner sc;
		
	public static void main(String[] args) throws Exception {
		
		sc = new Scanner(System.in);		
		
		int T = sc.nextInt();
		
		for (int t = 1; t <= T; ++t) {
			solve(t);			
		}
		
		sc.close();
	}
	
	private static void solve(int t) {
		
		for (int i = 0; i < numberOfCells; i++) {
			orderCells[i] = new Point();
		}
		
		System.out.println(IMPOSSIBLE);
	}

}
