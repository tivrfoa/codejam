import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Closeable;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
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
            RoaringYears solver = new RoaringYears();
            int testCount = Integer.parseInt(in.next());
            for (int i = 1; i <= testCount; i++)
                solver.solve(i, in, out);
            out.close();
        }
    }

    static class RoaringYears {
        String s;
        StringBuilder builder = new StringBuilder();
        String best = null;

        public void solve(int testNumber, FastInput in, FastOutput out) {
            long Y = in.rl();
            s = "" + Y;
            int len = s.length();
            best = null;

            for (int prefix = 1; prefix <= len; prefix++) {
                for (int i = 0; i <= 1; i++) {
                    long pv = Long.parseLong(s.substring(0, prefix)) + i;
                    compute(BigInteger.valueOf(pv));
                }
            }

            for (int i = 1; i <= 20; i++) {
                BigInteger cand = BigInteger.valueOf(10).pow(i);
                for (int j = 0; j < 20 && cand.compareTo(BigInteger.ZERO) > 0; j++, cand = cand.subtract(BigInteger.ONE)) {
                    compute(cand);
                }
            }

            out.printf("Case #%d: %s\n", testNumber, best);
        }

        public void compute(BigInteger start) {
            BigInteger pv = start;
            builder.setLength(0);
            builder.append(pv);
            pv = pv.add(BigInteger.ONE);
            builder.append(pv);
            while (compare(builder, s) <= 0) {
                pv = pv.add(BigInteger.ONE);
                builder.append(pv);
            }

            if (best == null || compare(best, builder) > 0) {
                best = builder.toString();
            }
        }

        public int compare(CharSequence a, CharSequence b) {
            int res = Integer.compare(a.length(), b.length());
            if (res == 0) {
                for (int i = 0; res == 0 && i < a.length(); i++) {
                    res = Integer.compare(a.charAt(i), b.charAt(i));
                }
            }
            return res;
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

        public long rl() {
            return readLong();
        }

        public long readLong() {
            boolean rev = false;

            skipBlank();
            if (next == '+' || next == '-') {
                rev = next == '-';
                next = read();
            }

            long val = 0;
            while (next >= '0' && next <= '9') {
                val = val * 10 - next + '0';
                next = read();
            }

            return rev ? val : -val;
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

        public FastOutput append(String c) {
            cache.append(c);
            afterWrite();
            return this;
        }

        public FastOutput printf(String format, Object... args) {
            return append(String.format(format, args));
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

