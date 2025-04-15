package APR.최단경로;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        init();
        br.close();
        System.out.println(sb);
    }

    /*
     * 방향 그래프이다.
     * 주어지는 입력은
     * u -> v 로가는 비용 w 이다.
     */
    static StringTokenizer st;
    static int nodes, edges, startNode;
    static List<int[]>[] graph;
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        nodes = Integer.parseInt(st.nextToken());
        edges = Integer.parseInt(st.nextToken());

        startNode = Integer.parseInt(br.readLine().trim());

        graph = new ArrayList[nodes+1];	// 1-base
        // 리스트 배열 초기화
        for(int node=0; node<nodes+1; node++) {
            graph[node] = new ArrayList<>();
        }

        // 간선 입력
        while(edges-- > 0) {
            st = new StringTokenizer(br.readLine().trim());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            // 방향그래프
            graph[from].add(new int[] {to, dist});
        }
        getMinDist();
    }

    /*
     * 시작 점에서 다른 모든 정점으로의 최단 경로를 구한다.
     * 가중치는 10 이하의 자연수 -> 음수 X
     *
     * 다익스트라
     */
    static void getMinDist() {
        // 가중치를 기준으로 오름차순 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);

        int[] dist = new int[nodes+1];	// 시작 정점에서 각 정점까지의 최단 거리를 기록
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 시작 노드 초기화
        pq.add(new int[] {startNode, 0});	// 현재 노드, 현재 정점까지의 거리
        dist[startNode] = 0;

        while(!pq.isEmpty()) {	// 우선순위큐가 빌때 까지
            int[] cur = pq.poll();
            int curNode = cur[0];
            int curDist = cur[1];

            // 현노드까지의 최소거리보다, 더 긴 거리를 갖고있을 경우 탐색할 필요 없음
            if(curDist > dist[curNode]) continue;

            // 현재 노드와 연결된 노드의 거리를 업데이트한다.
            for(int[] conn : graph[curNode]) {
                int connNode = conn[0];
                int connDist = conn[1];

                if(dist[connNode] < curDist + connDist) continue;

                dist[connNode] = curDist + connDist;
                pq.offer(new int[] {connNode, dist[connNode]});
            }
        }
        printDist(dist);
    }

    static void printDist(int[] dist) {
        for(int node=1; node<nodes+1; node++) {
            sb.append(dist[node] == Integer.MAX_VALUE ? "INF" : dist[node]).append('\n');
        }
    }
}
