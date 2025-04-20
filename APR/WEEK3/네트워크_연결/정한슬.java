import java.util.*;
import java.io.*;

/*
 * 모든 컴퓨터를 연결하는데 최소 비용을 출력하라.
 * MST(최소 정점 트리)를 이용하여 연결 최소 비용을 구하자.
 * 1. U-F: 각각의 트리(집합)를 하나의 트리로 연결하는 것
 * 2. Prim: 트리 정점과 비트리 정점으로 나누어생각하여 하나의 트리에 연결하는 것. 
 * */
public class 정한슬 {
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	
	static int computerCount; // vertex
	static int networkCount; // edge
	static List<int[]>[] graph;
	static boolean[] isConnected;
	static int minCost;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		
		connectNetwork();
		
		bw.write(String.valueOf(minCost));
		bw.flush();
		bw.close();
	}
	
	private static void connectNetwork() {
		minCost = 0;
		
		PriorityQueue<int[]> networkPQ = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
		isConnected = new boolean[computerCount];
		
		networkPQ.add(new int[] {0, 0}); // 1번컴퓨터부터 연결
		
		while (!networkPQ.isEmpty()) {
			int[] curStatus = networkPQ.poll();
			int computer = curStatus[0];
			int cost = curStatus[1];
			
			if (isConnected[computer]) continue; //이미 연결된 컴퓨터면 스킵

			isConnected[computer] = true; 
			minCost += cost;
			
			if (graph[computer] == null) continue; // 바로 연결된게 없으면 넘어감(그런일은 없음)
			
			for (int idx = 0; idx < graph[computer].size(); idx++) {
				int[] nextStatus = graph[computer].get(idx);
				if (!isConnected[nextStatus[0]]) networkPQ.add(nextStatus);
			}
		}
	}
	
	private static void initInput() throws IOException {
		computerCount = Integer.parseInt(br.readLine().trim());
		networkCount = Integer.parseInt(br.readLine().trim());
		
		graph = new ArrayList[computerCount];
		for (int network = 0; network < networkCount; network++) {
			st = new StringTokenizer(br.readLine().trim());
			int computer1 = Integer.parseInt(st.nextToken()) - 1;
			int computer2 = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			if (graph[computer1] == null) graph[computer1] = new ArrayList<>();
			if (graph[computer2] == null) graph[computer2] = new ArrayList<>();
			
			graph[computer1].add(new int[] {computer2, cost});
			graph[computer2].add(new int[] {computer1, cost});
		}
	}
}
