import java.io.*;
import java.util.*;

public class XeniaandBitOperations {

    static class SegmentTree {
        int[] seg;
        int n;

        SegmentTree(int n) {
            this.n = n;
            seg = new int[4 * n];
        }

        void build(int ind, int low, int high, int arr[], boolean useOrHere) {
            if (low == high) {
                seg[ind] = arr[low];
                return;
            }
            int mid = (low + high) >> 1;
            // toggle operation when going down
            build(2 * ind + 1, low, mid, arr, !useOrHere);
            build(2 * ind + 2, mid + 1, high, arr, !useOrHere);
            if (useOrHere) seg[ind] = seg[2 * ind + 1] | seg[2 * ind + 2];
            else seg[ind] = seg[2 * ind + 1] ^ seg[2 * ind + 2];
        }

        void update(int ind, int low, int high, int pos, int val, boolean useOrHere) {
            if (low == high) {
                seg[ind] = val;
                return;
            }
            int mid = (low + high) >> 1;
            if (pos <= mid) update(2 * ind + 1, low, mid, pos, val, !useOrHere);
            else update(2 * ind + 2, mid + 1, high, pos, val, !useOrHere);

            if (useOrHere) seg[ind] = seg[2 * ind + 1] | seg[2 * ind + 2];
            else seg[ind] = seg[2 * ind + 1] ^ seg[2 * ind + 2];
        }
    }

    public static void main(String[] args) throws Exception {
        FastReader fr = new FastReader();
        StringBuilder sb = new StringBuilder();

        int n = fr.nextInt();
        int m = fr.nextInt();
        int size = 1 << n;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = fr.nextInt();

        SegmentTree st = new SegmentTree(size);
        boolean useOrAtRoot = (n % 2 == 1);

        st.build(0, 0, size - 1, arr, useOrAtRoot);

        for (int q = 0; q < m; q++) {
            int p = fr.nextInt() - 1;
            int b = fr.nextInt();
            st.update(0, 0, size - 1, p, b, useOrAtRoot);
            sb.append(st.seg[0]).append('\n');
        }
        System.out.print(sb.toString());
    }

    // Fast reader
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        FastReader() { br = new BufferedReader(new InputStreamReader(System.in)); }
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
