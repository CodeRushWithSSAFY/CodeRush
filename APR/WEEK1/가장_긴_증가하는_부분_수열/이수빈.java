import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(br.readLine().trim());
        int[] arr = new int[size];
        int[] dp = new int[size];
        int result = 1;

        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < size; idx++) arr[idx] = Integer.parseInt(st.nextToken());

        for (int idx = 0; idx < size; idx++) {
            dp[idx] = 1;
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                if (arr[idx] > arr[prevIdx]) {
                    dp[idx] = Math.max(dp[prevIdx] + 1, dp[idx]);
                }
            }
            result = Math.max(dp[idx], result);
        }

        System.out.println(result);
    }
}
