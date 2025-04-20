import java.util.*;
import java.io.*;

/*
 * 플로이드 워샬
 * 정점:v 
 * 시간복잡도O(v^3) 이고 간선이 많아도 정점 기준으로 시간 복잡도가 정해진다.
 * DP 알고리즘. 모든 정점으로 부터 연결된 모든 최소 경로를 저장하는 것.
 * 음수 싸이클이 없을때만 !! (음수 사이클이 있을땐 벨만포드)
 * */
public class 정한슬 {
	 static final int INF = 1_000_000_000; // 충분히 큰 수 (INT_MAX 아님!)
	
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	static StringBuilder sb;
	
	static int cityCount;
	static int busCount;
	static int[][] busCost;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		floyd();
		
		checkBus();
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static void floyd() {
		// k: 경유 도시
		for (int k = 0; k < cityCount; k++) {
			for (int i = 0; i < cityCount; i++) {
				for (int j = 0; j < cityCount; j++) {
					if (busCost[i][j] > busCost[i][k] + busCost[k][j]) {
						busCost[i][j] = busCost[i][k] + busCost[k][j];
					}
				}
			}
		}
	}

	private static void checkBus() {
		sb = new StringBuilder();

		for (int i = 0; i < cityCount; i++) {
			for (int j = 0; j < cityCount; j++) {
				if (busCost[i][j] == INF) sb.append("0 ");
				else sb.append(busCost[i][j]).append(" ");
			}
			sb.append("\n");
		}
	}
	
	private static void initInput() throws IOException {
		cityCount = Integer.parseInt(br.readLine().trim());
		busCount = Integer.parseInt(br.readLine().trim());
		
		for (int row = 0; row < cityCount; row++) {
			for (int col = 0; col < cityCount; col++) {
				if(row == col) busCost[row][col] = 0;
				else busCost[row][col] = INF;
			}
		}
		
		for (int idx = 0; idx < busCount; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			int fromCity = Integer.parseInt(st.nextToken()) - 1;
			int toCity = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			if (busCost[fromCity][toCity] > cost) busCost[fromCity][toCity] = cost;
		}
	}
}
