import java.io.*;
import java.util.*;

public class 정한슬 {
	static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	static final int EMPTY = -1;
	static final int RIPE_TOMATO = 1;
	
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	
	static int height;
	static int width;
	static int basketCount;

	static List<int[]> ripeTomatoes;
	static int[][][] tomatoes;
	static int minDay;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		
		minDay = Integer.MAX_VALUE; // 초기화
		goingToRipe();

		bw.write(String.valueOf(minDay));
		bw.flush();
		bw.close();
	}
	
	private static void goingToRipe() {
		if (ripeTomatoes.size() ==basketCount  * width * height) { // 초기 상태가 다 익은 상태면 0
			minDay = 0;
			return; 
		}
		 
		Queue<int[]> queue = new ArrayDeque<>();
		    
	    // 모든 익은 토마토를 시작점으로 큐에 추가
	    for (int[] pos : ripeTomatoes) {
	        queue.offer(pos);
	    }

	    while (!queue.isEmpty()) {
	        int[] cur = queue.poll();
	        int basketIdx = cur[0];
	        int row = cur[1];
	        int col = cur[2];

	        for (int[] dir : DIRECTIONS) {
	            int newRow = row + dir[0];
	            int newCol = col + dir[1];

	            if (newRow < 0 || newCol < 0 || newRow >= height || newCol >= width) continue;
	            if (tomatoes[basketIdx][newRow][newCol] == 0) {
	                tomatoes[basketIdx][newRow][newCol] = tomatoes[basketIdx][row][col] + 1;
	                queue.offer(new int[]{basketIdx, newRow, newCol});
	            }
	        }
	        
//	        System.out.println("basketIdx: " + basketIdx);
	        
	        for (int basketDir = -1; basketDir < 2; basketDir += 2) {
	        	int nextBasketIdx = basketIdx + basketDir;
	        	if (nextBasketIdx < 0 || nextBasketIdx >= basketCount) continue;
//	        	System.out.println("nextBasketIdx: " + nextBasketIdx);	     
	        	if (tomatoes[nextBasketIdx][row][col] == 0) {
	                tomatoes[nextBasketIdx][row][col] = tomatoes[basketIdx][row][col] + 1;
	                queue.offer(new int[]{nextBasketIdx, row, col});
	            }
	        }
	    }

	    int max = 0;
	    for (int r = 0; r < basketCount; r++) {
	    	for (int i = 0; i < height; i++) {
		        for (int j = 0; j < width; j++) {
		            if (tomatoes[r][i][j] == 0) { // 안익은 토마토가 있다면 -1 
		                minDay = -1;
		                return;
		            }
		            max = Math.max(max, tomatoes[r][i][j]);
		        }
		    }
	    }
	    
	    minDay = max - 1;
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());
		basketCount = Integer.parseInt(st.nextToken());
				
		ripeTomatoes = new ArrayList<>();
		tomatoes = new int[basketCount][height][width];
		for (int r = 0; r < basketCount; r++) {
			for (int row = 0; row < height; row++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int col = 0; col < width; col++) {
					tomatoes[r][row][col] = Integer.parseInt(st.nextToken());
					if (tomatoes[r][row][col] == RIPE_TOMATO) ripeTomatoes.add(new int[] {r, row, col});
				}
			}
		}
		
	}
}
