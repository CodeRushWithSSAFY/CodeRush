package feb2_week_maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * 
 * 2주차 - 나무섭지
 * 
 * ## BFS
 * 
 * # 전략
 * 	- 1. 남우가 출구에 도달할 수 있는 모든 경우의 수 탐색
 * 		- 1-1. 출구에 도달할 수 있는 방법이 없다면 "No" 출력
 * 		- 1-2. 출구에 도달할 수 있는 방법이 1개 이상이라면 기록 하고, 2번으로 이동
 * 			- 기록 : 탈출구 위치에 최단 경로에 소요되는 시간 기록  
 * 
 * 	- 2. ** 가설 : 남우가 출구에 도착할 수 있는 최단시간 이하의 시간으로 출구에 도착할 수 있는 유령이 있다면 무조건 탈출 불가능
 * 		- 2-1. 위 명제가 참이라면, 남우가 최단시간보다 더 오래 걸려서 탈출하는 모든 경우에도 유령이 남우를 잡을 수 있음(출구에서 존버)
 * 		- 2-2. 가능성 있는 하나의 경우 : 남우가 유령보다 출구에 먼저 도착할 수 있으면서, 중간에서 유령이 잡을 수 있는 경우 
 * 			- 불가능! 같은 시점에 한 칸에 유령과 남우가 같이 있을 수 있다면, 유령은 최소 남우의 해당 경로 시간 안에 출구에 도달 가능
 * 	 
 * 	- 따라서 남우의 최단경로를 구하고, 유령 중에 남우보다 먼저 출구에 도달 가능한 유령이 있다면 "No" 출력
 * 	- 그렇지 않다면 "Yes" 출력
 * 
 *  
 * # 구현
 * 	- 1. 입력 받기 
 * 		- 1-1. 입력이 '.'이 아닐때, 입력값을 보고 해당 좌표를 기록 
 * 
 * 
 * 	- 2. 유령 최단경로 찾기
 * 		- 2-1. 유령의 위치와 출구 위치의 좌표값 차이로 최단경로 찾을 수 있음 (유령은 벽의 영향을 받지 않기 때문)
 * 		- 2-2. 전체 유령 중 가장 시간이 짧은 유령의 최단 시간만 기록 
 * 
 * 
 * 	- 3. 남우 BFS
 * 		- 3-1. 방문한 지점은 #으로 표시 (벽으로 표시하기)
 * 		- 3-2. 시간 기록 -> 유령의 최단시간을 넘는 순간 바로 종료, "No" 출력 
 * 
 */


public class Main {
	
	private static int row;	// 미로의 행 수 
	private static int col;	// 미로의 열 수 
	
	private static char[][] mazeGrid;	// 2차원 배열 : 미로 
	private static int[][] timeGrid;	// 2차원 배열 : 해당 지점에서의 최단 경로 시간 
	
	private static int ghostMinTime = Integer.MAX_VALUE;	// 유령의 최단 시간 
	
	// 위치 표시 : [ rowIdx , colIdx ] 
	private static int[] positionNam;			// 남우의 초기 위치 
	private static int[] positionExit;			// 출구의 초기 위치 
	private static List<int[]> positionGhost;	// 유령의 초기 위치 -> 유령의 수가 미정이므로 List로 관리
	
	private static Queue<int[]> bfsQueue;		// bfs를 위한 큐 
	
	private static int[] dx = {0, 0, -1, 1};
	private static int[] dy = {1, -1, 0, 0};
	
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		row = Integer.parseInt(st.nextToken());
		col = Integer.parseInt(st.nextToken());
		
		mazeGrid = new char[row][col];
		timeGrid = new int[row][col];
		positionGhost = new ArrayList<>();
		bfsQueue = new ArrayDeque<>();
		
		// 입력 받기 
		for(int rowIdx = 0; rowIdx < row; rowIdx++) {
			String inputLine = br.readLine();
			for(int colIdx = 0; colIdx < col; colIdx++) {
				char input = inputLine.charAt(colIdx);
				
				if(input != '.') {
					
					if(input == 'G') {	// 확률이 가장 높은 G를 먼저 비교 
						positionGhost.add(new int[] {rowIdx, colIdx});
					}else if(input == 'N') {
						positionNam = new int[] {rowIdx, colIdx};
					}else if(input == 'D') {
						positionExit = new int[] {rowIdx, colIdx};
					}
					
				}
				
				mazeGrid[rowIdx][colIdx] = input;
			}
		}
		
		
		// 유령 최단 경로 계산 
		for(int[] ghost : positionGhost) {
			int X = ghost[0];
			int Y = ghost[1];
			
			int time = Math.abs(positionExit[0] - X) + Math.abs(positionExit[1] - Y); 
			
			ghostMinTime = Math.min(ghostMinTime, time);
		}
		
		// bfs 수행 
		boolean escaped = bfs();
		
		
		// 결과 출력 
		if(escaped) {
			System.out.println("Yes");
			return;
		}
		
		System.out.println("No");
		return;
		
	}
	
	
	
	private static boolean bfs() {
		int currX = positionNam[0];
		int currY = positionNam[1];
		int[] nextPosition = new int[2];
		
		// 시작 위치를 큐에 삽입
		bfsQueue.offer(new int[] {currX, currY});
		
		// 시작 위치 방문 처리  
		mazeGrid[currX][currY] = '#';
		timeGrid[currX][currY] = 0;
		
		while(!bfsQueue.isEmpty()) {
			nextPosition = bfsQueue.poll();
			currX = nextPosition[0];
			currY = nextPosition[1];
			
			// 탈출구 도착 
			if(currX == positionExit[0] && currY == positionExit[1]) return true;
			
			for(int direction = 0; direction < 4; direction++) {
				int nextX = currX + dx[direction];
				int nextY = currY + dy[direction];
				
				// 범위 확인
				if(nextX >= 0 && nextX < row && nextY >= 0 && nextY < col) {
					
					// 벽인지, 이미 방문한 지점인지 확인
					if(mazeGrid[nextX][nextY] != '#') {
						timeGrid[nextX][nextY] = timeGrid[currX][currY] + 1;
						
						// 유령의 최소 시간 초과 -> 탈출 실패(유령에게 잡힘) 
						if(timeGrid[nextX][nextY] >= ghostMinTime) return false;
						
						// 방문 표시 => 벽으로 표시
						mazeGrid[nextX][nextY] = '#';
						bfsQueue.offer(new int[] {nextX, nextY});
					}
				}
			}
		}
		
		// 큐가 비었는데 탈출구에 도달 못함 -> 탈출 실패 (탈출구 도달 불가능)
		return false;
		
	}

}