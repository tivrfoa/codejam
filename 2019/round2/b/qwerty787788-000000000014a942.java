import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    void solve() {
        int tc = in.nextInt();
        for (int t = 0; t < tc; t++) {
            final int MAGIC = 57;
            final int HALF = 9;
            final int n = 20;
            int it = 0;
            double[] b = new double[n];
            int my = -1;
            for (int i = 0; i < 100; i++) {
                if (in.nextInt() - 1 != i) {
                    throw new AssertionError();
                }
                if (i < MAGIC) {
                    out.println((it + 1) + " " + 100);
                    out.flush();
                    it++;
                    it %= HALF;
                } else if (i < MAGIC + (n - HALF)) {
                    int pos = i - MAGIC + HALF;
                    out.println((pos + 1) + " " + 0);
                    out.flush();
                    int xx = in.nextInt();
                    for (int j = 0; j < xx; j++) {
                        in.nextInt();
                    }
                    b[pos] = xx + (b.length - pos - 1.0) / n;
                } else if (i != 99) {
                    my = HALF;
                    for (int j = HALF + 1; j < n; j++) {
                        if (b[j] < b[my]) {
                            my = j;
                        }
                    }
                    int use = -1;
                    for (int j = HALF; j < n; j++) {
                        if (j != my) {
                            if (use == -1 || b[use] > b[j]) {
                                use = j;
                            }
                        }
                    }
                    b[use]++;
                    out.println((use + 1) + " " + 100);
                    out.flush();
                } else {
                    out.println((my + 1) + " " + 100);
                    out.flush();
                }
            }
        }
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
