import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int arr1L, arr2L, maxValue;
    static int[] arr1;
    static int[] arr2;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out ));

        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        arr1L = Integer.parseInt(st.nextToken());
        arr1 = new int[arr1L];
        arr2L = Integer.parseInt(st.nextToken());
        arr2 = new int[arr2L];
        maxValue = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<arr1L; idx++){
            arr1[idx] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<arr2L; idx++){
            arr2[idx] = Integer.parseInt(st.nextToken());
        }
        br.close();

        bw.write(String.valueOf(maxLength()));
        bw.flush();
        bw.close();
    }

    private static int maxLength() {
        int maxL = 0;

        int[][] lcsArr = new int[arr1L+1][arr2L+1];

        for(int idx1=1; idx1<=arr1.length; idx1++) {
            for(int idx2=1; idx2<=arr2.length; idx2++){
                if(arr1[idx1-1] == arr2[idx2-1]) {  // 문자가 일치한다
                    lcsArr[idx1][idx2] = lcsArr[idx1-1][idx2-1] + 1;    // 이전까지의 길이 + 1
                    maxL = Math.max(maxL, lcsArr[idx1][idx2]);
                }
            }
        }

        return maxL;
    }
}
/*
입력
N M K (각 원소들의 최대 값은 K)
N 길이의 배열
M 길이의 배열

두 배열간의 최장 공통 부분 수열을 구하면 된다
이는 LSC 알고리즘을 사용한다.

LCS 알고리즘
1. 두 문자열을 비교하여 값을 저장할 배열을 생성한다.
    a. 배열의 크기는 [N+1][M+1]
2. 각각의 원소를 비교하여 같은 경우
    a. 이전까지의 길이 값에 1을 더해준다.
 */