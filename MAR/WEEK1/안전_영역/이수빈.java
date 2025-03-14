import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 안전 영역의 최대 개수
 */
public class 이수빈 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int[][] board;
	static boolean[][] visited;
	static int size, maxScore, minScore;
	
	static int[] dr = {0, 0, -1, 1};
	static int[] dc = {1, -1, 0, 0};
	
	public static void init() throws Exception {
		maxScore = Integer.MIN_VALUE;
		minScore = Integer.MAX_VALUE;
		size = Integer.parseInt(br.readLine().trim());
		
		visited = new boolean[size][size];
		board = new int[size][size];
		
		for (int row = 0; row < size; row++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int col = 0; col < size; col++) {
				board[row][col] = Integer.parseInt(st.nextToken());
				maxScore = Math.max(board[row][col], maxScore);
				minScore = Math.min(board[row][col], minScore);
			}
		}
	}
	
	public static void initVisited() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				visited[row][col] = false;
			}
		}
	}
	
	public static void getAreaCnt(int row, int col, int score) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {row, col});
		visited[row][col] = true;
		
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int curRow = cur[0];
			int curCol = cur[1];
			
			for (int dir = 0; dir < 4; dir++) {
				int nextRow = curRow + dr[dir];
				int nextCol = curCol + dc[dir];
				
				if (nextRow < 0 || nextCol < 0 || nextRow >= size || nextCol >= size) continue;
				if (!visited[nextRow][nextCol] && board[nextRow][nextCol] > score) {
					visited[nextRow][nextCol] = true;
					q.offer(new int[] {nextRow, nextCol});
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		//--------------솔루션 코드를 작성하세요.--------------------------------
		init();
		
		int result = 1;
		for (int score = minScore; score <= maxScore; score++) {
			initVisited();
			int cnt = 0;
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					if (!visited[row][col] && board[row][col] > score) {
						getAreaCnt(row, col, score);
						cnt++;
					}
				}
			}
			result = Math.max(result, cnt);
		}
		System.out.println(result);
	}

}
