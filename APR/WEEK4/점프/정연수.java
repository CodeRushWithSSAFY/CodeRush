import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] board = new int[n][n];

        // 입력받기
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp 배열 초기화 - 각 위치에 도달하는 경로의 수를 저장
        long[][] dp = new long[n][n];
        dp[0][0] = 1; // 시작점

        // 모든 칸을 순회하며 dp 값 계산
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) continue; // 도달할 수 없는 칸이거나 이동할 수 없는 칸

                int jump = board[i][j];

                // 오른쪽으로 점프
                if (j + jump < n) {
                    dp[i][j + jump] += dp[i][j];
                }

                // 아래쪽으로 점프
                if (i + jump < n) {
                    dp[i + jump][j] += dp[i][j];
                }
            }
        }

        System.out.println(dp[n-1][n-1]);
        br.close();
    }
}
