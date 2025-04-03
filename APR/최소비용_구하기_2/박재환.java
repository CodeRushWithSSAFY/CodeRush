package APR.최소비용_구하기_2;

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

    static StringTokenizer st;
    static int nodes, edges;
    static List<int[]>[] graph;
    static int startNode, endNode;
    static void init() throws IOException {
        nodes = Integer.parseInt(br.readLine().trim());
        edges = Integer.parseInt(br.readLine().trim());

        graph = new ArrayList[nodes+1];
        for(int node=0; node<nodes+1; node++) {
            graph[node] = new ArrayList<>();
        }

        while(edges-- > 0) {
            st = new StringTokenizer(br.readLine().trim());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            // 양방향?
            graph[from].add(new int[] {to, cost});
//			graph[to].add(new int[] {from, cost});
        }

        st = new StringTokenizer(br.readLine().trim());
        startNode = Integer.parseInt(st.nextToken());
        endNode = Integer.parseInt(st.nextToken());

        getMinCost();
    }

    /*
     * 문제에서 해당 노드까지의 비용과, 거쳐오는 경로를 원함
     */
    static class Node implements Comparable<Node> {
        int no;
        int minCost;
        List<Integer> commingRoute;

        public Node(int no, int minCost, List<Integer> commingRoute) {
            this.no = no;
            this.minCost = minCost;
            this.commingRoute = commingRoute;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.minCost, o.minCost);
        }

        // 리스트는 참조형이라 그대로 전달하게 되면, 후속 값에 영향을 줌
        // 복사를 해서 줌
        public List<Integer> cloneList() {
            List<Integer> list = new ArrayList<Integer>();

            for(int i : this.commingRoute) {
                list.add(i);
            }

            return list;
        }

//		public void print() {
//			System.out.println(no);
//			System.out.println(minCost);
//			for(int i : commingRoute) {
//				System.out.print(i+" ");
//			}
//			System.out.println();
//		}
    }

    static void getMinCost() {
        // Node 의 가중치를 기준으로 오름차순 정렬
        PriorityQueue<Node> pq = new PriorityQueue<>();
        // 최소 가중치를 저장할 배열
        Node[] dist = new Node[nodes+1];
        // 초기 설정
        for(int node=0; node<nodes+1; node++) {
            dist[node] = new Node(node, Integer.MAX_VALUE, new ArrayList<Integer>());
        }

        // 시작 도시 설정
        pq.offer(new Node(startNode, 0, new ArrayList<Integer>()));
        dist[startNode].minCost = 0;

        while(!pq.isEmpty()) {
            Node curNode = pq.poll();
            List<Integer> commingRoute = curNode.cloneList();
            // 현재 최소 가중치보다, 해당 경로의 가중치가 크다면 패스
            if(curNode.minCost > dist[curNode.no].minCost) continue;

            commingRoute.add(curNode.no);

            for(int[] conn : graph[curNode.no]) {
                int connNode = conn[0];
                int connCost = conn[1];

                // 나아가려는 경로의 가중치가 기존 가중치보다 크다면 패스
                // 여기 왜 = 쓰면 통과고, 안쓰면 메모리초과?
                if(dist[connNode].minCost <= curNode.minCost + connCost) continue;

                // 작다면
                dist[connNode].minCost = curNode.minCost + connCost;
                dist[connNode].commingRoute = commingRoute;

                pq.offer(new Node(connNode, curNode.minCost+connCost, commingRoute));
            }
        }

        printLog(dist);
    }

    static void printLog(Node[] dist) {
        sb.append(dist[endNode].minCost).append('\n')
                .append(dist[endNode].commingRoute.size()+1).append('\n');

        for(int i : dist[endNode].commingRoute) {
            sb.append(i).append(' ');
        }
        sb.append(dist[endNode].no);
    }
}
