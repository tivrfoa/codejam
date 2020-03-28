import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    void solve() {
        int tc = in.nextInt();
        final int mod = (int) 1e9 + 7;
        for (int t = 0; t < tc; t++) {
            int n = in.nextInt();
            int[][] next = new int[2][n];
            List<Integer>[] g = new List[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 2; j++) {
                    next[j][i] = in.nextInt() - 1;
                    g[next[j][i]].add(i);
                }
            }
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            int[] q = new int[n];
            int qIt = 0, qSz = 0;
            q[qSz++] = 0;
            boolean[] canReach0 = new boolean[n];
            canReach0[0] = true;
            while (qIt < qSz) {
                int v = q[qIt++];
                for (int to : g[v]) {
                    if (!canReach0[to]) {
                        canReach0[to] = true;
                        q[qSz++] = to;
                    }
                }
            }
            boolean[] atLeastOne = new boolean[n];
            qSz = 0;
            qIt = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > 0 && canReach0[i]) {
                    atLeastOne[i] = true;
                    q[qSz++] = i;
                }
            }
            while (qIt < qSz) {
                int v = q[qIt++];
                for (int j = 0; j < 2; j++) {
                    int to = next[j][v];
                    if (!atLeastOne[to] && canReach0[to]) {
                        atLeastOne[to] = true;
                        q[qSz++] = to;
                    }
                }
            }
            boolean cycleFromZero = false;
            if (atLeastOne[0]) {
                int v = 0;
                while (true) {
//                    System.err.println("??");
                    int nxt = -1;
                    for (int i = 0; i < 2; i++) {
                        int to = next[i][v];
                        if (atLeastOne[to]) {
                            if (nxt == -1) {
                                nxt = to;
                            } else {
                                cycleFromZero = true;
                            }
                        }
                    }
                    if (nxt == -1 || nxt == 0 || cycleFromZero) {
                        break;
                    }
                    v = nxt;
                }
                next[0][0] = next[1][0] = -1;
            }
            order = new ArrayList<>();
            boolean[] was = new boolean[n];
            for (int v = 0; v < n; v++) {
                if (atLeastOne[v] && !was[v]) {
                    go(v, was, atLeastOne, next);
                }
            }
            Collections.reverse(order);
            int[] orderPos = new int[n];
            Arrays.fill(orderPos, -1);
            for (int i = 0; i < order.size(); i++) {
                orderPos[order.get(i)] = i;
            }
            boolean cycle = false;
            for (int i = 0; i < order.size(); i++) {
                int v = order.get(i);
                for (int j = 0; j < 2; j++) {
                    int to = next[j][v];
                    if (to == -1) {
                        continue;
                    }
                    if (orderPos[to] != -1 && orderPos[to] <= i) {
                        cycle = true;
                    }
                }
            }
            out.print("Case #" + (t + 1) + ": ");
            if (cycle || cycleFromZero) {
                out.println("UNBOUNDED");
            } else {
                for (int v : order) {
                    for (int j = 0; j < 2; j++) {
                        int to = next[j][v];
                        if (to == -1) {
                            continue;
                        }
                        a[to] += a[v];
                        a[to] %= mod;
                    }
                }
                out.println(a[0] % mod);
            }
        }
    }

    List<Integer> order;

    void go(int v, boolean[] was, boolean[] atLeastOne, int[][] next) {
        was[v] = true;
        for (int i = 0; i < 2; i++) {
            int to = next[i][v];
            if (to == -1 || was[to] || !atLeastOne[to]) {
                continue;
            }
            go(to, was, atLeastOne, next);
        }

        order.add(v);
    }

    void run() {
        try {
            in = new FastScanner(new File("Solution.in"));
            out = new PrintWriter(new File("Solution.out"));

            solve();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void runIO() {

        in = new FastScanner(System.in);
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return null;
                st = new StringTokenizer(s);
            }
            return st.nextToken();
        }

        boolean hasMoreTokens() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return false;
                st = new StringTokenizer(s);
            }
            return true;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) {
        new Solution().runIO();
    }
}
