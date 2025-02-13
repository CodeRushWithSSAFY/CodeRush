import java.io.*;
import java.util.*;



public class Main {

    static int[] ingreds;   // 재료 배열
    static int ingredNum;   // 재료 개수 

    static int max;         // 최대 만족도  

    static int[] leftDP;    // 왼쪽 서브 배열 최대값을 저장할 배열
    static int[] rightDP;   // 왼쪽 서브 배열 최대값을 저장할 배열

    
    public static void main(String[] args) throws IOException {

        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ingredNum = Integer.parseInt(br.readLine());

        // DP배열 초기화
        leftDP = new int[ingredNum];
        rightDP = new int[ingredNum];

        // 재료 입력
        ingreds = new int[ingredNum];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int idx = 0 ; idx < ingredNum ; idx++){
            ingreds[idx] = Integer.parseInt(st.nextToken());
        }

        // 배열의 왼쪽 부분 배열에서 최대 합 구하기
        leftDP[0] = ingreds[0];

        // - DP
        for(int idx = 1 ; idx < ingredNum-2; idx++){
            leftDP[idx] = Math.max(leftDP[idx-1] + ingreds[idx], ingreds[idx]);
        }

        // - 실제 최대값으로 업데이트
        for(int idx = 1 ; idx < ingredNum-2; idx++){
            leftDP[idx] = Math.max(leftDP[idx-1], leftDP[idx]);
        }

        // 배열의 오른쪽 부분 배열에서 최대 합 구하기
        rightDP[ingredNum-1] = ingreds[ingredNum-1];

        // - DP
        for(int idx = ingredNum-2 ; idx > 1; idx--){
            rightDP[idx] = Math.max(rightDP[idx+1] + ingreds[idx], ingreds[idx]);
        }

        // - 실제 최대값으로 업데이트
        for(int idx = ingredNum-2 ; idx > 1; idx--){
            rightDP[idx] = Math.max(rightDP[idx+1], rightDP[idx]);
        }


        // 왼쪽/오른쪽 구분하면서 각각 최대 합 구하기
        max = Integer.MIN_VALUE;
        for(int pivot = 1 ; pivot < ingredNum-1; pivot++){
            max = Math.max(max, leftDP[pivot-1] + rightDP[pivot+1]);
        }

        System.out.println(max);
        
    }
}
