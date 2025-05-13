package MAY.WEEK2.우주신과의_교감;

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
    static int nodeCnt, edgeCnt;        // 우주신 개수, 통로 개수
    static int[][] nodes;               // 각 좌표 저장
    static int[] parents;               // 연결 정보 확인
    static double totalDist;
    static int edges;
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        nodeCnt = Integer.parseInt(st.nextToken());
        edgeCnt = Integer.parseInt(st.nextToken());

        nodes = new int[nodeCnt+1][];
        for(int i=1; i<nodeCnt+1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            nodes[i] = new int[] {x,y};
//            System.out.println(Arrays.toString(nodes[i]));
        }

        // 1. 점 to 점으로 모든 거리를 구한다.
        calcAllDist();
        // 2. MST 를 구한다.
        // 간선의 개수는 nodeCnt - 1
        make();
        edges = 0;
        totalDist = 0;
        while(edgeCnt-- > 0) {
            st = new StringTokenizer(br.readLine().trim());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());
            union(nodeA, nodeB);
            edges += 1;
        }
        while(!pq.isEmpty() || edges < nodeCnt-1) {
            double[] cur = pq.poll();
            int nodeA = (int) cur[0];
            int nodeB = (int) cur[1];
            double dist = cur[2];

            if(!union(nodeA, nodeB)) continue;

            edges++;
            totalDist += dist;
        }

        System.out.printf("%.2f",totalDist);
    }

    /*
        Point to Point 로 모든 거리를 구한다.
     */
    static PriorityQueue<double[]> pq;
    static void calcAllDist() {
        // 연결 가중치를 기준으로 오름차순 정렬
        pq = new PriorityQueue<>((a,b)->Double.compare(a[2], b[2]));

        for(int i=1; i<nodeCnt+1; i++) {
            for(int ii=i+1; ii<nodeCnt+1; ii++) {
                long xDiff = Math.abs(nodes[i][0] - nodes[ii][0]);
                long yDiff = Math.abs(nodes[i][1] - nodes[ii][1]);
                double dist = Math.sqrt(xDiff*xDiff + yDiff*yDiff);

                pq.offer(new double[] {i, ii, dist});
            }
        }
    }

    // Union-Find
    static void make() {
        parents = new int[nodeCnt+1];
        for(int i=0; i<nodeCnt+1; i++) parents[i] = i;
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

/*
    바로 연결할 필요 없이, 이미 연결되어 있는 우주신을 이용해서 교감이 가능하다.
    통로들이 짧은 것을 선호한다.
    -> MST ??
 */
