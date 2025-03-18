import java.util.*;

class Solution {

    private static int INF = 1000000000;
    
    public int solution(int[][] info, int n, int m) {
        int answer = INF;
        int[][] dp = new int[info.length + 1][m];
        for (int idx = 0; idx <= info.length; idx++) {
            Arrays.fill(dp[idx], INF);
        }
        dp[0][0] = 0;
        for (int i = 0; i < info.length; i++) {
            int a = info[i][0];
            int b = info[i][1];
            for (int j = 0; j < m; j++) {
                // a 고름
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + a);
                
                // b 고름
                if (j + b < m) {
                    dp[i + 1][j + b] = Math.min(dp[i + 1][j + b], dp[i][j]);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            answer = Math.min(dp[info.length][i], answer);
        }
        if (answer >= n) answer = -1;
        return answer;
    }
}
