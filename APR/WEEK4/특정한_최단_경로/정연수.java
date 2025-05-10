import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int[][] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int nodeCnt = Integer.parseInt(st.nextToken());
        int edgeCnt = Integer.parseInt(st.nextToken());
        graph = new int[nodeCnt + 1][nodeCnt + 1];

        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            graph[from][to] = weight;
            graph[to][from] = weight;
        }
        st = new StringTokenizer(br.readLine());
        int node1 = Integer.parseInt(st.nextToken());
        int node2 = Integer.parseInt(st.nextToken());

        int dist1 = dijkstra(1, node1);
        int dist2 = dijkstra(node1, node2);
        int dist3 = dijkstra(node2, nodeCnt);
        int dist4 = dijkstra(1, node2);
        int dist5 = dijkstra(node1, nodeCnt);

        int path1 = Integer.MAX_VALUE;
        int path2 = Integer.MAX_VALUE;

        if (dist1 != Integer.MAX_VALUE && dist2 != Integer.MAX_VALUE && dist3 != Integer.MAX_VALUE) {
            path1 = dist1 + dist2 + dist3;
        }

        if (dist2 != Integer.MAX_VALUE && dist4 != Integer.MAX_VALUE && dist5 != Integer.MAX_VALUE) {
            path2 = dist4 + dist5 + dist2;
        }

        int result = Math.min(path1, path2);
        if (result == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(result);
        }
    }

    static class Vertex implements Comparable<Vertex> {
        int idx;
        int dist;

        public Vertex(int idx, int dist) {
            this.idx = idx;
            this.dist = dist;
        }

        @Override
        public int compareTo(Vertex o) {
            return this.dist - o.dist;
        }
    }

    private static int dijkstra(int from, int to) {
        int[] dist = new int[graph.length + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(from, 0));
        dist[from] = 0;
        while (!pq.isEmpty()) {
            Vertex cur = pq.poll();

            if (dist[cur.idx] < cur.dist)
                continue;

            for (int i = 1; i < graph.length; i++) {
                if (graph[cur.idx][i] != 0) {
                    int newDist = dist[cur.idx] + graph[cur.idx][i];
                    if (newDist < dist[i]) {
                        dist[i] = newDist;
                        pq.add(new Vertex(i, newDist));
                    }
                }
            }
        }
        return dist[to];
    }
}
