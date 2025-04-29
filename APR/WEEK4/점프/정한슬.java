import java.util.*;
import java.io.*;

/*
 * bfs로 풀되 칸에 입력되어있는 값 만큼 아래, 오른쪽으로 이동한다.
 * --> 경로의 수가 너무 많아서 메모리 초과.. (경로의 개수는 263-1보다 작거나 같다.)
 * 
 * dp를 이용해서 풀이 ... ,,
 * */
public class 정한슬 {
	static final int[][] DIRECTIONS = {{1, 0}, {0, 1}};
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int boardSize;
	static int[][] board;
	static long totalCount;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		jump();
		bw.write(String.valueOf(totalCount));
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void jump() {
		long[][] dp = new long[boardSize][boardSize];
		
		dp[0][0] = 1;

		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				int jumpValue = board[row][col];
				
				if (jumpValue == 0 || dp[row][col] == 0) continue;
				
				if (row + jumpValue < boardSize) dp[row + jumpValue][col] += dp[row][col];
				if (col + jumpValue < boardSize) dp[row][col + jumpValue] += dp[row][col];
			}
		}
		
		totalCount = dp[boardSize - 1][boardSize - 1];
	}
	
	
//	private static void jumpBFS() {
//		totalCount = 0;
//		
//		Queue<int[]> queue = new ArrayDeque<>();
//		queue.add(new int[] {0, 0});
//		
//		while (!queue.isEmpty()) {
//			int[] curStatus = queue.poll();
//			int curRow = curStatus[0];
//			int curCol = curStatus[1];
//			
//			if (curRow == boardSize - 1 && curCol == boardSize - 1) {
//				totalCount++;
//			}
//			
//			for (int d = 0; d < 2; d++) {
//				int jumpValue = board[curRow][curCol];
//				int nextRow = curRow;
//				int nextCol = curCol;
//				
//				while (jumpValue > 0) {
//					nextRow += DIRECTIONS[d][0];
//					nextCol += DIRECTIONS[d][1];
//					jumpValue--;
//				}
//				
//				if (isInBoard(nextRow, nextCol)) queue.add(new int[] {nextRow, nextCol});
//			}
//		}
//	}
	
	private static boolean isInBoard(int row, int col) {
		return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
	}
	
	private static void initInput() throws IOException {
		boardSize = Integer.parseInt(br.readLine());
		
		board = new int[boardSize][boardSize];
		for (int row = 0; row < boardSize; row++) {
			st = new StringTokenizer(br.readLine());
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = Integer.parseInt(st.nextToken());
			}
		}
	}
}
