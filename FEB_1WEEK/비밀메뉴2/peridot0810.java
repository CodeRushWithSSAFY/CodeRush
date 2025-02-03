package solution;

import java.util.*;


/**
 * 
 * CodeRush 1주차 1번 문제 - 비밀 메뉴2
 * 
 * ## 최장 공통 부분 연속 수열 찾기?
 * 
 * 
 * #0. 입력 받기 + 변수 선언
 * 	0-1. 긴 수열 : longSeq , 짧은 수열 : shortSeq
 * 		0-1-1. longSeq의 경우, (shortSeq의 길이-1) 만큼 양쪽으로 -1을 추가로 입력받기 (longSeqInput 메서드)
 * 		0-1-2. shortSeq의 경우, 일반적인 방식으로 입력받기 (shortSeqInput 메서드)
 * 
 * 	0-2. int cnt : 공통 부분 카운트 할 변수
 * 	0-3. int max : 최장 길이 저장할 변수 -> Integer.MIN_VALUE으로 초기화 
 * 
 * 
 * 
 * #1. 이중 for 문 (solve 메서드)
 * 	1-1. 바깥 for문
 * 		1-1-1. int startIdx = 0 ~ (longSeq.length - shortSeq.length)
 * 		1-1-2. cnt 변수 0으로 초기화
 * 	
 * 	1-2. 안쪽 for문
 * 		1-2-1. int currIdx = 0 ~ (shortSeq.length)
 * 		1-2-2. shortSeq[ currIdx ] 과 longSeq[ startIdx + currIdx ] 를 비교
 * 		1-2-3. 공통 부분 나올때마다 cnt 1 증가
 * 		1-2-4. 공통이 아닌 부분이 나오면 연속이 끊기므로
 * 			1-2-4-1. max는 max와 cnt와 더 큰 값으로 업데이트
 * 			1-2-4-2. cnt를 0으로 초기화
 * 
 * 	1-3. 안쪽 for문 종료 -> max값 업데이트
 * 		1-3-1. max = Math.max(max, cnt)
 * 		
 * 
 * 
 * #2. 정답 출력
 * 	2-1. 정답 : max
 * 
 * 
 * 
 */




public class Main {
	
	public static int seqLength1;	// N
	public static int seqLength2;	// M
	public static int maxNum;		// K
	
	public static int cnt;
	public static int max = Integer.MIN_VALUE;
	public static int[] longSeq;
	public static int[] shortSeq;
	
	
	
	// main
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		seqLength1 = sc.nextInt();		// N 입력
		seqLength2 = sc.nextInt();		// M 입력
		maxNum = sc.nextInt();			// K 입력
		
		
		if(seqLength1 >= seqLength2) {	// 1번 수열이 더 길다 -> longSeq를 먼저 입력 받기
			longSeqInput(seqLength1, seqLength2, sc);
			shortSeqInput(seqLength1, seqLength2, sc);
		}else {							// 2번 수열이 더 길다 -> shortSeq를 먼저 입력 받기
			shortSeqInput(seqLength2, seqLength1, sc);
			longSeqInput(seqLength2, seqLength1, sc);
		}
		
		
		// 정답 계산 및 출력
		System.out.println(solve(longSeq, shortSeq));
		
		
		sc.close();
	}
	
	
	
	// 정답 계산 및 반환
	public static int solve(int[] longSeq, int[] shortSeq) {
		
		int longLength = longSeq.length;
		int shortLength = shortSeq.length;
				
				
		for(int startIdx = 0; startIdx <= (longLength - shortLength); startIdx++) {
			cnt = 0;
			
			for(int currIdx = 0; currIdx < shortLength; currIdx++) {
				if(shortSeq[currIdx] == longSeq[startIdx + currIdx]) {
					cnt++;
				}else {
					max = Math.max(max, cnt);
					cnt = 0;
				}
			}
			
			max = Math.max(max, cnt);
			
		}
		
		return max;
	}


	
	// longSeq 입력 받는 메서드
	public static void longSeqInput(int longLength, int shortLength, Scanner sc) {
		// 양쪽으로 (shortLength - 1) 만큼 -1을 입력받을 것이므로 아래의 값으로 길이 설정 
		int seqLength = longLength + 2 * (shortLength - 1);
		longSeq = new int[seqLength];
		
		// 수열의 앞부분 입력(-1)
		for(int frontIdx = 0; frontIdx < shortLength - 1; frontIdx++) {
			longSeq[frontIdx] = -1;
		}
			
		// 실제 수열 값 입력
		for(int midIdx = shortLength-1; midIdx < longLength + shortLength - 1; midIdx++) {
			longSeq[midIdx] = sc.nextInt();
		}
			
		// 수열의 뒷부분 입력(-1)
		for(int behindIdx = longLength + shortLength - 1; behindIdx < seqLength; behindIdx++) {
			longSeq[behindIdx] = -1;
		}
		
	}
	
	
	
	// shortSeq 입력 받는 메서드
	public static void shortSeqInput(int longLength, int shortLength, Scanner sc) {
		shortSeq = new int [shortLength];
		
		for(int idx = 0; idx < shortLength; idx++) {
			shortSeq[idx] = sc.nextInt();
		}
		
	}


}
