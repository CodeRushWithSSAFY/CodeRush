import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *	SWEA_1954_달팽이숫자
 *
 *	1. N을 입력받는다.
 *	2. 0,0에서 1부터 시작하여 숫자를 늘려준다.
 *	3. 우, 하, 좌, 상 순서대로 더 이상 갈 곳이 없으면 방향을 바꾼다.
 */
public class 이수빈 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int[][] board;
	
	static int[] dr = {0, 1, 0, -1}; // 우, 하, 좌, 상
	static int[] dc = {1, 0, -1, 0};
	
	static int direction = 0;
	static int N;
	
	
	public static void drawSnail(int row, int col, int num) {
		board[row][col] = num;
		
		if (num == N * N) return ;
		int nextRow = row + dr[direction % 4];
		int nextCol = col + dc[direction % 4];
		
		// 범위를 벗어나거나 이미 숫자가 채워졌을 때 방향을 바꾼다.
		if (nextRow < 0 || nextCol < 0 || nextRow >= N || nextCol >= N || board[nextRow][nextCol] != 0) {
			direction++;
			nextRow = row + dr[direction % 4];
			nextCol = col + dc[direction % 4];
		}
		drawSnail(nextRow, nextCol, num + 1);
	}
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine().trim());
		for (int testCase = 1; testCase <= T; testCase++) {
			System.out.println("#" + testCase);
			
			N = Integer.parseInt(br.readLine().trim());
			
			direction = 0;
			board = new int[N][N];
			drawSnail(0, 0, 1);
			
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < N; col++) {
					System.out.print(board[row][col] + " ");
				}
				System.out.println();
			}
		}
	}
}
