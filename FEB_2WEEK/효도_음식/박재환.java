package 효도_음식;

import java.util.*;
import java.io.*;

// Softeer Lv.3
// 효도 음식
// https://softeer.ai/practice/7367
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int itemNum;
    static int[] items;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        itemNum = Integer.parseInt(br.readLine().trim());
        items = new int[itemNum];

        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<itemNum; idx++) {
            items[idx] = Integer.parseInt(st.nextToken());
        }
        br.close();

        박재환 problem = new 박재환();
        bw.write(String.valueOf(problem.getMaxPrefer()));
        bw.flush();
        bw.close();
    }
    long[] left;
    long[] right;
    public long getMaxPrefer() {
        left = new long[itemNum];
        right = new long[itemNum];

        long nowMax = items[0];
        left[0] = nowMax;
        for(int idx=1; idx<itemNum; idx++) {
            // 현위치의 값과, 현위치까지의 합을 비교
            nowMax = Math.max(items[idx], nowMax+items[idx]);
            left[idx] = Math.max(left[idx-1], nowMax);  // 이전까지의 합과, 현재 위치까지의 누적 합을 갱신한다.
        }

        nowMax = items[itemNum-1];
        right[itemNum-1] = nowMax;
        for(int idx=itemNum-2; idx > -1; idx--) {
            nowMax = Math.max(items[idx], nowMax + items[idx]);
            right[idx] = Math.max(right[idx+1], nowMax);
        }

        long answer = Long.MIN_VALUE;
        // left 는 우측이 가장 큰 값
        // right 는 좌측이 가장 큰 값
        // 따라서 left 를 기준으로 한칸 건너 뛴 값이 right 의 최대값이다.
        for(int idx=0; idx<itemNum-2; idx++) {
            answer = Math.max(answer, left[idx] + right[idx+2]);
        }

        return answer;
    }
}
/*
N 개의 재료가 있다.

1. 요리는 연속한 재료들로만 만들 수 있다. 최소 1개 이상의 재료를 선택해야만한다.
2. 서로 다른 요리에 사용되는 재료끼리 겹쳐서도 인접해서도 안된다.

각 재료마다 부모님의 선호도가 있다. 만족도는 선호도의 총 합이다.
2개의 요리를 만들 것이다. -> 2개의 요리로 얻을 수 있는 최대 만족도를 구하여라

제약조건
3 ≤ n ≤ 100,000
-1,000 ≤ 선호도 ≤ 1,000

1. 구간합을 이용한다.
-> 두 개의 최대 합 구하기?
-> 순방향과, 역방향의 구간합 구하기?

ex)
6
4 -6 1 2 -2 3

 */