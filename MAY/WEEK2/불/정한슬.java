import java.io.*;
import java.util.*;

public class 정한슬{
	private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	private static final String IMPOSSIBLE = "IMPOSSIBLE";
	private static final char FIRE = 'F';
	private static final char JIHUN = 'J';
	private static final char WALL = '#';
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int minTime;
	static int R;
	static int C;
	static char[][] board;
	static int[] jihunPosition;
	static List<int[]> fireStartPosition;
	
	static int[][] fireMemo;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		
		br.close();
		minTime = Integer.MAX_VALUE;
		fire();
		escape();
		
		if (minTime == Integer.MAX_VALUE) bw.write(IMPOSSIBLE);
		else bw.write(String.valueOf(minTime));
		bw.flush();
		bw.close();
	}
	
	private static void escape() {
//		for (char[] rows : board) {
//			System.out.println(Arrays.toString(rows));
//		}
//		System.out.println();
//		
//		for (int[] rows : fireMemo) {
//			System.out.println(Arrays.toString(rows));
//		}
		
		if (isEscape(jihunPosition[0], jihunPosition[1])) {
	        minTime = 1;
	        return;
	    }
		
//		System.out.println("escape");
		PriorityQueue<int[]> jihunPQ = new PriorityQueue<>(
				(o1, o2) -> Integer.compare(o1[2], o2[2]));
		
		boolean[][] visited = new boolean[R][C];
		jihunPQ.add(new int[] {
				jihunPosition[0], jihunPosition[1], 0}); // row, col, time
		visited[jihunPosition[0]][jihunPosition[1]] = true; 
		while (!jihunPQ.isEmpty()) {
			int[] curStatus = jihunPQ.poll();
			int row = curStatus[0];
			int col = curStatus[1];
			int time = curStatus[2];
			
//			System.out.println("row: " + row + " col: " + col + " time: " + time);
//			visited[row][col] = true;
			
			for (int d = 0; d < 4; d++) {
				int nextRow = row + DIRECTIONS[d][0];
				int nextCol = col + DIRECTIONS[d][1];
				int nextTime = time + 1;
                
				if (!isInBoard(nextRow, nextCol)) continue;
				if (visited[nextRow][nextCol]) continue;
				if (board[nextRow][nextCol] == WALL) continue;
				
				if (fireMemo[nextRow][nextCol] <= nextTime) continue;
//				System.out.println("nextRow: " + nextRow + " nextCol: " + nextCol + " time: " + time);
				if(isEscape(nextRow, nextCol)) {
					minTime = Math.min(minTime, nextTime + 1);
					return;
				} 
				visited[nextRow][nextCol] = true; 
				jihunPQ.add(new int[] {nextRow, nextCol, nextTime});
			}
		}
	}
	
	private static void fire() {
		fireMemo = new int[R][C];
		for (int[] rows : fireMemo) {
			Arrays.fill(rows, Integer.MAX_VALUE);
		}
		PriorityQueue<int[]> firePQ = new PriorityQueue<>(
				(o1, o2) -> Integer.compare(o1[2], o2[2]));
		
		boolean[][] visited = new boolean[R][C];
		for (int[] firePosition : fireStartPosition) {
			firePQ.add(new int[] {
					firePosition[0], firePosition[1], 0}); // row, col, time		
			visited[firePosition[0]][firePosition[1]] = true;
		}

		while (!firePQ.isEmpty()) {
			int[] curStatus = firePQ.poll();
			int row = curStatus[0];
			int col = curStatus[1];
			int time = curStatus[2];
			
			fireMemo[row][col] = Math.min(fireMemo[row][col], time);
			
			for (int d = 0; d < 4; d++) {
				int nextRow = row + DIRECTIONS[d][0];
				int nextCol = col + DIRECTIONS[d][1];
				int nextTime = time + 1;
                
				if (!isInBoard(nextRow, nextCol)) continue;
				if (board[nextRow][nextCol] == WALL) continue;

				if (visited[nextRow][nextCol]) continue;
				if (fireMemo[nextRow][nextCol] <= nextTime) continue;

				visited[nextRow][nextCol] = true;
				fireMemo[nextRow][nextCol] = nextTime;
				firePQ.add(new int[] {nextRow, nextCol, nextTime});
			}
		}
	}
	
	private static boolean isInBoard(int row, int col) {
		return row >= 0 && row < R && col >= 0 && col < C;
	}
	
	private static boolean isEscape(int row, int col) { // 가장 자리일때..!
//		return (row < 0 && col < 0) || (row >= R && col < 0)
//				|| (row < 0 &&col >= C) || (row <= R && col >= C);
		return row == 0 || row == R - 1 || col == 0 || col == C - 1;
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		fireStartPosition = new ArrayList<>();
		for (int row = 0; row < R; row++) {
			String line = br.readLine().trim();
			for (int col = 0; col < C; col++) {
				board[row][col] = line.charAt(col);
				if (board[row][col] == FIRE) fireStartPosition.add(new int[] {row, col});
				else if (board[row][col] == JIHUN) jihunPosition = new int[] {row, col};
			}
		}
	}
}
