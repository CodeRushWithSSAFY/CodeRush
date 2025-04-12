import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	static class Edge {
		int vertex, weight;
		
		Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
	}
	
	static int vertexCnt, edgeCnt;
	static List<Edge>[] graph;
	
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		vertexCnt = Integer.parseInt(st.nextToken());
		edgeCnt = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[vertexCnt + 1];
		for (int idx = 1; idx <= vertexCnt; idx++) {
			graph[idx] = new ArrayList<>();
		}
		
		for (int cnt = 0; cnt < edgeCnt; cnt++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			graph[node1].add(new Edge(node2, weight));
			graph[node2].add(new Edge(node1, weight));
		}
	}
	
	public static int prim() {
		PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> {
			return Integer.compare(e1.weight, e2.weight);
		});
		pq.offer(new Edge(1, 0));
		boolean[] visited = new boolean[vertexCnt + 1];
		int totalWeight = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			
			if (visited[cur.vertex]) continue;
			
			visited[cur.vertex] = true;
			totalWeight += cur.weight;
			
			for (int idx = 0; idx < graph[cur.vertex].size(); idx++) {
				Edge nextNode = graph[cur.vertex].get(idx);

				pq.offer(new Edge(nextNode.vertex, nextNode.weight));
			}
		}
		
		return totalWeight;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(prim());
	}
}
