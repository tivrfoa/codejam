import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    class Elem implements Comparable<Elem> {
        int x, y;
        int pos;

        public Elem(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Elem elem) {
            if (x != elem.x) {
                return Integer.compare(x, elem.x);
            }
            return Integer.compare(y, elem.y);
        }
    }

    class Event implements Comparable<Event> {
        Elem left, right;
        long up, down;

        public Event(Elem left, Elem right, long up, long down) {
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }

        @Override
        public int compareTo(Event event) {
            return Long.compare(up * event.down, event.up * down);
        }
    }

    PriorityQueue<Event> pq;

    void addEvent(int pos, Elem[] elem) {
        Elem left = elem[pos], right = elem[pos + 1];
        if (left.y <= right.y) {
            return;
        }
        if (right.x - left.x < 0) {
            throw new AssertionError();
        }
        Event e = new Event(left, right, right.x - left.x, left.y - right.y);
        pq.add(e);
    }

    void swap(Elem[] a, int pos) {
        Elem left = a[pos], right = a[pos + 1];
        a[pos] = right;
        a[pos + 1] = left;
        left.pos++;
        right.pos--;
        if (pos != 0) {
            addEvent(pos - 1, a);
        }
        if (pos + 2 < a.length) {
            addEvent(pos + 1, a);
        }
    }

    void solve() {
        int tc = in.nextInt();
        for (int t = 0; t < tc; t++) {
            int n = in.nextInt();
            Elem[] a = new Elem[n];
            for (int i = 0; i < n; i++) {
                a[i] = new Elem(in.nextInt(), in.nextInt());
            }
            Arrays.sort(a);
            for (int i = 0; i < n; i++) {
                a[i].pos = i;
            }
            pq = new PriorityQueue<>();
            for (int i = 0; i + 1 < n; i++) {
                addEvent(i, a);
            }
            int res = 1;
            while (!pq.isEmpty()) {
                Event startEvent = pq.peek();
                boolean changed = false;
                while (!pq.isEmpty()) {
                    Event e = pq.peek();
                    if (e.compareTo(startEvent) != 0) {
                        break;
                    }
                    pq.poll();
                    if (e.left.pos + 1 == e.right.pos) {
                        swap(a, e.left.pos);
                        changed = true;
                    }
                }
                if (changed) {
                    res++;
                }
            }
            out.println("Case #" + (t + 1) + ": " + res);
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
