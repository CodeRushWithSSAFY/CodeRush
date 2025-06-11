import java.util.*;
import java.io.*;

/*
 * 
 * 1번 vertex를 선자씨라고 판단하자,,
 * 크루스칼을 이용해서 최소 비용을 출력
 * */
public class Main {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int vertexCount;
	static int alreadyConnectEdgeCount;
	
	static int[][] positions;
	static double minLength;
	
	static double initLength;
	static int[] parents;
	static int[] ranks;
    static int edgeCount;

	static class Edge {
	    int u, v;
	    double cost;

	    Edge(int u, int v, double cost) {
	        this.u = u;
	        this.v = v;
	        this.cost = cost;
	    }
	}
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		
		br.close();
		bbangsang();
		bw.write(String.format("%.2f", initLength));
		bw.flush();
		bw.close();
	}
	
	private static void bbangsang() {
		PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.cost));

	    // 모든 간선 후보 추가
	    for (int i = 0; i < vertexCount; i++) {
	        for (int j = i + 1; j < vertexCount; j++) {
	            double cost = getDistance(
	                positions[i][0], positions[i][1],
	                positions[j][0], positions[j][1]
	            );
	            pq.offer(new Edge(i, j, cost));
	        }
	    }

	    while (!pq.isEmpty() && edgeCount < vertexCount - 1 ) {
	        Edge e = pq.poll();
	        if (union(e.u, e.v)) {
	            initLength += e.cost;
              edgeCount++;
	        }
	    }
	}
	
	private static void make() {
		parents = new int[vertexCount];
		ranks = new int[vertexCount];
		for (int elementIdx = 0; elementIdx < vertexCount; elementIdx++) {
			ranks[elementIdx] = 0;
			parents[elementIdx] = elementIdx;
		}
	}
	
	private static int find(int element1) {
		if (parents[element1] == element1) return element1;
		return parents[element1] = find(parents[element1]);
	}
	
	private static boolean union(int element1, int element2) {
		int parent1 = find(element1);
		int parent2 = find(element2);
		
		if (parent1 == parent2) return false;
		
		if (ranks[parent1] > ranks[parent2]) {
			parents[parent2] = parent1;
			return true;
		}
		
		if (ranks[parent1] == ranks[parent2]) ranks[parent2]++;
		parents[parent1] = parent2;
		
		return true;
	}
	
	private static double getDistance(int x1, int y1, int x2, int y2) {
		long dx = Math.abs(x1 - x2);
	    long dy = Math.abs(y1 - y2);
	    return Math.sqrt(dx * dx + dy * dy);
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		vertexCount = Integer.parseInt(st.nextToken());
		alreadyConnectEdgeCount = Integer.parseInt(st.nextToken());
		
		positions = new int[vertexCount][2];
		for (int vertexIdx = 0; vertexIdx < vertexCount; vertexIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			positions[vertexIdx] = new int[] {row, col};
		}
		
		
		make();
		initLength = 0;
    edgeCount = 0;
		for (int edgeIdx = 0; edgeIdx < alreadyConnectEdgeCount; edgeIdx++) {
			st = new StringTokenizer(br.readLine().trim());
      int element1 = Integer.parseInt(st.nextToken()) - 1;
			int element2 = Integer.parseInt(st.nextToken()) - 1;
      if (union(element1, element2)) edgeCount++;
            
//			initLength += getDistance(
//					positions[element1][0], positions[element1][1],
//					positions[element2][0], positions[element2][1]);
		}
	}
}
