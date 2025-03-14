package MAR.WEEK3.비밀_코드_해독;

public class 박재환 {

	public static void main(String[] args) {
		int n = 10;
//		int n = 15;
		int[][] q = {
				{1, 2, 3, 4, 5},
				{6, 7, 8, 9, 10}, 
				{3, 7, 8, 9, 10}, 
				{2, 5, 7, 9, 10}, 
				{3, 4, 5, 6, 7}};
//		int[][] q = {
//				{2, 3, 9, 12, 13}, 
//				{1, 4, 6, 7, 9}, 
//				{1, 2, 8, 10, 12}, 
//				{6, 7, 11, 13, 15}, 
//				{1, 4, 10, 11, 14}};
		
		int[] ans = {2, 3, 4, 3, 3};
//		int[] ans = {2, 1, 3, 0, 1};
		System.out.println(new 박재환().solution(n,q,ans));
	}
	
	// 외부 함수에서 접근을 위해 전역 변수로 재선언
	int numRage;	
	int[][] tryCnt;
	int[] matchCnt;
	int predictAns;
	public int solution(int n, int[][] q, int[] ans) {	// 1~n 정수의 범위, q번 시도, ans 비밀 코드에 들어간 수의 개수
		numRage=n;
		tryCnt=q;
		matchCnt=ans;
		predictAns = 0;
		
		getAllCodeComb(1, 0, new int[5]);
		
		return predictAns;
	}

	/*
	 * 생성된 비밀번호 조합이 
	 * 무작위 대입 결과와 일치하는지 확인
	 */
	void compareMatchLog(int[] selected) {

		// 모든 테스트를 확인한다. 
		for(int testIdx=0; testIdx<matchCnt.length; testIdx++) {
			int match = 0;	// 일치하는 수의 개수 
			
			for(int selectNum : selected) {		// 생성된 비밀번호
				for(int testNum : tryCnt[testIdx]) {	// 테스트 비밀번호 
					if(selectNum == testNum) {	// 수가 일치한다면 
						match++;
						break;
					}
				}
			}
			
			if(match != matchCnt[testIdx]) return; 
		}
		
		predictAns++;
	}
	
	// 1~n 의 정수로 생성가능한 모든 조합을 구한다. 
	// 오름차순 정렬되어 있으므로 백트래킹은 하지 않는다.
	void getAllCodeComb(int numIdx, int selectIdx, int[] selected) {
		if(selectIdx == 5) {	// 비밀번호 조합을 완성한 경우 
			// 해당 조합이 시도한 경우의 모든 경우를 만족하는지 탐색한다. 
			compareMatchLog(selected);
			return;
		}
		
		if(numIdx > numRage) {	// 탐색하고자 하는 범위를 벗어나는 경우
			return;
		}
		
		// 해당 수를 포함하는 경우
		selected[selectIdx] = numIdx;
		getAllCodeComb(numIdx+1, selectIdx+1, selected);
		
		// 해당 수를 포함하지 않는 경우
		selected[selectIdx] = 0;
		getAllCodeComb(numIdx+1, selectIdx, selected);
	}
}

/* 
 *  시스템은 1부터 n 까지의 서로 다른 정수 5개가 오름차순으로 정렬된 비밀 코드를 가지고 있다.
 *  
 *  비밀 코드를 알아내기 위한 m 번의 시도를 할 수 있다. 
 *  서로 다른 5개의 정수를 입력하면 그 중 몇 개가 비밀 코드에 포함되어 있는지 알려준다. 
 *  
 *  시도는 최대 10 번까지 가능하다. 
 *  
 *  최악의 경우 각 시도마다 1개의 숫자씩만 정답일때 
 *  5^10 의 경우가 나온다.
 *  
 *  탐색 수 줄이기
 *  
 *  반대로 생각.
 *  1 ~ n 사이의 정수로 만들 수 있는 5자리 비밀 코드를 모두 생성한다. 
 *  	생성된 비밀 코드가 테스트케이스에 모두 부합하는지 확인한다. 
 *  
 */