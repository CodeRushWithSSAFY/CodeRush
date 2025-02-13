import java.io.*;
import java.util.*;

/*
    징검다리

    최장 증가 부분 수열, dp
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int stoneCnt = Integer.parseInt(br.readLine().trim());
        int[] stoneHeight = new int[stoneCnt];
        int[] passCnt = new int[stoneCnt]; // dp

        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < stoneCnt; index++) {
            stoneHeight[index] = Integer.parseInt(st.nextToken());
            passCnt[index] = 1;
        }

        int result = 0;
        // 뒤에서부터 탐색
        for (int index = stoneCnt - 1; index >= 0; index--) {
            int curStoneHeight = stoneHeight[index];
            int maxCnt = 0;
            // curStone의 뒤 stone들에서 밟을 수 있는 돌의 최대 개수 구하기
            for (int next = index + 1; next < stoneCnt; next++) {
                if (curStoneHeight < stoneHeight[next]) {
                    maxCnt = Math.max(maxCnt, passCnt[next]);
                }
            }
            passCnt[index] += maxCnt;
            result = Math.max(passCnt[index], result);
        }
        System.out.println(result);
    }
}
