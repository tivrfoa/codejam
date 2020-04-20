import java.util.*;
import java.io.*;

class Solution {

    private static final int bx = -1_000_000_000;
    private static final int by = -1_000_000_000;
    private static final int cx = 1_000_000_000;
    private static final int cy = 1_000_000_000;

    private static int a, b, r, d;
    private static boolean done;

    private static Scanner sc;
    
    public static void main(String args[]) throws Exception {

        sc = new Scanner(System.in);
        int T = sc.nextInt();
        a = sc.nextInt();
        b = sc.nextInt();

        r = a/2;
        d = a*2;

        for (int t = 0; t < T; ++t)
            solve();
    }

    private static int query(int x, int y) {
        if (done) return 0;
        System.out.println(x + " " + y);
        switch (sc.next()) {
            case "MISS":
                return 0;
            case "HIT":
                return 1;
            case "CENTER":
                done = true;
                return -1;
            default:
                System.err.println("wrong ...");
                System.exit(-1);
                return 0;
        }
    }

    private static void solve() {
        

        done = false;
        int[] at = new int[2];
        at[0] = -1;
        at[1] = -1;

        loop1:
        for (int i = bx + a; i <= cx; i+=a) {
            for (int j = by + a; j <= cy; j+=a) {
                if (query(i, j) != 0) {
                    at[0] = i;
                    at[1] = j;
                    break loop1;
                }
            }
        }

        int lx, rx, ly, ry;
        {
            int lo = bx, hi = at[1] - 1, ans = at[0];
            while (lo <= hi) {
                int mid = (lo + hi) >> 1;
                if (query(mid, at[1]) != 0) {
                    ans = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            lx = ans;
        }
        {
            int lo = at[0] + 1, hi = cx, ans = at[0];
            while (lo <= hi) {
                int mid = (lo + hi) >> 1;
                if (query(mid, at[1]) != 0) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            rx = ans;
        }
        {
            int lo = bx, hi = at[1] - 1, ans = at[1];
            while (lo <= hi) {
                int mid = (lo + hi) >> 1;
                if (query(at[0], mid) != 0) {
                    ans = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            ly = ans;
        }
        {
            int lo = at[1] + 1, hi = cx, ans = at[1];
            while (lo <= hi) {
                int mid = (lo + hi) >> 1;
                if (query(at[0], mid) != 0) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            ry = ans;
        }

        query((lx+rx)>>1, (ly+ry)>>1);

        if (!done) {
            System.exit(-1);
        }
    }
}

