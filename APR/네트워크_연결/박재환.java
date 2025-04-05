package APR.네트워크_연결;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static StringTokenizer st;
    static int nodes, edges;
    static List<int[]> graph;
    static void init() throws IOException {
        nodes = Integer.parseInt(br.readLine().trim());
        edges = Integer.parseInt(br.readLine().trim());
        graph = new ArrayList<>();
        while(edges-- > 0) {
            st = new StringTokenizer(br.readLine().trim());

            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            graph.add(new int[] {nodeA, nodeB, dist});
        }

        getMinDist();
    }

    static void getMinDist() {
        // List 를 가중치 기준으로 오름차순 정렬한다.
        Collections.sort(graph, (a, b) -> a[2] - b[2]);

        int totalDist = 0;
        int connCnt = 0;
        make();
        for(int[] arr : graph) {
            int nodeA = arr[0];
            int nodeB = arr[1];
            int dist = arr[2];

            if(union(nodeA, nodeB)) {
                totalDist += dist;

                if(++connCnt == nodes-1) break;
            }
        }

        System.out.println(totalDist);
    }

    // union find 구현
    static int[] parents;
    static void make() {
        parents = new int[nodes+1];

        for(int idx=0; idx<=nodes; idx++) {
            parents[idx] = idx;
        }
    }

    static int find(int node) {
        if(parents[node] == node) return node;

        return parents[node] = find(parents[node]);
    }

    static boolean union(int nodeA, int nodeB) {
        int rootA = find(nodeA);
        int rootB = find(nodeB);

        if(rootA == rootB) return false;

        parents[rootB] = rootA;
        return true;
    }
}
