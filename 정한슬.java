import java.util.*;
import java.io.*;


/*
 * 정수 X에 사용할 수 있는 연산은 3가지
 * 1. X가 3으로 나누어 떨어지면 3으로 나눈다.
 * 2. X가 2로 나누어 떨어지면 2로 나눈다.
 * 3. 1을 뺀다.
 * 
 * 정수 N이 주어졌을 때 위와 같은 연산 세개를 적절히 사용해서 1을 만들때 최소 연산 횟수를 출력.
 * 
 * 
 * */
public class 정한슬 {
	static final int INF = Integer.MAX_VALUE;
	static final int TARGET_NUMBER = 1;
	
	static BufferedReader br;
	
	static int number;
	static int[] minCal;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		number = Integer.parseInt(br.readLine().trim());
		minCal = new int[number + 1];
		
		Arrays.fill(minCal, INF); // 모든 숫자에 연산이 무한대라고 초기화 설정
		minCal[number] = 0; // number부터 연산 시작.
		
		while (number > 0) {
			if (number % 3 == 0) {
				minCal[number / 3] = Math.min(minCal[number / 3], minCal[number] + 1);
			}
            if (number % 2 == 0) {
				minCal[number / 2] = Math.min(minCal[number / 2], minCal[number] + 1);
			}
			minCal[number - 1] = Math.min(minCal[number - 1], minCal[number] + 1);
			number--;
		}
		
		br.close();
		System.out.println(minCal[TARGET_NUMBER]);
	}
}
