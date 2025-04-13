import java.util.*;
import java.io.*;

public class 정한슬 {
	static final int INF = Integer.MAX_VALUE;
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	static StringBuilder sb;
	
	static int cityCount, busCount;
	static int[][] adjMatrix;
	static int city1, city2, cost;
	static int startCity, endCity;
	static List<Integer> visitCities;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		sb = new StringBuilder();
		findMinCostWitDijkstra();
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static void findMinCostWitDijkstra() {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
		pq.add(new int[] {startCity, 0});
		
		int[] prevMemo = new int[cityCount];
		int[] distance = new int[cityCount];
		Arrays.fill(distance, INF);
		distance[startCity] = 0;

		while (!pq.isEmpty()) {
			int[] curStatus = pq.poll();
			int to = curStatus[0];
			int cost = curStatus[1];
			
			if (distance[to] < cost) continue;
						
			for (int cityNumber = 0; cityNumber < cityCount; cityNumber++) {
				if (adjMatrix[to][cityNumber] != INF) {
					if (distance[cityNumber] > distance[to] + adjMatrix[to][cityNumber]) {
						distance[cityNumber] = distance[to] + adjMatrix[to][cityNumber];
						prevMemo[cityNumber] = to;
						pq.add(new int[] {cityNumber,distance[cityNumber]});
						
					}
				}
			}
		}
		
		visitCities = new ArrayList<>();
		for (int prev = endCity; prev != startCity; prev = prevMemo[prev]) {
			visitCities.add(prev);
		}
		visitCities.add(startCity);
		
		sb.append(distance[endCity]).append("\n").append(visitCities.size()).append("\n");
		for (int idx = visitCities.size() - 1; idx >= 0; idx--) {
			int city = visitCities.get(idx);
			sb.append(city + 1).append(" ");
		}
		sb.append("\n");
	}
	
	private static void initInput() throws IOException {
		cityCount = Integer.parseInt(br.readLine().trim());
		busCount =  Integer.parseInt(br.readLine().trim());
		
		adjMatrix = new int[cityCount][cityCount];
		for (int[] row : adjMatrix) {
			Arrays.fill(row, INF);
		}
		
		for (int info = 0; info < busCount; info++) {
			st = new StringTokenizer(br.readLine().trim());
			city1 = Integer.parseInt(st.nextToken()) - 1;
			city2 = Integer.parseInt(st.nextToken()) - 1;
			cost = Integer.parseInt(st.nextToken());
			adjMatrix[city1][city2] = Math.min(adjMatrix[city1][city2], cost);
		}
		st = new StringTokenizer(br.readLine().trim());
		startCity = Integer.parseInt(st.nextToken()) - 1;
		endCity = Integer.parseInt(st.nextToken()) - 1;
	}
}
