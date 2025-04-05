package APR.최소_스패닝_트리;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    /*
     * 간선의 개수가 최대 100,000 개
     */
    static StringTokenizer st;
    static int nodes, edges;
    static List<int[]>[] graph;
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        nodes = Integer.parseInt(st.nextToken());
        edges = Integer.parseInt(st.nextToken());

        graph = new ArrayList[nodes+1];
        for(int i=0; i<nodes+1; i++) {
            graph[i] = new ArrayList<int[]>();
        }

        while(edges-- > 0) {
            st = new StringTokenizer(br.readLine().trim());

            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            graph[nodeA].add(new int[] {nodeB, dist});
            graph[nodeB].add(new int[] {nodeA, dist});
        }

        getMinCost();
    }

    static void getMinCost() {
        long totalCost = 0;
        int nodeCnt = 0;

        // 가중치를 기준으로 오름차순
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1]-b[1]);
        boolean[] visited = new boolean[nodes+1];

        // 출발점 설정
        pq.offer(new int[] {1,0});

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curNode = cur[0];
            int curDist = cur[1];

            // 이미 처리된 노드라면 넘어간다.
            if(visited[curNode]) continue;

            visited[curNode] = true;
            totalCost += curDist;
            if(++nodeCnt == nodes) break;	// 모든 노드를 연결했다면 종료

            // 현 노드와 연결된 노드들을 확인
            for(int[] connNode : graph[curNode]) {
                if(visited[connNode[0]]) continue;

                pq.offer(connNode);
            }
        }

        System.out.println(totalCost);
    }
}

/*
 * 그래프의 MST 를 구하여라
 *
 * 가중치의 범위는 절댓값 INT 의 범위 내이다.
 */
