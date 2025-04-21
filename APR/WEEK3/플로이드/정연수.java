import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int start, arrive, cost;
    static final int INF = 2147000000;
    static ArrayList<ArrayList<Pair>> vertex;
    static int[][] dist;
    
    static class Pair implements Comparable<Pair> {
        int cost;
        int node;
        
        public Pair(int cost, int node) {
            this.cost = cost;
            this.node = node;
        }
        
        @Override
        public int compareTo(Pair other) {
            return this.cost - other.cost;
        }
    }
    
    static void dijkstra(int start) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(0, start));  // cost, start
        dist[start][start] = 0;  // 자기 자신까지의 거리는 0으로 초기화
        
        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int currentNode = current.node;
            int currentCost = current.cost;
            
            if (dist[start][currentNode] < currentCost) {
                continue;
            }
            
            for (int i = 0; i < vertex.get(currentNode).size(); i++) {
                Pair next = vertex.get(currentNode).get(i);
                int nextNode = next.node;
                int nextCost = next.cost;
                
                if (dist[start][nextNode] > nextCost + currentCost) {
                    dist[start][nextNode] = nextCost + currentCost;
                    pq.offer(new Pair(nextCost + currentCost, nextNode));
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // N과 M이 각각 다른 줄에 있는 경우
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        
        // 인접 리스트 초기화
        vertex = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            vertex.add(new ArrayList<>());
        }
        
        // 거리 배열 초기화
        dist = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                dist[i][j] = INF;
            }
        }
        
        // 간선 정보 입력
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            start = Integer.parseInt(st.nextToken());
            arrive = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());
            
            vertex.get(start).add(new Pair(cost, arrive));
        }
        
        // 모든 정점에서 다익스트라 알고리즘 수행
        for (int i = 1; i <= N; i++) {
            dijkstra(i);
        }
        
        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    sb.append("0 ");  // 자기 자신까지의 거리는 0
                } else if (dist[i][j] == INF) {
                    sb.append("0 ");  // 도달할 수 없는 경우
                } else {
                    sb.append(dist[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        
        System.out.print(sb);
    }
}
