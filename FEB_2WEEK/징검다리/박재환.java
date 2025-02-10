package FEB_2WEEK.징검다리;

import java.io.*;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int itemNum;
    static int[] items;
    public static void main(String[] args) throws IOException {
        박재환 problem = new 박재환();

        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 재료의 개수를 입력 받는다.
        itemNum = Integer.parseInt(br.readLine().trim());
        // 재료 개수 배열 초기화
        items = new int[itemNum];

        // 재료를 입력 받는다.
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx < itemNum; idx++) {
            items[idx] = Integer.parseInt(st.nextToken());
        }
        br.close();

        bw.write(String.valueOf(problem.findMaxValue()));
        bw.flush();
        bw.close();
    }

    long answer;
    public long findMaxValue() {
        long[] leftMax = new long[itemNum];
        long[] rightMax = new long[itemNum];

        // 1. 0 번째 부터 i 번째 까지 최대합을 구한다.
        long nowMax = items[0];
        leftMax[0] = nowMax;
        for(int idx=1; idx<itemNum; idx++) {
            // 현재까지의 합 + 현재 위치의 값 과 현재 위치의 값 중 최대 값을 찾는다
            // 현재 위치의 값이 더 큰 경우, 이전까지의 누적합을 버리고, 현재 위치의 값으로 최대 값을 갱신한다.
            nowMax = Math.max(items[idx], items[idx] + nowMax);
            //  위와 동일하다. 현재 위치까지의 최대 누적합을 갱신한다.
            leftMax[idx] = Math.max(leftMax[idx-1], nowMax);
        }

        // 2. i 번째 부터 N 까지의 최대합을 구한다.
        nowMax = items[itemNum-1];  // 가장 마지막 값 부터 구한다.
        rightMax[itemNum-1] = nowMax;
        for(int idx=itemNum-2; idx > -1; idx--) {
            // 현재까지의 합 + 현재 위치의 값 과 현재 위치의 값 중 최대 값을 찾는다
            // 현재 위치의 값이 더 큰 경우, 이전까지의 누적합을 버리고, 현재 위치의 값으로 최대 값을 갱신한다.
            nowMax = Math.max(items[idx], nowMax + items[idx]);
            rightMax[idx] = Math.max(rightMax[idx+1], nowMax);
        }

        answer = Long.MIN_VALUE;
        for(int idx=0; idx<itemNum-2; idx++) {
            answer = Math.max(leftMax[idx]+rightMax[idx+2], answer);
        }

        return answer;
    }

}

/*
재료는 1~n 번까지 있음

1. 요리는 연속한 재료들로만 만들 수 있다
    이때 재료는 최소 1개 이상
2. 서로 다른 요리에 사용되는 재료끼리 겹쳐서도, 인접해서도 안된다.

⚠️ 요리는 두 개만 만든다.
각 재료마다 부모님의 선호도가 정해져있다, 만족도는 두 요리에 쓰인 재료의 선호도의 총 합으로 정해진다.

ex)
6 ( 재료의 개수 )
4 -6 1 2 -2 3 ( 재료들 )

각 위치마다 현재 위치까지의 최대 값을 구한다?

첫번째 요리부터 i 번쨰 요리까지 부분배열의 최대 합을 구한다
i 번째부터 n 까지의 최대 합을 구한다.
*/