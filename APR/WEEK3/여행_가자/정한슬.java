import java.util.*;
import java.io.*;

/*
 * 뿌리가 같으면 여행 계획이 가능하다는 개념을 이용해서 U-F이용..
 * */
public class 정한슬 {
	static final String YES = "YES";
	static final String NO = "NO";

	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;

	static int cityCount, tourCityCount;
	static int[][] cityMap;
	static int[] tourList;
	static int[] parent;
//	static boolean[] visited;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		initInput();

		makeUnionSet();  // 연결된 도시끼리 union 수행

		bw.write(checkTrip() ? YES : NO);
		
		// dfs, bfs로도 풀이가능 
//		visited = new boolean[cityCount];
//		bfs(tourList[0]);  // 여행 경로의 첫 도시부터 BFS 시작
//
//		boolean allConnected = true;
//		for (int city : tourList) {
//			if (!visited[city]) {
//				allConnected = false;
//				break;
//			}
//		}
//
//		bw.write(allConnected ? YES : NO);
		bw.flush();
		bw.close();
		br.close();
	}

	private static boolean checkTrip() {
		int root = find(tourList[0]);
		for (int i = 1; i < tourList.length; i++) {
			if (find(tourList[i]) != root) {
				return false;
			}
		}
		return true;
	}

	private static void makeUnionSet() {
		parent = new int[cityCount];
		for (int i = 0; i < cityCount; i++) {
			parent[i] = i;
		}

		for (int i = 0; i < cityCount; i++) {
			for (int j = 0; j < cityCount; j++) {
				if (cityMap[i][j] == 1) {
					union(i, j);
				}
			}
		}
	}

	private static int find(int city) {
		if (parent[city] != city) {
			parent[city] = find(parent[city]);  // 경로 압축
		}
		return parent[city];
	}

	private static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		if (rootA != rootB) {
			parent[rootB] = rootA;
		}
	}

	private static void initInput() throws IOException {
		cityCount = Integer.parseInt(br.readLine().trim());
		tourCityCount = Integer.parseInt(br.readLine().trim());
		cityMap = new int[cityCount][cityCount];

		for (int i = 0; i < cityCount; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < cityCount; j++) {
				cityMap[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		tourList = new int[tourCityCount];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < tourCityCount; i++) {
			tourList[i] = Integer.parseInt(st.nextToken()) - 1; // 0-based
		}
	}
	
//	private static void bfs(int startCity) {
//		Queue<Integer> queue = new LinkedList<>();
//		queue.add(startCity);
//		visited[startCity] = true;
//
//		while (!queue.isEmpty()) {
//			int current = queue.poll();
//
//			for (int next = 0; next < cityCount; next++) {
//				if (cityMap[current][next] == 1 && !visited[next]) {
//					visited[next] = true;
//					queue.add(next);
//				}
//			}
//		}
//	}
	
//	private static void dfs(int city) {
//		visited[city] = true;
//
//		for (int next = 0; next < cityCount; next++) {
//			if (cityMap[city][next] == 1 && !visited[next]) {
//				dfs(next);
//			}
//		}
//	}
}
