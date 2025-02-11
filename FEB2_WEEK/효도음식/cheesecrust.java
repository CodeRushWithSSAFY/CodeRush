import java.io.*;
import java.util.*;

/*
왼쪽과 오른쪽 수열의 합이 가장 큰 부분 수열 구하기
*/
public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int[] numArr;
    private static int answer = Integer.MIN_VALUE;
    private static int leftArr[];
    private static int leftMax[];
    private static int rightArr[];
    private static int rightMax[];
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        numArr = new int[N];
        leftArr = new int[N];
        leftMax = new int[N];
        rightArr = new int[N];
        rightMax = new int[N];
        
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            numArr[i] = Integer.parseInt(st.nextToken());            
        }

        leftArr[0] = numArr[0];
        leftMax[0] = numArr[0];
        for (int i = 1; i < N; i++) {
            leftArr[i] = Math.max(numArr[i], numArr[i] + leftArr[i - 1]);
            leftMax[i] = Math.max(leftMax[i - 1], leftArr[i]);
        }

        rightArr[N - 1] = numArr[N - 1];
        rightMax[N - 1] = numArr[N - 1];
        for (int i = N - 2; i > 1; i--) {
            rightArr[i] = Math.max(numArr[i], numArr[i] + rightArr[i + 1]);
            rightMax[i] = Math.max(rightMax[i + 1], rightArr[i]);
        }

        for (int i = 1; i < N - 1; i++) {
            answer = Math.max(answer, leftMax[i - 1] + rightMax[i + 1]);
        }

        System.out.println(answer);
    }
}
