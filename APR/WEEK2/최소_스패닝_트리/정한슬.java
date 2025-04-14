import java.util.*;
import java.io.*;

public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int vertexCount, edgeCount;
	static List<int[]>[] adjList;
	static int vertex1, vertex2, weight;
	static long minCost;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		findMinCostWitPrimPQ();
		bw.write(String.valueOf(minCost));
		bw.flush();
		bw.close();
	}
	
	private static void findMinCostWitPrimPQ() {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
		boolean[] isConnected = new boolean[vertexCount];
		
		pq.add(new int[] {0, 0});
		
		minCost = 0;
		while (!pq.isEmpty()) {
			int[] curStatus = pq.poll();
			int to = curStatus[0];
			int weight = curStatus[1];
			
			if (isConnected[to]) continue;
			
			isConnected[to] = true;
			minCost += weight;
			
			for (int[] adjVertexInfo : adjList[to]) {
				if (isConnected[adjVertexInfo[0]]) continue;
				pq.add(adjVertexInfo);
			}
		}
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		vertexCount = Integer.parseInt(st.nextToken());
		edgeCount = Integer.parseInt(st.nextToken());
		
		adjList = new ArrayList[vertexCount];
		for (int info = 0; info < edgeCount; info++) {
			st = new StringTokenizer(br.readLine().trim());
			vertex1 = Integer.parseInt(st.nextToken()) - 1;
			vertex2 = Integer.parseInt(st.nextToken()) - 1;
			weight = Integer.parseInt(st.nextToken());
			if (adjList[vertex1] == null) adjList[vertex1] = new ArrayList<>();
			if (adjList[vertex2] == null) adjList[vertex2] = new ArrayList<>();
			adjList[vertex1].add(new int[] {vertex2, weight});
			adjList[vertex2].add(new int[] {vertex1, weight});
		}
		
	}
}
