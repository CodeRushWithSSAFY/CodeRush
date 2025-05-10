import java.io.*;
import java.util.*;

public class Main {
    static int[] arr = new int[1002];
    static int[] dp = new int[1002];
    static int[] dp2 = new int[1002];
    
    static void left(int N) {
        for (int i = 1; i <= N; i++) {
            dp[i] = 1;
            for (int j = 1; j < i; j++) {
                if (dp[j] + 1 > dp[i] && arr[j] < arr[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }
    }
    
    static void right(int N) {
        for (int i = N; i > 0; i--) {
            dp2[i] = 1;
            for (int j = N; j > i; j--) {
                if (dp2[j] + 1 > dp2[i] && arr[j] < arr[i]) {
                    dp2[i] = dp2[j] + 1;
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int cnt = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for (int i = 1; i <= cnt; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        left(cnt);
        right(cnt);
        
        int ans = 0;
        for (int i = 0; i <= cnt; i++) {
            if (ans < dp[i] + dp2[i] - 1) {
                ans = dp[i] + dp2[i] - 1;
            }
        }
        
        System.out.println(ans);
    }
}
