package WEEK3.완전범죄;

import java.util.Arrays;

public class 박재환 {
    public static void main(String[] args) {
//        int[][] info = {{1, 2}, {2, 3},{2, 1}};
        int[][] info = {{3,3}, {3, 3}};
        int n = 6;
        int m = 1;

        System.out.println(new 박재환().solution(info,n,m));
    }
    final static int INF = 987654321;
    int minValue;
    public int solution(int[][] info, int n, int m) {
//        minValue = Integer.MAX_VALUE;
//        allComb(0, info.length, 0,0, info, n, m);
//        return minValue == Integer.MAX_VALUE ? -1 : minValue;

        /*
            📌 완전 탐색으로 풀이가 불가능한 경우 2가지 풀이를 생각할 수 있다.
                1. 그리디
                2. 다이다믹 프로그래밍 DP
         */
        // dp[i][j] : i 개의 물건을 고려했을 때, b의 흔적 개수가 y 일 때, 값이 a 의 최소 흔적 개수이다.
        int[][] dp = new int[info.length+1][m]; // 최대 흔적을 기록할 배열
        // 최대값으로 초기화
        for(int item=0; item <= info.length; item++) {
            Arrays.fill(dp[item], INF);
        }
        dp[0][0] = 0;   // 아무것도 훔치지 않은 상태

       /*
            [i][j]
            i : 훔친 물건의 개수
            j : b의 흔적 -> 값 : 이때 a 의 흔적
         */
        for(int item=1; item<=info.length; item++) {
            int a = info[item-1][0];    // a 가 훔침
            int b = info[item-1][1];    // b 가 훔침

            /*
                a 가 물건을 훔치는 경우, b 의 흔적은 증가하지 않고, a의 흔적 ([i][j] 의 값 ) 에 a 를 더한다.

                b 가 물건을 훔치는 경우 i-1 개의 물건을 훔칠때 a 의 최소값을 그대로 가져온다.
             */
            for(int j=0; j<m; j++) {
                // A 가 훔친다.
                dp[item][j] = Math.min(dp[item][j], dp[item-1][j]+a);

                // B 가 훔친다.
                if(j+b < m) {   // 더 훔칠 수 있다.
                    dp[item][j+b] = Math.min(dp[item][j+b], dp[item-1][j]);
                }
            }
        }

        // 최소 흔적 찾기
        minValue = INF;
        for(int j=0; j<m; j++) {
            minValue = Math.min(minValue, dp[info.length][j]);
        }

        return minValue >= n ? -1 : minValue;
    }

    /*
        ❌ 완전 탐색 시간 초과!
        구할 수 있는 조합을 구한다.
        A 개 가져갈 수 있는 물건 , B 가 가져갈 수 있는 물건
     */
    void allComb(int itemIdx, int targetLen, int a, int b, int[][] info, int n, int m) {
        if(itemIdx == targetLen) {  // 탐색을 모두 끝낸 경우
            // a 의 흔적 개수를 갱신한다.
            if(a < n && b < m) {
                minValue = Math.min(minValue, a);
            }
            return;
        }

        // 가지치기
        if(a >= n || b >= m) return;  // 경찰에 걸리는 경우
        if(minValue < a) return;    // 최소값을 넘어버리는 경우

        // 추가로 탐색할 수 있다.

        // 1. a 가 훔진다.
        if(a+info[itemIdx][0] < n) {
            allComb(itemIdx + 1, targetLen, a + info[itemIdx][0], b, info, n, m);
        }
        // 2. b 가 훔친다.
        if(b+info[itemIdx][1] < m) {
            allComb(itemIdx + 1, targetLen, a, b + info[itemIdx][1], info, n, m);
        }
    }
}
/*
    A, B 가 팀을 이루어 물건을 훔치려한다. (흔적을 최소화 해서)

    물건 i 를 훔칠 때
        A 가 훔치면 info[i][0]  A 에 대한 흔적을 남긴다.
        B 가 훔치면 info[i][1] B 에 대한 흔적을 남긴다.
    각 물건에 대해 A, B 가 남기는 흔적의 개수는 1 이상 3 이하이다.

    A 의 흔적의 개수가 n 개 이상이면 안된다.
    B 의 흔적의 개수가 m 개 이상이면 안된다.

    입력에서 info[i] 는 i를 훔칠 때 생기는 흔적의 개수이다.
    [A에 대한 흔적 개수, B에 대한 흔적 개수]

    A 도둑이 남긴 흔적의 개수 최소값을 구해라

    주어진 물건은 전부 훔쳐야하는 조건인가?
 */
