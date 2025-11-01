import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SegmentTreeforTwoArrayProblems {
    static class SegmentTree{
        int n;
        int arr[];
        int seg[];

        SegmentTree(int arr[]){
            this.arr = arr;
            this.n = arr.length;
            this.seg = new int[4*n];
            build(0,0,n-1);
        }

        private void build(int ind, int low, int high){
            if(low == high){
                seg[ind] = arr[low];
                return;
            }

            int mid = (low + high) >> 1;
            build(2*ind+1, low, mid);
            build(2*ind+2, mid+1, high);
            seg[ind] = Math.min(seg[2*ind+1], seg[2*ind+2]);
        }

        private int query(int ind, int low, int high, int l, int r){
            if(r<low || high<l) return Integer.MAX_VALUE;
            if(l<=low && high<=r) return seg[ind];

            int mid = (low + high) >> 1;
            int left = query(2*ind+1, low, mid, l, r);
            int right = query(2*ind+2, mid+1, high, l, r);

            return Math.min(left, right);
        }

        private void update(int ind, int low, int high, int i, int val){
            if(low == high){
                seg[ind] = val;
                return;
            }

            int mid = (low + high) >> 1;
            if (i <= mid) update(2 * ind + 1, low, mid, i, val);
            else update(2 * ind + 2, mid + 1, high, i, val);

            seg[ind] = Math.min(seg[2 * ind + 1], seg[2 * ind + 2]);
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st; 

        int n = Integer.parseInt(br.readLine());
        // ---- Read first array ----
        int[] arr1 = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) arr1[i] = Integer.parseInt(st.nextToken());

        // ---- Read second array ----
        int[] arr2 = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) arr2[i] = Integer.parseInt(st.nextToken());

        // ---- Build trees using constructor ----
        SegmentTree tree1 = new SegmentTree(arr1);
        SegmentTree tree2 = new SegmentTree(arr2);

        int q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                // Query both arrays
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int res1 = tree1.query(0, 0, n - 1, l, r);
                int res2 = tree2.query(0, 0, n - 1, l, r);
                sb.append(Math.min(res1, res2)).append("\n");
            } else if (type == 2) {
                // Update one of the arrays
                int arrNo = Integer.parseInt(st.nextToken());
                int idx = Integer.parseInt(st.nextToken());
                int val = Integer.parseInt(st.nextToken());

                if (arrNo == 1) tree1.update(0, 0, n - 1, idx, val);
                else tree2.update(0, 0, n - 1, idx, val);
            }
        }

        System.out.print(sb.toString());   
    }
}
