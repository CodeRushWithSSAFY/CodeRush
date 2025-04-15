import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 	크루스칼
 * 	1. 간선 입력 받는다.
 * 	2. 간선을 가중치 오름차순으로 정렬
 * 	3. 각 간선의 정점들을 union
 * 	4. union 성공하면 결과에 해당 간선의 가중치를 더해주고 간선 카운트++
 * 	5. 간선 카운트가 정점 개수 - 1이 되면 break
 */
public class Main {
	static StringTokenizer st;
	static BufferedReader br;
	
	static class Edge {
		int from, to, weight;

		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	
	static int vertex, edge;
	static Edge[] edgeList;
	static int[] parentList, rankList;
	
	public static void init() throws Exception {
		vertex = Integer.parseInt(br.readLine().trim());
		edge = Integer.parseInt(br.readLine().trim());
		
		edgeList = new Edge[edge];
		for (int idx = 0; idx < edge; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			edgeList[idx] = new Edge(from, to, weight);
		}
	}
	
	public static void make() {
		parentList = new int[vertex + 1];
		rankList = new int[vertex + 1];
		
		for (int idx = 0; idx <= vertex; idx++) {
			parentList[idx] = idx;
			rankList[idx] = 0;
		}
	}
	
	public static int find(int element) {
		if (element == parentList[element]) return element;
		
		return parentList[element] = find(parentList[element]);
	}
	
	public static boolean union(int e1, int e2) {
		int e1Root = find(e1);
		int e2Root = find(e2);
		
		if (e1Root == e2Root) return false;
		
		if (rankList[e1Root] > rankList[e2Root]) {
			parentList[e2Root] = e1Root;
			return true;
		}
		
		parentList[e1Root] = e2Root;
		
		if (rankList[e1Root] == rankList[e2Root]) {
			rankList[e2Root]++;
		}
		return true;
	}
	
	public static long getMST() {
		make();
		Arrays.sort(edgeList, (e1, e2) -> {
			return Integer.compare(e1.weight, e2.weight);
		});
		
		long result = 0, edgeCnt = 0;
		for (Edge edge : edgeList) {
			if (union(edge.from, edge.to)) {
				result += (long)edge.weight;
				if (++edgeCnt == vertex - 1) {
					break;
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		init();
		System.out.println(getMST());
	}
}
