import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static class Edge implements Comparable<Edge> {
		int end;
		int price;
		
		public Edge(int end, int price) {
			this.end = end;
			this.price = price;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.price - o.price;
		}
	} 
	
	private static PriorityQueue<Edge> pq;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int nodeCnt = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		int edgeCnt = Integer.parseInt(st.nextToken());

		pq = new PriorityQueue<>();
		List<Edge>[] edges = new List[nodeCnt + 1];
		for (int i = 0; i < nodeCnt + 1; i++) {
			edges[i] = new ArrayList<>();
		}
		
		for (int edgeIdx = 0; edgeIdx < edgeCnt; edgeIdx++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int price = Integer.parseInt(st.nextToken());
			edges[start].add(new Edge(end, price));
			edges[end].add(new Edge(start, price));
		}
		
		int cnt = 0;
		long total = 0;
		int[] visit = new int[nodeCnt + 1];
		pq.add(new Edge(1, 0));

		while (cnt < nodeCnt) {
			Edge cur = pq.poll();
			
			if(visit[cur.end] == 1) continue;
			visit[cur.end] = 1;
			cnt++;
			total += cur.price;
			
			for (Edge edge : edges[cur.end]) {
				pq.add(edge);
			}
		}
		System.out.println(total);
	
	}
}
