// SegmentTreeLazy.java
import java.io.*;
import java.util.*;

public class SegmentTreeLazy {
    static class SegTree {
        long[] tree, lazy;
        int n;
        SegTree(int n, int[] arr) {
            this.n = n;
            tree = new long[4*n];
            lazy = new long[4*n];
            build(1,0,n-1,arr);
        }
        void build(int node,int l,int r,int[] a){
            if(l==r){ tree[node]=a[l]; return;}
            int m=(l+r)>>1;
            build(node<<1,l,m,a);
            build(node<<1|1,m+1,r,a);
            tree[node]=tree[node<<1]+tree[node<<1|1];
        }
        void apply(int node,int l,int r,long val){
            tree[node] += val * (r-l+1);
            lazy[node] += val;
        }
        void push(int node,int l,int r){
            if(lazy[node]!=0){
                int m=(l+r)>>1;
                apply(node<<1,l,m,lazy[node]);
                apply(node<<1|1,m+1,r,lazy[node]);
                lazy[node]=0;
            }
        }
        void update(int node,int l,int r,int ql,int qr,long val){
            if(ql>r || qr<l) return;
            if(ql<=l && r<=qr){ apply(node,l,r,val); return; }
            push(node,l,r);
            int m=(l+r)>>1;
            update(node<<1,l,m,ql,qr,val);
            update(node<<1|1,m+1,r,ql,qr,val);
            tree[node]=tree[node<<1]+tree[node<<1|1];
        }
        long query(int node,int l,int r,int ql,int qr){
            if(ql>r || qr<l) return 0;
            if(ql<=l && r<=qr) return tree[node];
            push(node,l,r);
            int m=(l+r)>>1;
            return query(node<<1,l,m,ql,qr)+query(node<<1|1,m+1,r,ql,qr);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine().trim());
        int[] a=Arrays.stream(br.readLine().trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int q=Integer.parseInt(br.readLine().trim());
        SegTree st=new SegTree(n,a);
        for(int i=0;i<q;i++){
            String[] parts=br.readLine().trim().split("\\s+");
            if(parts[0].equalsIgnoreCase("update")){
                int l=Integer.parseInt(parts[1]), r=Integer.parseInt(parts[2]);
                long v=Long.parseLong(parts[3]);
                st.update(1,0,n-1,l,r,v);
            } else {
                int l=Integer.parseInt(parts[1]), r=Integer.parseInt(parts[2]);
                System.out.println(st.query(1,0,n-1,l,r));
            }
        }
    }
}
INPUT:
5
1 2 3 4 5
3
update 1 3 10
query 0 4
query 2 2
OUPUT:
40
13

