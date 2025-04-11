import java.io.*;
import java.util.*;

/*
 * 최단경로 -> dijkstra ..
 * */
public class Main {
	static final int INF = Integer.MAX_VALUE;
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	static StringBuilder sb;
	
	static int vertexCount;
	static int edgeCount;
	static int startVertex;
	
	static List<int[]>[] graph; 
	static int[] lineConnections;
	static int to, from, distance;
	static int[] distances;
		
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		initInput();
		br.close();

		sb = new StringBuilder();
		checkMinDistance();
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static void checkMinDistance() {
	    distances = new int[vertexCount];
	    Arrays.fill(distances, INF);
	    distances[startVertex] = 0;

	    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
	    pq.add(new int[] {startVertex, 0});  // {vertex, distance}

	    while (!pq.isEmpty()) {
	        int[] current = pq.poll();
	        int curVertex = current[0];
	        int curDistance = current[1];

	        // 이미 더 짧은 경로로 방문한 적 있다면 skip
	        if (curDistance > distances[curVertex]) continue;

	        if (graph[curVertex] != null) {
	            for (int[] neighbor : graph[curVertex]) {
	                int nextVertex = neighbor[0];
	                int weight = neighbor[1];

	                if (distances[nextVertex] > curDistance + weight) {
	                    distances[nextVertex] = curDistance + weight;
	                    pq.add(new int[] {nextVertex, distances[nextVertex]});
	                }
	            }
	        }
	    }

	    for (int d : distances) {
	        sb.append(d == INF ? "INF" : d).append("\n");
	    }
	}

	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		
		vertexCount = Integer.parseInt(st.nextToken());
		edgeCount = Integer.parseInt(st.nextToken());
		startVertex = Integer.parseInt(br.readLine().trim()) - 1;
		
		graph = new ArrayList[vertexCount];
		for (int info = 0; info < edgeCount; info++) {
			st = new StringTokenizer(br.readLine().trim());
			from = Integer.parseInt(st.nextToken()) - 1;
			to = Integer.parseInt(st.nextToken()) - 1;
			distance = Integer.parseInt(st.nextToken());
			if (graph[from] == null) graph[from] = new ArrayList<>();
			graph[from].add(new int[] {to, distance});
		}
	}
}
