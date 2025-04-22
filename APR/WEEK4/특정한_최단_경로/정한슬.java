import java.util.*;
import java.io.*;

/*
 * 세준이는 1번 정점에서 N번 정점으로 최단 거리로 이동하려고 한다.
 * 
 * 조건
 * 1. 임의로 주어진 두 정점은 반드시 통과해야한다.
 * 2. 한번 이동했던 정점 뿐만아니라 이동했던 간선도 다시 이동할 수 있다.
 * 
 * 1번 정점 -> N번 정점 이동할 때 주어진 두 정점을 반드시 거치면서 최단 경로로 이동하는 프로그램 작성 
 * 
 * 그러한 경로가 없을 때는 -1을 출력
 * 
 * 총 3가지의 다익스트라 distance 배열을 만든다.
 * 1. 시작 정점에서부터 모든 정점까지의 최단 거리 저장
 * 2. 반드시 자나가야하는 정점1에서 모든 정점까지의 최단거리 저장
 * 3. 반드시 지나가야하는 정점2에서 모든 정점까지의 최단 거리 저장
 * 
 * 그럼 문제에서 원하는 경로는 2가지로 생각할 수 있다.
 * 1. 0 -> mustPassVertex1 -> mustPassVertex2 -> N - 1
 * 2. 0 -> mustPassVertex2 -> mustPassVertex1 -> N - 1
 * 그 중 최단 거리를 갱신
 * */
public class 정한슬 {
	static final int INF = 100_000_000;
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int vertexCount;
	static int edgeCount;
	static List<int[]>[] graph;
	static int mustPassVertex1;
	static int mustPassVertex2;
	static int[][] minDistances;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		calAllMinDistances();
		bw.write(String.valueOf(result()));
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static int result() {
		int minDistance = Integer.MAX_VALUE;
		
		// 0 -> mustPassVertex1 -> mustPassVertex2 -> N - 1
		int tmp = 0;
		if (minDistances[0][mustPassVertex1] != INF && 
				minDistances[1][mustPassVertex2] != INF &&
					minDistances[2][vertexCount - 1] != INF) {
			tmp += minDistances[0][mustPassVertex1];
			tmp += minDistances[1][mustPassVertex2];
			tmp += minDistances[2][vertexCount - 1];
		}
		if (tmp != 0) minDistance = Math.min(minDistance, tmp);
		
		// 0 -> mustPassVertex2 -> mustPassVertex1 -> N - 1
		 tmp = 0;
		if (minDistances[0][mustPassVertex2] != INF && 
				minDistances[2][mustPassVertex1] != INF &&
					minDistances[1][vertexCount - 1] != INF) {
			tmp += minDistances[0][mustPassVertex2];
			tmp += minDistances[2][mustPassVertex1];
			tmp += minDistances[1][vertexCount - 1];
		}
		if (tmp != 0) minDistance = Math.min(minDistance, tmp);
		
		return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
	}
	
	private static void calAllMinDistances() {
		minDistances = new int[3][vertexCount]; // startVertex(0), 지나가야하는 정점1, 지나가야하는 정점2에서 갈 수 있는 모든 거리를 구한다.
		minDistances[0] = calMinDistance(0);
		minDistances[1] = calMinDistance(mustPassVertex1);
		minDistances[2] = calMinDistance(mustPassVertex2);
	}
	
	private static int[] calMinDistance(int standardVertex) {
		int[] distance = new int[vertexCount];
		Arrays.fill(distance, INF);
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
		distance[standardVertex] = 0;
		pq.add(new int[] {standardVertex, 0});
		
		while (!pq.isEmpty()) {
			int[] curStatus = pq.poll();
			int fromVertex = curStatus[0];
			int cost = curStatus[1];
			
			if (distance[fromVertex] < cost) continue; // 지금 보고 있는 비용이 기존에 갱신된 비용보다 비싸면 이 길로 가지 않는다.
			
			if (graph[fromVertex] == null) continue; // 여기서 지나갈 수 있는 정점이 없으면 끝..
			
			for (int[] nextStatus : graph[fromVertex]) {
				int toVertex = nextStatus[0];
				int toCost = nextStatus[1];
				
				if (distance[toVertex] > distance[fromVertex] + toCost) {
					distance[toVertex] = distance[fromVertex] + toCost;
					pq.add(new int[] {toVertex, toCost});
				}
			}
		}
		
		
		return distance;
	}
	
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine());
		
		vertexCount = Integer.parseInt(st.nextToken());
		edgeCount = Integer.parseInt(st.nextToken());
		graph = new ArrayList[vertexCount];

		for (int info = 0; info < edgeCount; info++) {
			st = new StringTokenizer(br.readLine());
			int vertex1 = Integer.parseInt(st.nextToken()) - 1;
			int vertex2 = Integer.parseInt(st.nextToken()) - 1;
			int distance = Integer.parseInt(st.nextToken());
			
			if (graph[vertex1] == null) graph[vertex1] = new ArrayList<>();
			if (graph[vertex2] == null) graph[vertex2] = new ArrayList<>();
			
			graph[vertex1].add(new int[] {vertex2, distance});
			graph[vertex2].add(new int[] {vertex1, distance});
		}

		st = new StringTokenizer(br.readLine());
		mustPassVertex1 = Integer.parseInt(st.nextToken()) - 1;
		mustPassVertex2 = Integer.parseInt(st.nextToken()) - 1;
	}
}
