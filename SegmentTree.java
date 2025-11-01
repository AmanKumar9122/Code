import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SegmentTree {

    // function for building the segment tree....
    public void build(int ind, int low ,int high , int arr[] ,int seg[]){
        // base case 
        if(low == high){
            seg[ind] = arr[low];
            return;
        }

        int mid = (low + high) >> 1;
        build(2*ind+1, low, mid, arr, seg);
        build(2*ind+2, mid+1, high, arr, seg);

        seg[ind] = Math.min(seg[2*ind+1], seg[2*ind+2]);
    }
    // Time Complexity : nearly about 0(n)

    // function for performing queries....
    public int query(int ind, int low, int high, int l, int r,int seg[]){
        // no overlap
        // l r low high    or    low high l r
        if( r<low || high<l){
            return Integer.MAX_VALUE;
        }

        // complete overlap
        // l low high r
        if( l<=low && high<=r ){
            return seg[ind];
        }

        // partial overlap
        int mid = (low + high) >> 1;
        int left = query(2*ind+1, low, mid, l, r, seg);
        int right = query(2*ind+2, mid+1, high, l, r, seg);
        return Math.min(left, right);
    }
    // Time Complexity : O(logN) in all the query range (high og tree is O(logN))

    // function for updating at particular index 
    public void update(int ind, int low, int high, int i, int val, int seg[]){
        if(low == high){
            seg[ind] = val;
            return;
        }

        int mid = (low + high) >> 1;
        if(i <= mid) update(2*ind+1, low, mid, i, val, seg);
        else update(2*ind+2, mid+1, high, i, val, seg);

        seg[ind] = Math.min(seg[2*ind+1], seg[2*ind+2]);

    }
    // Time Complexity : O(logN)

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int [] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int seg[] = new int[4*n];
        SegmentTree tree = new SegmentTree();
        tree.build(0, 0, n-1, arr, seg);

        int q = Integer.parseInt(br.readLine());
        while(q-- > 0){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if(type == 1){
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int ans = tree.query(0, 0, n-1, l, r, seg);
                sb.append(ans).append("\n");
            }
            else if (type == 2){
                int idx = Integer.parseInt(st.nextToken());
                int val = Integer.parseInt(st.nextToken());
                tree.update(0, 0, n-1, idx, val, seg);
            }
        }
        
        System.out.println(sb.toString());
    }
}
// Time Complexity of overall : O(n + qlogn) where n is for building the tree and qlogn is for q queries each taking logn time.
// Space Complexity : O(4n) for segment tree array.