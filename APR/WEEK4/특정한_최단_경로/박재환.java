package WEEK4.특정한_최단_경로;

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
	static List<int[]>[] graph;
	static int nodeA, nodeB;
	static final int INF = 200000*1000;
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		nodes = Integer.parseInt(st.nextToken());
		edges = Integer.parseInt(st.nextToken());
		
		/*
		 * 특정 정점에서 특정 정점으로
		 * 
		 * 근데? 임의 2 개의 정점을 반드시 지나야한다. 
		 * 플로이드 워셜 -> O(n^3) -> 800^3 XX 
		 * 다익스트라 -> O(nm) -> 800*200000 
		 */
		graph = new ArrayList[nodes+1];
		for(int idx=0; idx<nodes+1; idx++) {
			graph[idx] = new ArrayList<>();
		}
		while(edges-- > 0) {
			st = new StringTokenizer(br.readLine().trim());
			
			nodeA = Integer.parseInt(st.nextToken());
			nodeB = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			
			graph[nodeA].add(new int[] {nodeB, dist});
			graph[nodeB].add(new int[] {nodeA, dist});
		}

		// 그래프 입력 확인 
//		for(int idx=1; idx<nodes+1; idx++) {
//			System.out.println(idx+"번");
//			for(int[] conn : graph[idx]) {
//				System.out.print(Arrays.toString(conn)+"  ");
//			}
//			System.out.println();
//		}
		
		// 필수 경유 경로 
		st = new StringTokenizer(br.readLine().trim());
		nodeA = Integer.parseInt(st.nextToken());
		nodeB = Integer.parseInt(st.nextToken());
		
		// 생각할 수 있는 경우는 2가지
		// 		1. s -> nodeA -> nodeB -> e
		//		2. s -> nodeB -> nodeA -> e
		int routeA = findMinDist(1, nodeA) + findMinDist(nodeA, nodeB) + findMinDist(nodeB, nodes);
		int routeB = findMinDist(1, nodeB) + findMinDist(nodeB, nodeA) + findMinDist(nodeA, nodes);
		
		System.out.println(Math.min(routeA, routeB) >= INF ? -1 : Math.min(routeA, routeB));
	}
	
	/*
	 * 다익스트라를 사용한다
	 * [출발지, 목적지]
	 * 
	 * 특정 노드에서 특정 노드까지의 거리를 구한다. 
	 */
	static int findMinDist(int s, int e) {
		// 가중치를 기준 오름차순 정렬
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
		int[] dpArr = new int[nodes+1];
		Arrays.fill(dpArr, INF);
		
		// 출발지 설정 
		dpArr[s] = 0;
		pq.offer(new int[] {s,0});
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int curNode = cur[0];
			int curDist = cur[1];
			
			// 이전의 해가 더 최적해인경우
			if(dpArr[curNode] < curDist) continue;
			
			// 탐색을 해야한다면
			for(int[] conn : graph[curNode]) {	// 현 노드와 연결되어 있는 노드들을 확인한다.
				int connNode = conn[0];
				int connDist = conn[1];
				
				// 더 짧은 경로인지 확인
				if(dpArr[connNode] > curDist+connDist) {
					dpArr[connNode] = curDist+connDist;
					pq.offer(new int[] {connNode, dpArr[connNode]});
				}
			}
		}
//		System.out.println(Arrays.toString(dpArr));
		return dpArr[e];
	}
}

/* 
 * 방향성이 없는 그래프
 * 1 -> N 으로 가는 최단 거리 ( 다익스트라? )
 * 두 조건을 만족하며 이동해야함 
 * 임의의 두 정점을 반드시 통과해야함 ( 플로이드 워셜 ? )
 */
