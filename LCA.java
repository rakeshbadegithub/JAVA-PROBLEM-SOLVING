// LCA.java
import java.io.*;
import java.util.*;

public class LCA {
    static int LOG = 20;
    static int N;
    static ArrayList<Integer>[] tree;
    static int[][] up;
    static int[] depth;

    static void dfs(int u, int p) {
        up[u][0] = p;
        for (int i = 1; i < LOG; i++) up[u][i] = up[u][i-1] < 0 ? -1 : up[ up[u][i-1] ][i-1];
        for (int v : tree[u]) {
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dfs(v, u);
        }
    }

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) { int t = a; a = b; b = t; }
        int diff = depth[a] - depth[b];
        for (int i = 0; i < LOG; i++)
            if ((diff & (1<<i)) != 0) a = up[a][i];
        if (a == b) return a;
        for (int i = LOG-1; i >= 0; i--) {
            if (up[a][i] != up[b][i]) {
                a = up[a][i];
                b = up[b][i];
            }
        }
        return up[a][0];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(nm[0]), q = Integer.parseInt(nm[1]);
        N = n;
        LOG = 1;
        while ((1<<LOG) <= N) LOG++;
        tree = new ArrayList[n];
        for (int i=0;i<n;i++) tree[i]=new ArrayList<>();
        for (int i=0;i<n-1;i++) {
            String[] e = br.readLine().trim().split("\\s+");
            int u = Integer.parseInt(e[0]), v = Integer.parseInt(e[1]);
            tree[u].add(v);
            tree[v].add(u);
        }
        up = new int[n][LOG];
        depth = new int[n];
        for (int i=0;i<n;i++) Arrays.fill(up[i], -1);
        depth[0]=0;
        dfs(0, -1);
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<q;i++) {
            String[] qq = br.readLine().trim().split("\\s+");
            int a = Integer.parseInt(qq[0]), b = Integer.parseInt(qq[1]);
            sb.append(lca(a,b)).append('\n');
        }
        System.out.print(sb.toString());
    }
}
OUTPUT:
7 3
0 1
0 2
1 3
1 4
2 5
2 6
3 4
5 6
3 6

