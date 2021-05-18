import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.ArrayList;
import java.io.Writer;
import java.io.BufferedReader;
import java.util.Comparator;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Niyaz Nigmatullin
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        FastPrinter out = new FastPrinter(outputStream);
        TaskD solver = new TaskD();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class TaskD {
        public void solve(int testNumber, FastScanner in, FastPrinter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            int f = in.nextInt();
            int s = in.nextInt();
            char[][] from = new char[n][];
            for (int i = 0; i < n; i++) {
                from[i] = in.next().toCharArray();
            }
            char[][] to = new char[n][];
            for (int i = 0; i < n; i++) {
                to[i] = in.next().toCharArray();
            }
            MinCostMaxFlowGraph g = new MinCostMaxFlowGraph(n * m + 2);
            int src = n * m;
            int tar = src + 1;
            int[][] type = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (from[i][j] != to[i][j]) {
                        type[i][j] = from[i][j] == 'M' ? 0 : 1;
                    } else {
                        type[i][j] = -1;
                    }
                }
            }
            long ans = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (type[i][j] == 0) {
                        g.addEdge(src, i * m + j, 1, -f);
                        ans += f;
                    } else if (type[i][j] == 1) {
                        g.addEdge(i * m + j, tar, 1, -f);
                        ans += f;
                    }
                }
            }
            for (int v = 0; v < n * m; v++) {
                if (type[v / m][v % m] != 0) continue;
                for (int u = 0; u < n * m; u++) {
                    if (v == u) continue;
                    if (type[u / m][u % m] != 1) continue;
                    g.addEdge(v, u, 1, (long) (Math.abs(v / m - u / m) + Math.abs(v % m - u % m)) * s);
                }
            }
            ans += g.getMinCostFlow(src, tar)[1];
            out.println("Case #" + testNumber + ": " + ans);
        }

    }

    static class FastScanner extends BufferedReader {
        public FastScanner(InputStream is) {
            super(new InputStreamReader(is));
        }

        public int read() {
            try {
                int ret = super.read();
//            if (isEOF && ret < 0) {
//                throw new InputMismatchException();
//            }
//            isEOF = ret == -1;
                return ret;
            } catch (IOException e) {
                throw new InputMismatchException();
            }
        }

        public String next() {
            StringBuilder sb = new StringBuilder();
            int c = read();
            while (isWhiteSpace(c)) {
                c = read();
            }
            if (c < 0) {
                return null;
            }
            while (c >= 0 && !isWhiteSpace(c)) {
                sb.appendCodePoint(c);
                c = read();
            }
            return sb.toString();
        }

        static boolean isWhiteSpace(int c) {
            return c >= 0 && c <= 32;
        }

        public int nextInt() {
            int c = read();
            while (isWhiteSpace(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int ret = 0;
            while (c >= 0 && !isWhiteSpace(c)) {
                if (c < '0' || c > '9') {
                    throw new NumberFormatException("digit expected " + (char) c
                            + " found");
                }
                ret = ret * 10 + c - '0';
                c = read();
            }
            return ret * sgn;
        }

        public String readLine() {
            try {
                return super.readLine();
            } catch (IOException e) {
                return null;
            }
        }

    }

    static class MinCostMaxFlowGraph {
        int n;
        ArrayList<MinCostMaxFlowGraph.Edge>[] edges;

        public MinCostMaxFlowGraph(int n) {
            this.n = n;
            edges = new ArrayList[n];
            for (int i = 0; i < edges.length; i++) {
                edges[i] = new ArrayList<MinCostMaxFlowGraph.Edge>();
            }
        }

        public MinCostMaxFlowGraph.Edge addEdge(int from, int to, int cap, long cost) {
            MinCostMaxFlowGraph.Edge e1 = new MinCostMaxFlowGraph.Edge(from, to, 0, cap, cost);
            MinCostMaxFlowGraph.Edge e2 = new MinCostMaxFlowGraph.Edge(to, from, 0, 0, -cost);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
            return e1;
        }

        public long[] getMinCostFlow(int source, int target) {
            long[] h = new long[n];
            for (boolean changed = true; changed; ) {
                changed = false;
                for (int i = 0; i < n; i++) {
                    for (MinCostMaxFlowGraph.Edge e : edges[i]) {
                        if (e.cap > 0 && h[e.to] > h[e.from] + e.cost) {
                            h[e.to] = h[e.from] + e.cost;
                            changed = true;
                        }
                    }
                }
            }
            MinCostMaxFlowGraph.Edge[] lastEdge = new MinCostMaxFlowGraph.Edge[n];
            long[] d = new long[n];
            int flow = 0;
            long cost = 0;
            while (true) {
                dijkstra(source, lastEdge, d, h);
                if (d[target] == Long.MAX_VALUE) {
                    break;
                }
                long curCost = d[target] + h[target] - h[source];
                if (curCost >= 0) {
                    break;
                }
                int addFlow = Integer.MAX_VALUE;
                for (int v = target; v != source; ) {
                    MinCostMaxFlowGraph.Edge e = lastEdge[v];
                    addFlow = Math.min(addFlow, e.cap - e.flow);
                    v = e.from;
                }
                cost += curCost * addFlow;
                flow += addFlow;
                for (int v = target; v != source; ) {
                    MinCostMaxFlowGraph.Edge e = lastEdge[v];
                    e.flow += addFlow;
                    e.rev.flow -= addFlow;
                    v = e.from;
                }
                for (int i = 0; i < n; i++) {
                    h[i] += d[i] == Long.MAX_VALUE ? 0 : d[i];
                }
            }
            return new long[]{flow, cost};
        }

        void dijkstra(int source, MinCostMaxFlowGraph.Edge[] lastEdge, final long[] d, long[] h) {
            TreeSet<Integer> ts = new TreeSet<Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    if (d[o1] != d[o2]) {
                        return d[o1] < d[o2] ? -1 : 1;
                    }
                    return o1 - o2;
                }
            });
            Arrays.fill(d, Long.MAX_VALUE);
            d[source] = 0;
            ts.add(source);
            while (!ts.isEmpty()) {
                int v = ts.pollFirst();
                for (MinCostMaxFlowGraph.Edge e : edges[v]) {
                    if (e.flow >= e.cap) {
                        continue;
                    }
                    if (d[e.to] == Long.MAX_VALUE
                            || d[e.to] > d[e.from] + e.cost + h[e.from]
                            - h[e.to]) {
                        if (e.cost + h[e.from] - h[e.to] < 0) {
                            throw new AssertionError();
                        }
                        ts.remove(e.to);
                        d[e.to] = d[e.from] + e.cost + h[e.from] - h[e.to];
                        lastEdge[e.to] = e;
                        ts.add(e.to);
                    }
                }
            }
        }

        public static class Edge {
            int from;
            int to;
            public int flow;
            int cap;
            long cost;
            MinCostMaxFlowGraph.Edge rev;

            public Edge(int from, int to, int flow, int cap, long cost) {
                this.from = from;
                this.to = to;
                this.flow = flow;
                this.cap = cap;
                this.cost = cost;
            }

            public String toString() {
                return "Edge [from=" + from + ", to=" + to + ", flow=" + flow
                        + ", cap=" + cap + ", cost=" + cost + "]";
            }

        }

    }

    static class FastPrinter extends PrintWriter {
        public FastPrinter(OutputStream out) {
            super(out);
        }

        public FastPrinter(Writer out) {
            super(out);
        }

    }
}

