import java.util.*;

import static java.lang.Math.abs;

/**
 * Each uppercase letter maps to a digit between 0 and 9
 *
 * Digit string D: 10 different letters
 */ 
public class Solution {
   
    
    private static int randomQuery(int M) {
        String m = "" + M;

        Random rand = new Random();
        int n = rand.nextInt(M + 1);

        return -1;
    }

    private static void solve(int t) {
    }


    private static Scanner sc;
    public static void main(String args[]) throws Exception {
        sc = new Scanner(System.in);
        
        int T = sc.nextInt();

        for (int t = 1; t <= T; ++t) {
            solve(t);
        }
    }
}

