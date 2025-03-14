package MAR.WEEK2.석유_시추;

import java.util.*;

public class 박재환 {
    public static void main(String[] args) {
		int[][] land = {
				{0, 0, 0, 1, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 0},
				{1, 1, 0, 0, 0, 1, 1, 0}, 
				{1, 1, 1, 0, 0, 0, 0, 0},
				{1, 1, 1, 0, 0, 0, 1, 1}
		};
		
		System.out.println(new Solution().solution(land));
	}
	
	static class Solution {
		int width, height;
		int[] oilArea;
	    public int solution(int[][] land) {
	    	oilArea = new int[land[0].length];
	    	
	    	int maxOil = 0;
	    	
	    	height = land.length;
	    	width = land[0].length;
	    	
	    	// 1. 석유를 얻을 수 있는 구역과 양을 구한다.
	    	
	    	// 각 경우에서 석유를 얻을 수 있는 위치를 기록	    	
	    	boolean[][] isChecked = new boolean[height][width];	
	    	for(int x=0; x<height; x++) {
	    		for(int y=0; y<width; y++) {
	    			// 석유가 없거나 이미 체크한 구역은 패스
	    			if(land[x][y]==0 || isChecked[x][y]) continue;
	    			
	    			
	    			// 석유가 있고 체크해야한다면
	    			getOil(x, y, land, isChecked);
	    		}
	    	}
	    	
	    	
	    	// 2. 가로로 이동하며 각 Y 좌표에서 얻을 수 있는 최대값을 구한다.
	    	for(int y=0; y<width; y++) {
	    		maxOil = Math.max(maxOil, oilArea[y]);
	    	}
	    	return maxOil;
	    }
	    
	    /*
	     * 현 위치에서 얻을 수 있는 석유량과 최소Y, 최대 Y를 구한다.
	     * 탐색 위치는 상하좌우이다.
	     * [현 x, 현 y, 격자]
	     */
	    int[] dx = {0,1,0,-1};
	    int[] dy = {1,0,-1,0};
	    void getOil(int x, int y, int[][] land, boolean[][] isChecked) {
	    	int minY = y;
	    	int maxY = y;
	    	
	    	Queue<int []> queue = new LinkedList<>();
	    	// 현 위치 확인 처리
	    	queue.offer(new int[] {x,y});
	    	isChecked[x][y] = true;
	    	int area = 1;	// 현위치를 포함 시킨다.
	    	
	    	// 인접해 있는 모든 구역을 탐색한다. 
	    	while(!queue.isEmpty()) {
	    		int[]curPoint = queue.poll();
	    		int curX = curPoint[0];
	    		int curY = curPoint[1];
	    		
	    		for(int dir=0; dir<4; dir++){
	    			int nx = curX+dx[dir];
	    			int ny = curY+dy[dir];
	    			
	    			// 범위를 벗어나는 경우
	    			if(nx < 0 || ny < 0 || nx >= height || ny >= width) continue;
	    			// 이미 체크한 구역이거나, 석유가 없는 경우
	    			if(isChecked[nx][ny] || land[nx][ny] == 0) continue;
	    			
	    			// 현 위치 확인 처리
	    			isChecked[nx][ny] = true;
	    			queue.offer(new int[] {nx, ny});
	    			area++;
	    			
	    			// 최대 최소 y 값을 갱신한다.
	    			maxY=Math.max(maxY, ny);
	    			minY=Math.min(minY, ny);
	    		}
	    	}
	    	accOilSum(minY, maxY, area);
	    }
	    
	    /*
	     * 누적합을 구해준다.
	     */
	    void accOilSum(int minY, int maxY, int area) {
	    	
	    	for(int y=minY; y<=maxY; y++) {
	    		oilArea[y]+=area;
	    	}
	    	
	    }
	}

	
}

/* 
 * 하나의 시추관을 수직으로 뚫을 때 얻을 수 있는 가장 많은 석유량을 구한다.
 * 
 * 가로 세로는 최대 500이다. 
 * 1 은 석유가 있음을 나타낸다.
 * 
 * 정확성 10 초 
 * 
 * 가로로 한칸한칸 탐색?
 * 
 * 탐색 줄이기 -> 석유가 얼마나 있는지 사전에 구해놓는다면 계속해서 탐색할 필요가 없음 
 * 
 * 석유의 구역 [minY, maxY] 
 */
