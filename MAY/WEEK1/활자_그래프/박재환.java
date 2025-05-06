package MAY.WEEK1.활자_그래프;

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
	static int graphCnt;			// 활자 그래프의 개수 
	static long[][] minDists;			// 각 그래프의 1->2, 2->1 의 최단 경로를 저장한다.
	static List<long[]>[] graph;		// 각 그래프의 연결 상태를 나타냄
	static final long INF = 1_000_000_000_000_000_001L;	// 최대 값 지정
	// -> 10^18 까지는 경로로 인정, 하지만 딱 맞춰 최대값을 지정하면, 유효 경로인지, 유효하지 않은 경로인지 판단 어려움
	static void init() throws IOException {
		graphCnt = Integer.parseInt(br.readLine().trim());
		minDists = new long[graphCnt+1][2];		// 1-base

		for(int i=1; i<graphCnt+1; i++) {
			minDists[i][0] = INF;
			minDists[i][1] = INF;
		}

		// 1. 활자 그래프를 입력 받음 
		for(int i=1; i<graphCnt+1; i++) {
			// 2. 현재 활자 그래프의 정점의 개수와, 간선/타 활자 그래프를 찍은 횟수나 주어짐
			st = new StringTokenizer(br.readLine().trim());
			int nodeCnt = Integer.parseInt(st.nextToken());
			int doSomeThing = Integer.parseInt(st.nextToken());	// 간선을 찍을 수도, 타 그래프을 찍을수도 있음 
			
			// 2-1. 현재 그래프의 정점의 개수만큼 공간을 할당해줌 
			graph = new ArrayList[nodeCnt+1];		// 1-base
			for(int node=0; node<nodeCnt+1; node++) {
				graph[node] = new ArrayList<>();
			}
			
			// 2-2. 간선 혹은 그래프를 찍는다. 
			while(doSomeThing-- > 0) {
				st = new StringTokenizer(br.readLine().trim());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				long costOrGraph = Long.parseLong(st.nextToken());

				if(costOrGraph >= 0) {	// 간선인 경우
					// 단방향 그래프
					graph[from].add(new long[]{to, costOrGraph});
				} else {	// 그래프인 경우
					int graphNo = (int) Math.abs(costOrGraph);

					// 그래프를 끼고 이동이 가능한 경우
					if(minDists[graphNo][0] < INF) {
						graph[from].add(new long[]{to, minDists[graphNo][0]});
					}
					if(minDists[graphNo][1] < INF) {
						graph[to].add(new long[]{from, minDists[graphNo][1]});
					}
				}
			}
			
			// 해당 그래프의 1->2 / 2->1 의 최단 경로를 저장한다
			// 이는 그래프가 중간에 찍히게 될 경우를 대비해서임
			minDists[i][0] = Math.min(getMinDist(graph, 1, 2), minDists[i][0]);
			if(i < graphCnt) {	// 마지막 그래프가 아니라면 
				minDists[i][1] = Math.min(getMinDist(graph, 2, 1), minDists[i][1]);
			}
		}

		System.out.println(minDists[graphCnt][0] >= INF ? -1 : minDists[graphCnt][0]);
	}
	
	/*
	 * start -> end 까지의 최소 경로를 구한 뒤, 이를 반환한다.
	 * 다익스트라 알고리즘을 사용한다. 
	 */
	static long getMinDist(List<long[]>[] graph, int start, int end) {
		// 가중치를 기준으로 오름차순 정렬 
		PriorityQueue<long[]> pq = 
				new PriorityQueue<>((a,b) -> Long.compare(a[1], b[1]));
		
		int nodeCnt = graph.length;		// 이전에 1-base 로 선언했으므로, 그대로 사용 
		
		long[] dp = new long[nodeCnt];
		// 최대값으로 지정 
		Arrays.fill(dp, INF);
		// 출발지는 제외 
		dp[start] = 0;
		pq.offer(new long[] {start, 0});
		
		while(!pq.isEmpty()) {
			long[] cur = pq.poll();
			int from = (int)cur[0];
			long accSum = cur[1];
			
			// 이전의 값 중 최적 해가 존재한다면, 더 이상 탐색하지 않는다.
			if(accSum > dp[from]) continue;
			
			// 현재 노드와 연결되어 있는 노드들을 순회한다.
			for(long[] conn : graph[from]) {
				int to = (int)conn[0];
				long dist = conn[1];
				
				if(dp[to] > accSum + dist) {
					dp[to] = accSum + dist;
					pq.offer(new long[] {to, dp[to]});
				}
			}
		}
		
		return dp[end];
	}
}

/* 
 * 활자 그래프에서의 최단 경로
 * T 개의 활자 그래프가 있음 (1~T)
 * 
 * 각 활자 그래프는 1~N 의 정점을 가지고 있음 
 * 간선 : v 번 정점에서 w번 정점으로 가는 가중치가 있는 단방향 간선을 추가한다.
 * 활자 그래프 : i 번째 활자 그래프의 시작점과, t 번 활자 그래프의 v 번 정점과 w 번 정점이 되도록 
 * 				i 번째 활자 그래프의 정점과 간선을 추가한다. -> 이때 생기는 i 번째 활자 그래프의 정점들에는 번호 X 
 * 
 *  모든 활자 그래프의 시작은 1, 끝점은 2 
 *  
 *  T 번 활자 그래프의 1 -> 2 의 최단 거리를 구해라 
 */
