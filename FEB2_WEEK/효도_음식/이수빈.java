import java.io.*;
import java.util.*;

/*
    효도 음식

    두 요리를 통해 얻을 수 있는 최대 만족도 구하기
    두 요리는 인접해있을 수 없다.
*/
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine().trim());
        int[] food = new int[n];

        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < n; index++) {
            food[index] = Integer.parseInt(st.nextToken());
        }

        int[] leftDp = new int[n];
        int[] rightDp = new int[n];
        leftDp[0] = food[0];
        rightDp[n - 1] = food[n - 1];

        // 1. '이전까지의 최댓값 + 현재값' 과 '현재값' 비교
        // 2. 1에서 비교한 '현재까지의 최댓값'과 '이전까지의 최댓값' 비교
        // 현재까지의 최댓값 (nowMax) 와 leftDp는 별개이다!
        int nowMax = food[0];
        for (int i = 1; i < n; i++) {
            nowMax = Math.max(food[i], nowMax + food[i]); // 현재 음식과, 현재 음식을 포함한 값을 비교
            leftDp[i] = Math.max(nowMax, leftDp[i - 1]); // 현재까지의 최대값, 이전까지의 최대값을 비교
        }

        nowMax = food[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            nowMax = Math.max(food[i], nowMax + food[i]); // 현재 음식과, 현재 음식을 포함한 값을 비교
            // 이전의 최대값과 현재값의 합보다, 현재 음식의 값이 더 큰 경우
            rightDp[i] = Math.max(nowMax, rightDp[i + 1]); // 현재까지의 최대값, 이전까지의 최대값을 비교
        }

        int result = Integer.MIN_VALUE;
        for (int i = 1; i < n - 1; i++) {
            result = Math.max(result, leftDp[i - 1] + rightDp[i + 1]);
        }

        System.out.println(result);


    }
}
