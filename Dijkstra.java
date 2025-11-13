// Dijkstra.java
import java.io.*;
import java.util.*;

public class Dijkstra {
    static class Edge { int to; int w; Edge(int t,int w){this.to=t;this.w=w;} }
    public static int[] dijkstra(List<Edge>[] graph, int src) {
        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a->a[0]));
        pq.add(new int[]{0, src});
        while (!pq.isEmpty()) {
            var cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (d != dist[u]) continue;
            for (Edge e : graph[u]) {
                int v = e.to, w = e.w;
                if (dist[v] > d + w) {
                    dist[v] = d + w;
                    pq.add(new int[]{dist[v], v});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(line[0]), m = Integer.parseInt(line[1]);
        List<Edge>[] g = new ArrayList[n];
        for (int i=0;i<n;i++) g[i]=new ArrayList<>();
        for (int i=0;i<m;i++) {
            String[] p = br.readLine().trim().split("\\s+");
            int u = Integer.parseInt(p[0]), v = Integer.parseInt(p[1]), w = Integer.parseInt(p[2]);
            g[u].add(new Edge(v,w));
            // for undirected: g[v].add(new Edge(u,w));
        }
        int src = Integer.parseInt(br.readLine().trim());
        int[] dist = dijkstra(g, src);
        for (int i=0;i<n;i++) {
            System.out.println(i + " : " + (dist[i]==Integer.MAX_VALUE ? -1 : dist[i]));
        }
    }
}

OUTPUT:
5 6
0 1 2
0 2 4
1 2 1
1 3 7
2 4 3
3 4 1
0

