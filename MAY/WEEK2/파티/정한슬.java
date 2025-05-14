import java.util.*;
import java.io.*;

/*
 * N: 마을 개수 -> cityCount;
 * M: 단방향 도로 개수 -> roadCount;
 * X: 파티 벌이는 마을 idx -> partyCityIdx;
 * 
 * 자신의 마을 -> 파티가 열리는 마을 -> 자신의 마을 의 최단 경로를 구한 뒤 가장 최단 경로가 큰 값을 출력하자
 * 
 * 해결법
 * 1번 부터 N번까지의 마을에서 X 마을로 가는 최단 경로 값을 dijkstra을 통해 구한뒤 max 값을 찾자.
 * -> dist[N][N] // 마을 idx별로 distance 경로 2차원 배열 구한 뒤 idx별로 dist[idx][X] 값이 큰것 찾기
 * 
 * => 저 dist 2차원 배열을 통해 답을 구하고 싶었으면 플로이드 워셜 개념 쓰는거임.. 다익스트라 쓰려고해놓고 정리되지않았음,,
 * 
 * ++ !! 마을 -> X(파티 열리는 마을)로 갈때는 역방향 그래프가 필요했음. 
 *    - 왜? 다익스트라 자체가 시작점 -> 모든 점까지의 경로 비용을 일차원 배열에 갱신하는 개념이기 때문에
 * 
 * 그래서 역방향 그래프 + 다익스트라 개념으로 dist 갱신을 해두면 
 * 		dist[마을 idx]의 비용은 마을 -> X의 비용이다.
 * */
public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int cityCount;
	static int roadCount;
	static int partyCityIdx;
	
	static List<int[]>[] graph;
	static List<int[]>[] reverseGraph;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		
		int[] toParty = dijkstra(partyCityIdx, 0); // i → X (역방향 그래프)
		int[] fromParty = dijkstra(partyCityIdx, 1); // X → i (정방향 그래프)

		int max = 0;
		for (int i = 0; i < cityCount; i++) {
			max = Math.max(max, toParty[i] + fromParty[i]);
		}

		bw.write(String.valueOf(max));
		bw.flush();
		bw.close();
	}
	
	private static int[] dijkstra(int fromIdx, int flag) { //flag가 0일때는 마을 -> X / 1일 때는 X -> 마을
		int[] distBycityIdx = new int[cityCount];
		Arrays.fill(distBycityIdx, Integer.MAX_VALUE);
		
		distBycityIdx[fromIdx] = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>(
				(o1, o2) -> Integer.compare(o1[1], o2[1]));
		pq.add(new int[] {fromIdx, 0});
		
		List<int[]>[] usingGraph = flag == 1 ? graph : reverseGraph;
		
		while (!pq.isEmpty()) {
			int[] curStatus = pq.poll();
			int fromCity = curStatus[0];
			int curCost = curStatus[1];
			
			if (distBycityIdx[fromCity] < curCost) continue;
			
			for (int[] adjCity : usingGraph[fromCity]) {
				int toCity = adjCity[0];
				int cost = adjCity[1];
				
				if (distBycityIdx[toCity] > distBycityIdx[fromCity] + cost) {
					distBycityIdx[toCity] = distBycityIdx[fromCity] + cost;
					pq.add(new int[] {toCity, distBycityIdx[toCity]});
				}
			}
		}
		
		return distBycityIdx;
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		
		cityCount = Integer.parseInt(st.nextToken());
		roadCount = Integer.parseInt(st.nextToken());
		partyCityIdx = Integer.parseInt(st.nextToken()) - 1;
		
		graph = new ArrayList[cityCount];
		reverseGraph = new ArrayList[cityCount];
		for (int cityIdx = 0; cityIdx < cityCount; cityIdx++) {
			graph[cityIdx] = new ArrayList<>();
			reverseGraph[cityIdx] = new ArrayList<>();
		}
		
		for (int roadIdx = 0; roadIdx < roadCount; roadIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			int fromCity = Integer.parseInt(st.nextToken()) - 1;
			int toCity = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			graph[fromCity].add(new int[] {toCity, cost});
			reverseGraph[toCity].add(new int[] {fromCity, cost});
		}
	}
}
