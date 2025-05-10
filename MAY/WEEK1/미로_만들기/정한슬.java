import java.util.*;
import java.io.*;

/*
 * 흰방으로만 이동 가능, 검은 방을 흰 방으로 바꿀 수 있다고 가정할때
 * 시작점 -> 끝점으로 갈때 이동경로를 검은 방을 흰 방으로 바꾸는 최소의 수를 출력. 
 * 그냥 바꾸지않고 갈 수 있으면 0
 * 
 * 시작점(0, 0) -> 끝점(roomSize - 1, roomSize - 1)
 * 
 * 일반 BFS하면 최단 경로만 생각해서 답이 2인데도 5가 나옴. 최단으로 가느라
 * DFS는 stack over flow,,
 * 
 * 0-1 BFS로 풀이
 * 0-1 BFS는 길마다 통과 비용이 0 또는 1인 미로를 가장 빠르게 탈출하는 방법을 알려주는 특수한 BFS
 * 
 * 비용이 0인것을 addFirst로 하고 1은 addLast로 넣어둔다. 그 다음 addFirstPoll로 값이 작은거 먼저 다 찾고 그 후에 찾는 로직인것 같음
 * 우선순위 PQ 를 사용해도 되나 Deque가 조금 더 가성비 있다. 정렬할 필요가 없어서
 * */
public class 정한슬 {
	static final int BLACK = 0;
	static final int WHITE = 1;
	static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	
	static int roomSize;
	static int[][] room;
	static int changeCount;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		changeCount = Integer.MAX_VALUE; // 초기화
		gameStart();
		
		bw.write(String.valueOf(changeCount));
		bw.flush();
		bw.close();
	}
	
	
	private static void gameStart() {
		Deque<int[]> que = new ArrayDeque<>();
		que.addFirst(new int[] {0, 0}); // row, col
		int[][] dist = new int[roomSize][roomSize];
		for (int[] rowDistance : dist) {
			Arrays.fill(rowDistance, Integer.MAX_VALUE);
		}
		dist[0][0] = 0;
		
		while (!que.isEmpty()) {
			int[] curStatus = que.pollFirst();
			int curRow = curStatus[0];
			int curCol = curStatus[1];

			for (int d = 0; d < 4; d++) {
				int nextRow = curRow + DIRECTIONS[d][0];
				int nextCol = curCol + DIRECTIONS[d][1];
			
				if (!isInRoom(nextRow, nextCol)) continue;
				
				int cost = room[nextRow][nextCol] == BLACK ? 1 : 0;
				if (dist[nextRow][nextCol] <= dist[curRow][curCol] + cost) continue;
				
				dist[nextRow][nextCol] = dist[curRow][curCol] + cost;
				
				if (cost == 1) que.addFirst(new int[] {nextRow, nextCol}); 
				else {
					que.addLast(new int[] {nextRow, nextCol}); 
				}
			}
		}
		changeCount = Math.min(changeCount, dist[roomSize - 1][roomSize - 1]);
	}
	
	private static boolean isInRoom(int row, int col) {
		return row >= 0 && row < roomSize && col >= 0 && col < roomSize;
	}
	
	private static void initInput() throws IOException {
		roomSize = Integer.parseInt(br.readLine().trim());
		
		room = new int[roomSize][roomSize];
		for (int row = 0; row < roomSize; row++) {
			String line = br.readLine().trim();
			for (int col = 0; col < roomSize; col++) {
				room[row][col] = line.charAt(col) - '0';
			}
		}
	}
}
