package MAR.WEEK2.땅따먹기;

import java.util.*;

public class 박재환 {

	public static void main(String[] args) {
		int[][] land = {
				{1,2,3,5},
				{5,6,7,8},
				{4,3,2,1}
		};
		
		System.out.println(new Solution().solution(land));
	}

	static class Solution {
		/*
		 * 바텀업? 탑다운? 
		 * 각 행마다 구할 수 있는 최대값을 갱신한다.
		 * 
		 * 마지막 행은 각 위치에서 얻을 수 있는 최대값을 기록하게 된다. 
		 */
	    int solution(int[][] land) {
	    	
	    	for(int row=1; row<land.length; row++) {
	    		land[row][0] += maxValue(0, row, land);
	    		land[row][1] += maxValue(1, row, land);
	    		land[row][2] += maxValue(2, row, land);
	    		land[row][3] += maxValue(3, row, land);
	    	}
	    	
	    	return maxValue(-1, land.length, land);
	    }
	    
	    /*
	     * 현재 위치의 인덱스를 제외한 나머지 위치의 인덱스 값 중 최대 값을 찾는다.
	     * 
	     * 마지막 행에서의 최대 값을 찾기 위해서는 
	     * [-1, 격자의 마지막행인덱스+1, 격자]
	     * 로 파라미터를 전달한다.
	     */
	    int maxValue(int avoidIdx, int nowRow, int[][] land) {
	    	int maxValue = Integer.MIN_VALUE;
	    	
	    	for(int idx=0; idx<4; idx++) {
	    		// 밟을 수 없는 땅
	    		if(avoidIdx == idx) continue;
	    		
	    		maxValue = Math.max(land[nowRow-1][idx], maxValue);
	    	}
	    	
	    	return maxValue;
	    }
	}
}

/* 
 * 땅은 총 N 행 4열로 이루어져 있다. 
 * 모든 칸에는 점수가 쓰여져 있다. 
 * 
 *  1 행부터 한 행씩 내려온다. 
 *  이때 4 개의 칸 중 한 칸만 밟아야한다. 
 *  
 *  한 행씩 내려올 때, 같은 열을 연속해서 밟을 수 없다. 
 *  
 *  1. 찾을 수 있는 모든 경우를 찾아본다. 
 *  첫 번째 행을 제외하고 나머지 행에서 선택할 수 있는 경우는 총 3개이다. 
 *  3^100000 -> 노노
 *  
 *  2. DP 로 O(N) 가능한가? 
 */