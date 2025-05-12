package MAY.WEEK2.파티;

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
	static final int INF = 987654321;
	static int nodeCnt;		// 마을의 개수 -> 학생들은 한 마을에 한 명씩 있다. 
	static int edgeCnt;		// 간선의 개수 
	static int targetNode;	// 도착 노드 
	// 1. 플로이드 풀이 
	static int[][] graphArr;
	// 2. 다익스트라 풀이
	static List<int[]>[] graphList;			// 순방향 그래프 -> x 에서 모든 노드로의 최단 거리
	static List<int[]>[] reverseGraphList;	// 역방향 그래프 -> 모든 노드에서 x 로의 최단 거리 
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		nodeCnt = Integer.parseInt(st.nextToken());		// 최대 1000 개
		edgeCnt = Integer.parseInt(st.nextToken());
		targetNode = Integer.parseInt(st.nextToken());
		
		// 1. 플로이드 풀이 
		graphArr = new int[nodeCnt+1][nodeCnt+1];
		for(int i=0; i<nodeCnt+1; i++) Arrays.fill(graphArr[i], INF);
		
		// 2. 다익스트라 풀이 
		graphList = new ArrayList[nodeCnt+1];
		reverseGraphList = new ArrayList[nodeCnt+1];
		for(int i=0; i<nodeCnt+1; i++) {
			graphList[i] = new ArrayList<>();
			reverseGraphList[i] = new ArrayList<>();
		}
		
		while(edgeCnt-- > 0) {
			st = new StringTokenizer(br.readLine().trim());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			// 1. 플로이드 풀이
			graphArr[from][to] = weight;
			
			// 2. 다익스트라 풀이 
			graphList[from].add(new int[] {to, weight});
			reverseGraphList[to].add(new int[] {from, weight});
		}
		
//		floyd();
		dijkstra();
	}
	
	/*
	 * 도시의 개수는 최대 1000 개 
	 * -> 플로이드 적용시 O(n^3) => 10억 
	 * 제한 시간 내에 불가
	 */
	static void floyd() {
		for(int mid=1; mid<nodeCnt+1; mid++) {			// 경유 노드
			for(int from=1; from<nodeCnt+1; from++) {	// 출발 노드
				// 1-1. 경유 노드와 출발 노드가 같을 수 없음 
				if(mid == from) continue;
				for(int to=1; to<nodeCnt+1; to++) {		// 도착 노드 
					// 1-2. 경유 노드와 출발 노드가 같을 수 없읍
					if(mid==to) continue;
					// 1-3. 츌발 노드와 도착 노드가 같으면 자기 자신을 참조
					if(from == to) {
						graphArr[from][to] = 0;
						continue;
					}
					
					// 이전의 최적해와 비교하여 갱신
					graphArr[from][to] = Math.min(graphArr[from][to], 
							graphArr[from][mid]+graphArr[mid][to]);
					
				}
			}
		}
		
		// 결과 확인 
//		for(long[] arr : floydArr) {
//			System.out.println(Arrays.toString(arr));
//		}
		
		// 최대 소요 시간 구하기 
		long maxValue = Long.MIN_VALUE;
		for(int i=1; i<nodeCnt+1; i++) {
			if(i==targetNode) continue;
			
			maxValue = Math.max(maxValue, graphArr[i][targetNode] + graphArr[targetNode][i]);
		}
		System.out.println(maxValue);
	}
	
	/*
	 * 다익스트라 이용 
	 * 2-1. 역방향 그래프를 이용하여 targetNode 에서 다익스트라를 구한다.
	 * => 이렇게하면 정방향 그래프 기준 모든 노드에서 targetNode 까지의 최소 거리를 구할 수 있다. 
	 * 2-2. 정방향 그래프를 이용해 targetNode 에서 다익스트라를 구한다. 
	 * => targetNode 에서 모든 노드로의 최소 거리를 구할 수 있다. 
	 */
	static void dijkstra() {
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1]-b[1]);
		
		int[] dijkstraArr = new int[nodeCnt+1];			// 순방향 : x 에서 모든 노드
		int[] reverseDijkstraArr = new int[nodeCnt+1];	// 역방향 : 모든 노드에서 x 
		Arrays.fill(dijkstraArr, INF);
		Arrays.fill(reverseDijkstraArr, INF);
		
		// 2-1. 순방향 다익스트라 
		pq.offer(new int[] {targetNode, 0});	// [to, weight]
		dijkstraArr[targetNode] = 0;			
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int from = cur[0];
			int accDist = cur[1];
			
			// 이전의 최적해보다, 현재 누적 합이 크다면 더 이상 탐색 X 
			if(accDist > dijkstraArr[from]) continue;
			
			// 현재 노드와 연결된 노드들 확인 
			for(int[] conn : graphList[from]) {
				int to = conn[0];
				int weight = conn[1];
				
				// 최적해를 갱신할 수 있는지 확인 
				if(dijkstraArr[to] > accDist + weight) {
					dijkstraArr[to] = accDist+weight;
					pq.offer(new int[] {to, dijkstraArr[to]});
				}
			}
		}
		
		// 2-2. 역방향 다익스트라 
		pq.offer(new int[] {targetNode, 0});	// [to, weight]
		reverseDijkstraArr[targetNode] = 0;			
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int from = cur[0];
			int accDist = cur[1];
			
			// 이전의 최적해보다, 현재 누적 합이 크다면 더 이상 탐색 X 
			if(accDist > reverseDijkstraArr[from]) continue;
			
			// 현재 노드와 연결된 노드들 확인 
			for(int[] conn : reverseGraphList[from]) {
				int to = conn[0];
				int weight = conn[1];
				
				// 최적해를 갱신할 수 있는지 확인 
				if(reverseDijkstraArr[to] > accDist + weight) {
					reverseDijkstraArr[to] = accDist+weight;
					pq.offer(new int[] {to, reverseDijkstraArr[to]});
				}
			}
		}
		
		// 결과 확인 
//		System.out.println(Arrays.toString(dijkstraArr));
//		System.out.println(Arrays.toString(reverseDijkstraArr));
		int maxValue = Integer.MIN_VALUE;
		for(int i=1; i<nodeCnt+1; i++) {
			if(i == targetNode) continue;
			maxValue = Math.max(maxValue, dijkstraArr[i]+reverseDijkstraArr[i]);
		}
		System.out.println(maxValue);
	}
}

/*
 * N 명의 학생 ( N 개의 마을 ) 이 X 에 갔다가 다시 돌아오는데 가장 많은 시간을 소비하는 값을 구하라
 * 
 * 모든 노드가 특정 노드로 이동한다. 
 * 특정 노드에서 다시 모든 노드로 이동한다. 
 * => 플로이드 워셜
 * => 다익스트라
 */
