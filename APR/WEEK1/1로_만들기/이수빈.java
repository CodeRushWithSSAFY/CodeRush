import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * 	BOJ_1463_1로만들기
 * 
 * 	1. 횟수를 저장하는 count 배열 생성 후 integer 최댓값으로 초기화
 * 	2. number부터 1씩 감소시키며 1이 될 때까지 반복문
 * 		2-1. count[number]는 0으로 초기화
 * 		2-2. 3으로 나누어 떨어질 때, 2로 나누어 떨어질 때, 1 감소할 때 세 경우에 대해 min 값 갱신
 * 		2-3. number 1 감소
 * 	3. count[1] 출력
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int number = Integer.parseInt(br.readLine().trim());
		int[] count = new int[number + 1];
		
		Arrays.fill(count, Integer.MAX_VALUE);
		count[number] = 0;
		
		while (number >= 1) {
			if (number % 3 == 0) 
				count[number / 3] = Math.min(count[number / 3], count[number] + 1);
			
			if (number % 2 == 0)
				count[number / 2] = Math.min(count[number / 2], count[number] + 1);
			
			count[number - 1] = Math.min(count[number - 1], count[number] + 1);
			number--;
		}
		System.out.println(count[1]);
	}
}
