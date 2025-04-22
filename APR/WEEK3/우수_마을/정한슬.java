import java.util.*;
import java.io.*;

/*
 * 두 마을이 인접하면 두 마을 모두 우수마을로 선정할 수 없다.
 * 우수 마을로 선정되지 못한 마을은 적어도 하나의 우수 마을과 인접해야한다.
 * 
 * 우수 마을로 선정된 마을 주민의 수의 총합을 최대로 해야한다.
 * => 우수 마을로 선정된 마을 주민의 수 총합 출력
 * 
 * dp 배열... 만들어서 선택하고/ 안하고를 0 ,1 로 구분한 뒤 맥스값을 return..
 * */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	
	static int villageCount;
	static int[] people;
	static int connectCount;
	static List<Integer>[] graph;
	static int[][] dp;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		visited = new boolean[villageCount];
		dp = new int[villageCount][2];
		
		dfs(0); // 루트 마을 0번부터 탐색 시작
		
		bw.write(String.valueOf(Math.max(dp[0][0], dp[0][1])));
		bw.flush();
		bw.close();
	}

	private static void dfs(int curVillage) {
		visited[curVillage] = true;
		dp[curVillage][0] = 0; // 현재 마을을 우수 마을로 선택 안함
		dp[curVillage][1] = people[curVillage]; // 현재 마을을 우수 마을로 선택함

		if (graph[curVillage] == null) return;

		for (int next : graph[curVillage]) {
			if (visited[next]) continue;

			dfs(next);

			dp[curVillage][0] += Math.max(dp[next][0], dp[next][1]);
			dp[curVillage][1] += dp[next][0]; // 자식이 우수 마을이면 안 됨
		}
	}	
	
	private static void initInput() throws IOException {
		villageCount = Integer.parseInt(br.readLine().trim());
		
		people = new int[villageCount];
		st = new StringTokenizer(br.readLine().trim());
		for (int village = 0; village < villageCount; village++) {
			people[village] = Integer.parseInt(st.nextToken());
		}
		
		graph = new ArrayList[villageCount];
		connectCount = villageCount - 1;
		for (int connect = 0; connect < connectCount; connect++) {
			st = new StringTokenizer(br.readLine().trim());
			int village1 = Integer.parseInt(st.nextToken()) - 1;
			int village2 = Integer.parseInt(st.nextToken()) - 1;
			if (graph[village1] == null) graph[village1] = new ArrayList<>();
			if (graph[village2] == null) graph[village2] = new ArrayList<>();
			
			graph[village1].add(village2);
			graph[village2].add(village1);
		}
	}
}
