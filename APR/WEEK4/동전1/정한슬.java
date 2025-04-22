import java.util.*;
import java.io.*;

public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int coinCount;
	static int targetWon;
	static int[] coins;
	static int count;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		knapsack();
		bw.write(String.valueOf(count));
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void knapsack() {
		int[] dp = new int[targetWon + 1];
		
		 dp[0] = 1; // 금액 0원 만드는 방법 1가지 (동전 X)

	    for (int coinIdx = 0; coinIdx < coinCount; coinIdx++) {
	        int coin = coins[coinIdx];
	        for (int value = coin; value <= targetWon; value++) {
	            dp[value] += dp[value - coin];
	        }
	    }

	    count = dp[targetWon];
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine());
		
		coinCount = Integer.parseInt(st.nextToken());
		targetWon = Integer.parseInt(st.nextToken());
		
		coins = new int[coinCount];
		for (int coinIdx = 0; coinIdx < coinCount; coinIdx++) {
			coins[coinIdx] = Integer.parseInt(br.readLine().trim());
		}
	}
}
