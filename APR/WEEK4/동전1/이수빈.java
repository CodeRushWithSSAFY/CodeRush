import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
  		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  		StringTokenizer st = new StringTokenizer(br.readLine().trim());
  		
  		int coinCnt = Integer.parseInt(st.nextToken());
  		int totalValue = Integer.parseInt(st.nextToken());
  		int[] coin = new int[coinCnt];
  		int[] dp = new int[totalValue + 1];
  		
  		for (int cnt = 0; cnt < coinCnt; cnt++) {
  			coin[cnt] = Integer.parseInt(br.readLine().trim());
  		}
  		
      dp[0] = 1;
  		for (int coinIdx = 0; coinIdx < coinCnt; coinIdx++) {
  			int curCoinValue = coin[coinIdx];
  			
  			for (int value = curCoinValue; value <= totalValue; value++) {
  				dp[value] += dp[value - curCoinValue];
  			}
  		}
  		
  		System.out.println(dp[totalValue]);
    }
}
