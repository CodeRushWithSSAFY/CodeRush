import java.util.*;
import java.io.*;

/*
 * 
 * vertex1 vertex2 cost에서 
 * cost >= 0 -> vertex1에서 vertex2으로 가는 가중치 cost의 단방향 간선 의미
 * cost < 0 -> vertex1에 시작점에 맞추고 vertex2 끝점을 맞춰서 cost 절대 값의 번호의  그래프를 찍었음을 의미
 * 
 * 항상 그래프마다 시작정점은 1, 끝점은 2이다.
 * 그래서 cost < 0 일때 cost번 활자 그래프에서 1번 정점 -> 2번 정점 그래프를 해당 vertex1 위치에서 끝점을 vertex2로 바꾸면 됨.
 * 
 * 맨 마지막의 활자 그래프에서 1번 -> 2번 정점까지 가는 최소 비용을 출력하라. 
 * 만약 경로 자체가 없거나, cost 값이 10^18보다 크면 -1을 대신 출력하자.
 * 
 * */
public class 정한슬 {
    static final int START_VERTEX = 1;
    static final int END_VERTEX = 2;
    static final long INF = 1_000_000_000_000_000_001L;

    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;

    static int graphCount;
    static int edgeCount;
    static int vertexCount;

    static List<long[]>[][] graphs;
    static long[][] minDistance;
    static long minCost;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        initInput();
        br.close();
        addContinousGraph();
        bw.write(String.valueOf(minCost));
        bw.flush();
        bw.close();
    }

    private static void addContinousGraph() {
        minDistance = new long[graphCount][2]; // [그래프 번호][0: 1->2, 1: 2->1]
        for (long[] arr : minDistance) Arrays.fill(arr, INF);

        for (int graphIdx = 0; graphIdx < graphCount; graphIdx++) {
            List<long[]>[] graph = graphs[graphIdx];

            // cost < 0 간선을 실제로 연결하기
            for (int from = 1; from < graph.length; from++) {
                List<long[]> newEdges = new ArrayList<>();
                for (long[] edge : graph[from]) {
                    if (edge[0] == -1) {
                        int start = (int) edge[1];
                        int end = (int) edge[2];
                        int usedGraphIdx = (int) edge[3] - 1;

                        long costToUse = minDistance[usedGraphIdx][0];
                        if (costToUse < INF) {
                            newEdges.add(new long[]{end, costToUse});
                        }
                        costToUse = minDistance[usedGraphIdx][1];
                        if (costToUse < INF) {
                            graphs[graphIdx][end].add(new long[]{start, costToUse});
                        }
                    } else {
                        newEdges.add(edge);
                    }
                }
                graph[from] = newEdges;
            }

            minDistance[graphIdx][0] = getMinDistance(graphs[graphIdx], START_VERTEX, END_VERTEX);
            if (graphIdx < graphCount - 1) {
                minDistance[graphIdx][1] = getMinDistance(graphs[graphIdx], END_VERTEX, START_VERTEX);
            }
        }

        long result = minDistance[graphCount - 1][0];
        minCost = (result >= INF) ? -1 : result;
    }

    private static long getMinDistance(List<long[]>[] graph, int startVertex, int endVertex) {
        int graphLength = graph.length;
        long[] distDp = new long[graphLength];
        Arrays.fill(distDp, INF);
        distDp[startVertex] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));
        pq.add(new long[]{startVertex, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int from = (int) cur[0];
            long cost = cur[1];

            if (distDp[from] < cost || distDp[from] == INF) continue;

            for (long[] adjArr : graph[from]) {
                long to = adjArr[0];
                long toCost = adjArr[1];

                if (distDp[(int) to] > distDp[from] + toCost) {
                    distDp[(int) to] = distDp[from] + toCost;
                    pq.add(new long[]{to, distDp[(int) to]});
                }
            }
        }
        return distDp[endVertex];
    }

    private static void initInput() throws IOException {
        graphCount = Integer.parseInt(br.readLine().trim());
        graphs = new ArrayList[graphCount][];
        for (int graphIdx = 0; graphIdx < graphCount; graphIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            vertexCount = Integer.parseInt(st.nextToken());
            edgeCount = Integer.parseInt(st.nextToken());

            graphs[graphIdx] = new ArrayList[vertexCount + 1];
            for (int idx = 0; idx <= vertexCount; idx++) {
                graphs[graphIdx][idx] = new ArrayList<>();
            }

            for (int edgeInfo = 0; edgeInfo < edgeCount; edgeInfo++) {
                st = new StringTokenizer(br.readLine().trim());
                int vertex1 = Integer.parseInt(st.nextToken());
                int vertex2 = Integer.parseInt(st.nextToken());
                long cost = Long.parseLong(st.nextToken());

                if (cost >= 0) {
                    graphs[graphIdx][vertex1].add(new long[]{vertex2, cost});
                } else {
                    int usingGraphIdx = (int) -cost;
                    graphs[graphIdx][vertex1].add(new long[]{-1, vertex1, vertex2, usingGraphIdx});
                }
            }
        }
    }
}
