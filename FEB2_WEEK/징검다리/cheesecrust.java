import java.io.*;
import java.util.*;
/*
    스타트 캠프때 푼 2번 문제와 같습니다. 
    
    완전 탐색의 경우 깊이가 너무 깊어지고, 같은 동작을 중복되어 수행할 수 있습니다.
    -> 같은 동작을 줄이기 위해 이미 했던 결과를 저장하는 dp활용

    점화식: dp[i] = 자기보다 i가 작은 값들 중에서 가장 큰값 + 1

    ex)
    3 2 1 4 5

    1 0 0 0 0
    1 1 0 0 0
    1 1 1 0 0
    1 1 1 2 0
    1 1 1 2 3
*/
public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); 
        st = new StringTokenizer(br.readLine());
        int[] stoneArr = new int[N + 1];
        int[] answerArr = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            stoneArr[i] = Integer.parseInt(st.nextToken());
        }
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < i; j++) {
                if (stoneArr[i] > stoneArr[j]) {
                    answerArr[i] = Math.max(answerArr[i], answerArr[j] + 1);
                }                                
            }
            answer = Math.max(answer, answerArr[i]);
        }
        System.out.println(answer);
    }
}

