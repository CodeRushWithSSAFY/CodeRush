import java.io.*;
import java.util.*;
import java.lang.*;

/*
 * 최장 공통 부분 수열인데 연속된 수열
 * 각각 가로 세로 +1 의 값으로 2차원 배열을 만든다.
 * 점화식: 최종 결과 = 같을 경우의 대각선 방향의 값 + 1 or 지금까지의 연속된 수열 중 큰 값
 * 다 같을 경우 3000 이므로 int
 */

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int[][] dp;
    private static int[] arr1;
    private static int[] arr2;
    
    public static void main(String[] args) throws Exception {
        int n;
        int m;
        int k;
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dp = new int[m + 1][n + 1];
        arr1 = new int[n + 1];
        arr2 = new int[m + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr1[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= m; i++) {
            arr2[i] = Integer.parseInt(st.nextToken());
        }

        int result = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (arr2[i] == arr1[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        System.out.println(result);
    }
}
