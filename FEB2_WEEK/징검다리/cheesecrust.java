import java.io.*;
import java.util.*;

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

