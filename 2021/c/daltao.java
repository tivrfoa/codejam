import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Closeable;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class daltao {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(null, new TaskAdapter(), "", 1 << 29);
        thread.start();
        thread.join();
    }

    static class TaskAdapter implements Runnable {
        @Override
        public void run() {
            InputStream inputStream = System.in;
            OutputStream outputStream = System.out;
            FastInput in = new FastInput(inputStream);
            FastOutput out = new FastOutput(outputStream);
            DoubleOrNOTing solver = new DoubleOrNOTing();
            int testCount = Integer.parseInt(in.next());
            for (int i = 1; i <= testCount; i++)
                solver.solve(i, in, out);
            out.close();
        }
    }

    static class DoubleOrNOTing {
        public void solve(int testNumber, FastInput in, FastOutput out) {
            out.printf("Case #%d: ", testNumber);

            String s = in.rs();
            String e = in.rs();

            int extra = 0;
            String impossible = "IMPOSSIBLE";
            if (s.equals("0")) {
                if (e.equals("0")) {
                    out.println(0);
                    return;
                }
                s = "1";
                extra++;
            }
            if (e.equals("0")) {
                e = "";
            }

            long inf = (long) 1e18;
            long ans = inf;
            for (int i = 0; i <= s.length() && i <= e.length(); i++) {
                String shead = s.substring(0, s.length() - i);
                String stail = s.substring(s.length() - i, s.length());

                int chance = diff(shead, false);
                if (shead.length() > 0) {
                    chance++;
                }
                String er = chance % 2 == 0 ? e : reverse(e);
                String ehead = er.substring(0, i);
                String etail = e.substring(i, e.length());
                if (!stail.equals(ehead)) {
                    continue;
                }
                if (shead.length() > 0 && stail.length() > 0 && shead.charAt(shead.length() - 1) == stail.charAt(0)) {
                    continue;
                }
                int req = diff(etail, true);
                if (req > chance) {
                    continue;
                }
                long cand = chance + etail.length();
                ans = Math.min(ans, cand);
            }
            if (ans == inf) {
                out.println(impossible);
                return;
            }
            out.println(ans + extra);
        }

        public String reverse(String s) {
            StringBuilder ans = new StringBuilder(s.length());
            for (char c : s.toCharArray()) {
                ans.append((char) ('1' - c + '0'));
            }
            return ans.toString();
        }

        public int diff(String s, boolean endWithZero) {
            if (endWithZero) {
                s += "0";
            }
            int ans = 0;
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) != s.charAt(i - 1)) {
                    ans++;
                }
            }
            return ans;
        }

    }

    static class FastInput {
        private final InputStream is;
        private StringBuilder defaultStringBuf = new StringBuilder(1 << 13);
        private byte[] buf = new byte[1 << 13];
        private int bufLen;
        private int bufOffset;
        private int next;

        public FastInput(InputStream is) {
            this.is = is;
        }

        private int read() {
            while (bufLen == bufOffset) {
                bufOffset = 0;
                try {
                    bufLen = is.read(buf);
                } catch (IOException e) {
                    bufLen = -1;
                }
                if (bufLen == -1) {
                    return -1;
                }
            }
            return buf[bufOffset++];
        }

        public void skipBlank() {
            while (next >= 0 && next <= 32) {
                next = read();
            }
        }

        public String next() {
            return readString();
        }

        public String rs() {
            return readString();
        }

        public String readString(StringBuilder builder) {
            skipBlank();

            while (next > 32) {
                builder.append((char) next);
                next = read();
            }

            return builder.toString();
        }

        public String readString() {
            defaultStringBuf.setLength(0);
            return readString(defaultStringBuf);
        }

    }

    static class FastOutput implements AutoCloseable, Closeable, Appendable {
        private static final int THRESHOLD = 32 << 10;
        private final Writer os;
        private StringBuilder cache = new StringBuilder(THRESHOLD * 2);

        public FastOutput append(CharSequence csq) {
            cache.append(csq);
            return this;
        }

        public FastOutput append(CharSequence csq, int start, int end) {
            cache.append(csq, start, end);
            return this;
        }

        private void afterWrite() {
            if (cache.length() < THRESHOLD) {
                return;
            }
            flush();
        }

        public FastOutput(Writer os) {
            this.os = os;
        }

        public FastOutput(OutputStream os) {
            this(new OutputStreamWriter(os));
        }

        public FastOutput append(char c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput append(int c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput append(long c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput append(String c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput printf(String format, Object... args) {
            return append(String.format(format, args));
        }

        public FastOutput println(String c) {
            return append(c).println();
        }

        public FastOutput println(int c) {
            return append(c).println();
        }

        public FastOutput println(long c) {
            return append(c).println();
        }

        public FastOutput println() {
            return append('\n');
        }

        public FastOutput flush() {
            try {
//            boolean success = false;
//            if (stringBuilderValueField != null) {
//                try {
//                    char[] value = (char[]) stringBuilderValueField.get(cache);
//                    os.write(value, 0, cache.length());
//                    success = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!success) {
                os.append(cache);
//            }
                os.flush();
                cache.setLength(0);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return this;
        }

        public void close() {
            flush();
            try {
                os.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public String toString() {
            return cache.toString();
        }

    }
}

