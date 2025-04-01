package WEEK1.1로_만들기;

import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		init();
		br.close();
	}
	
	static int num;
	static int[] arr; 
	static void init() throws IOException {
		num = Integer.parseInt(br.readLine().trim());
		arr = new int[num+1];
		
		makeArr();
	}
	
	static void makeArr() {
		for(int i=2; i<num+1; i++) {	// 0은 제외, 1은 연산을 할 필요가 없음
			arr[i] = arr[i-1]+1;	// 1을 빼는 연산 
			
			if(i%3 == 0) {	// 3 으로 나누어 떨어지는 경우
				arr[i] = Math.min(arr[i], arr[i/3]+1);
			}
			if(i%2 == 0) {	// 2 로 나누어 떨어지는 경우  
				arr[i] = Math.min(arr[i] , arr[i/2]+1);
			}
		}
		
		System.out.println(arr[num]);
	}

}

/* 
 * 연산의 종류
 * x 가 3 으로 나누어 떨어지면 3으로 나눈다.
 * x 가 2 로 나누어 떨어지면 2로 나눈다. 
 * 1 을 뺀다. 
 * 
 * 위 3가지의 연산을 적절히 사용해, 1을 만든다.
 * 최소 연산 횟수를 출력한다. 
 */