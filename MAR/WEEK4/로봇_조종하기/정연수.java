import java.util.*;

import java.io.*;

public class Main {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer st;
	private static int[][] map;
	private static int[][] dp;
	private static int rowSize = 0;
	private static int colSize = 0;
	
	private static void makeDp(int row) {
		int[] leftRow = new int[colSize];
		int[] rightRow = new int[colSize];
		
		leftRow[0] = dp[row - 1][0] + map[row][0];
		for (int idx = 1; idx < colSize; idx++) {
			leftRow[idx] = map[row][idx] + Math.max(leftRow[idx - 1], dp[row - 1][idx]);
		}
		
		rightRow[colSize - 1] = dp[row - 1][colSize - 1] + map[row][colSize - 1];
		for (int idx = colSize - 2; idx >= 0; idx--) {
			rightRow[idx] = map[row][idx] + Math.max(rightRow[idx + 1], dp[row - 1][idx]);			
		}
		
		for (int idx = 0; idx < colSize; idx++) {
			dp[row][idx] = Math.max(leftRow[idx], rightRow[idx]);
		}
	}
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		map = new int[rowSize][colSize];
		dp = new int[rowSize][colSize];
		
		for (int idxRow = 0; idxRow < rowSize; idxRow++) {
			st = new StringTokenizer(br.readLine());
			for (int idxCol = 0; idxCol < colSize; idxCol++) {
				map[idxRow][idxCol] = Integer.parseInt(st.nextToken());
			}
		}
		
		dp[0][0] = map[0][0];
		for (int idxCol = 1; idxCol < colSize; idxCol++) {
			dp[0][idxCol] = dp[0][idxCol - 1] +  map[0][idxCol]; 
		}
		
		for (int idxRow = 1; idxRow < rowSize; idxRow++) {
			makeDp(idxRow);
		}
	
		System.out.println(dp[rowSize - 1][colSize - 1]);
	}
}
