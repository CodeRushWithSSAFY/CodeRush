package feb_2week_maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
 * 			- 기록 : 경로 + 해당 경로에 걸리는 시간
 * 			- 최단 경로 시간은 출발 지점에 기록 -> 이후 BFS를 할때 해당 지점을 지나면 더 계산 안해도 되도록!   
 * 
 * 	- 2. ** 가설 : 남우가 출구에 도착할 수 있는 최단시간 이하의 시간으로 출구에 도착할 수 있는 유령이 있다면 무조건 탈출 불가능
 * 		- 2-1. 위 명제가 참이라면 남우가 최단시간보다 더 오래 걸려서 탈출하는 모든 경우에도 유령이 남우를 잡을 수 있음(출구에서 존버)
 * 		- 2-2. 가능성 있는 하나의 경우 : 남우가 출구에 먼저 도착할 수 있으나 중간에서 유령이 잡을 수 있는 경우 
 * 			- 불가능! 같은 시점에 한 칸에 유령과 남우가 같이 있을 수 있다면 유령은 최소 남우의 해당 경로 시간 안에 출구에 도달 가능
 * 	 
 * 	- 따라서 남우의 최단경로를 구하고, 모든 유령 중에 남우보다 먼저 출구에 도달 가능한 유령이 있다면 "No" 출력
 * 	- 그렇지 않다면 "Yes" 출력
 * 
 *  
 * # 구현
 * 	- 1. 입력 받기 
 * 
 * 	- 2. 남우 BFS
 * 
 * 	- 3. 유령 BFS (BFS 순서도 최적화하면? -> 출구와 더 가까운 유령부터)
 * 
 * 
 * 
 * 
 */


public class Main {
	
	static int row;	// 미로의 행 수 
	static int col;	// 미로의 열 수 
	
	static char[][] mazeGrid;	// 2차원 배열 : 미로 
	static int[][] timeGrid;	// 2차원 배열 : 해당 지점에서의 최단 경로 시간 
	
	// 위치 표시 : [ rowIdx , colIdx ] 
	static int[] positionNam = new int[2];					// 남우의 초기 위치 
	static int[] positionExit = new int[2];					// 출구의 초기 위치 
	static List<int[]> positionGhost = new ArrayList<>();	// 유령의 초기 위치 -> 유령의 수가 미정이므로 List로 관리 
	
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		row = Integer.parseInt(st.nextToken());
		col = Integer.parseInt(st.nextToken());
		
		mazeGrid = new char[row][col];
		timeGrid = new int[row][col];
		
		for(int rowIdx = 0; rowIdx < row; rowIdx++) {
			st = new StringTokenizer(br.readLine());
			for(int colIdx = 0; colIdx <col; colIdx++) {
				mazeGrid[rowIdx][colIdx] = st.nextToken().charAt(0);
			}
		}
		
		
		
	}
	
	

	
	
	
	
	
	
	
}







