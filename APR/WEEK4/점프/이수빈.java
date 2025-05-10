import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int size;
	static int[][] board;
	static long[][] caseCnt;
	
	public static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		size = Integer.parseInt(br.readLine().trim());
		board = new int[size][size];
		caseCnt = new long[size][size];
		for (int row = 0; row < size; row++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int col = 0; col < size; col++) {
				board[row][col] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		init();
		
		caseCnt[size-1][size-1] = 1;
		for (int row = size - 1; row >= 0; row--) {
			
			for (int col = size - 1; col >= 0; col--) {
				if (row == size - 1 && col == size - 1) continue;
				
				int rightCol = col + board[row][col];
				int downRow = row + board[row][col];
				
				if (rightCol < size && downRow < size) {
					caseCnt[row][col] = caseCnt[row][rightCol] + caseCnt[downRow][col];
				} else if (rightCol < size && downRow >= size) {
					caseCnt[row][col] = caseCnt[row][rightCol];
				} else if (rightCol >= size && downRow < size) {
					caseCnt[row][col] = caseCnt[downRow][col];
				}
			}
		}
		System.out.println(caseCnt[0][0]);
		
	}
}
