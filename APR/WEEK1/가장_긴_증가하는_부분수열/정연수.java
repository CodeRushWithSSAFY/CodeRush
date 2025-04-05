package day_0401;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dp;
	static int[] nums;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int num = Integer.parseInt(st.nextToken());
		
		dp = new int[num];
		nums = new int[num];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < num; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		
		dp[0] = 1;
		for (int i = 1; i < num; i++) {
			int maxCnt = 0;
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j])
					maxCnt = Math.max(maxCnt, dp[j]);
			}
			dp[i] = maxCnt + 1;
		}
		
		int answer = 0;
		for (int i = 0; i < num; i++) {
			answer = Math.max(answer, dp[i]);
		}
		System.out.println(answer);
	}
}
