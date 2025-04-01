package day_0401;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int num = Integer.parseInt(st.nextToken());
		
		dp = new int[num + 1];
		Arrays.fill(dp, 10000000);
		dp[1] = 0;
		for (int i = 1; i <= num; i++) {
			if (i + 1 <= num)
				dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);
			if (i * 2 <= num)
				dp[i * 2] = Math.min(dp[i * 2], dp[i] + 1);
			if (i * 3 <= num)
				dp[i * 3] = Math.min(dp[i * 3], dp[i] + 1);
		}
		System.out.println(dp[num]);
	}
}
