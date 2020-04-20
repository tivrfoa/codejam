import java.util.*;
import java.io.*;

class Solution {

    private static final int bx = -1_000_000_000;
    private static final int by = -1_000_000_000;
    private static final int cx = 1_000_000_000;
    private static final int cy = 1_000_000_000;

    private static int a, b, r, d;
    private static boolean done;
    private static TreeMap<Memo, Integer> memo;

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

    private static class Memo implements Comparable<Memo> {
        int x, y;

        public Memo(int x, int y) { this.x = x; this.y = y; }

        public boolean equals(Object m) {
            Memo m2 = (Memo) m;
            return x == m2.x && y == m2.y;
        }

        public int compareTo(Memo m) {
            if (x == m.x && y == m.y) return 0;
            if (x < m.x && y < m.y) return -1;
            if (x == m.x && y < m.y) return -1;
            return 1;
        }
    }

    private static int query(int x, int y) {
        if (x < bx || x > cx || y < by || y > cy) return 0;
        if (done) return 0;
        Memo m = new Memo(x,y);
        Integer v = memo.get(m);
        if (v != null) return v;
        System.out.println(x + " " + y);
        switch (sc.next()) {
            case "MISS":
                memo.put(m, 0);
                return 0;
            case "HIT":
                memo.put(m, 1);
                return 1;
            case "CENTER":
                done = true;
                memo.put(m, -1);
                return -1;
            default:
                System.err.println("wrong ...");
                System.exit(-1);
                return 0;
        }
    }

    private static void solve() {

        done = false;
        int[] at = {-1, -1};
        memo = new TreeMap<>();

        loop1:
        for (int i = bx + r; i <= cx; i+=r) {
            for (int j = by + r; j <= cy; j+=r) {
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

        if (!done) {
            int at_x = (lx + rx) >> 1;
            int at_y = (ly + ry) >> 1;
            for (int dx = -2; dx <= 2; ++dx)
                for (int dy = -2; dy <= 2; ++dy)
                    query(at_x + dx, at_y + dy);
        }
    }
}

