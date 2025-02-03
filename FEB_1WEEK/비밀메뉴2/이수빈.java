import java.io.*;
import java.util.*;

/*
    비밀 메뉴2

    LCS
*/
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] arr1 = new int[N];
        int[] arr2 = new int[M];
        int[][] dp = new int[N + 1][M + 1];

        int result = 0;

        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < N; index++) {
            arr1[index] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < M; index++) {
            arr2[index] = Integer.parseInt(st.nextToken());
        }

        for (int arr1Index = 0; arr1Index <= N; arr1Index++) {
            for (int arr2Index = 0; arr2Index <= M; arr2Index++) {
                if (arr1Index == 0 || arr2Index == 0) dp[arr1Index][arr2Index] = 0; // 마진
                else {
                    if (arr1[arr1Index - 1] == arr2[arr2Index - 1])
                        dp[arr1Index][arr2Index] = dp[arr1Index - 1][arr2Index - 1] + 1;
                    else
                        dp[arr1Index][arr2Index] = 0;
                }
                result = Math.max(result, dp[arr1Index][arr2Index]);
            }
        }
        System.out.println(result);
    }
}
