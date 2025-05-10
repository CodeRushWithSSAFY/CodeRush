import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static class Edge {
        int vertex;
        int weight;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
    static int vertexCnt, edgeCnt;
    static List<Edge>[] graph;
    static int[] midVertex;

    static int[][] minDist;

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        vertexCnt = Integer.parseInt(st.nextToken());
        edgeCnt = Integer.parseInt(st.nextToken());

        graph = new ArrayList[vertexCnt + 1];
        for (int i = 0; i <= vertexCnt; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int idx = 0; idx < edgeCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[from].add(new Edge(to, weight));
            graph[to].add(new Edge(from, weight));
        }

        st = new StringTokenizer(br.readLine().trim());
        midVertex = new int[2];
        midVertex[0] = Integer.parseInt(st.nextToken());
        midVertex[1] = Integer.parseInt(st.nextToken());

        minDist = new int[vertexCnt + 1][3];
        for (int idx = 0; idx < 3; idx++) {
            for (int vertex = 0; vertex <= vertexCnt; vertex++) {
                minDist[vertex][idx] = Integer.MAX_VALUE;
            }
        }
    }

    public static void Dijkstra(int start, int minDistIdx) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> {
            return Integer.compare(e1.weight, e2.weight);
        });
        pq.offer(new Edge(start, 0));
        minDist[start][minDistIdx] = 0;

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (minDist[cur.vertex][minDistIdx] < cur.weight) continue;

            for (int idx = 0; idx < graph[cur.vertex].size(); idx++) {
                int nextNode = graph[cur.vertex].get(idx).vertex;
                int nextWeight = graph[cur.vertex].get(idx).weight;

                if (nextWeight + cur.weight < minDist[nextNode][minDistIdx]) {
                    minDist[nextNode][minDistIdx] = nextWeight + cur.weight;
                    pq.offer(new Edge(nextNode, nextWeight + cur.weight));
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        init();

        Dijkstra(1, 0);
        Dijkstra(midVertex[0], 1);
        Dijkstra(midVertex[1], 2);

        int first = minDist[midVertex[0]][0] + minDist[midVertex[1]][1] + minDist[vertexCnt][2];
        if (minDist[midVertex[0]][0] == Integer.MAX_VALUE || minDist[midVertex[1]][1] == Integer.MAX_VALUE ||  minDist[vertexCnt][2] == Integer.MAX_VALUE) {
            first = Integer.MAX_VALUE;
        }
        int second = minDist[midVertex[1]][0] + minDist[midVertex[0]][2] + minDist[vertexCnt][1];
        if (minDist[midVertex[1]][0] == Integer.MAX_VALUE || minDist[midVertex[0]][2] == Integer.MAX_VALUE || minDist[vertexCnt][1] == Integer.MAX_VALUE) {
            second = Integer.MAX_VALUE;
        }

        if (first == Integer.MAX_VALUE && second == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(Math.min(first, second));
        }

    }
}
