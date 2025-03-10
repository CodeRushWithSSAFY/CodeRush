import java.util.*;

/*
    <시초 / 틀린 풀이> => 한 행에서 최적의 선택을 했다한들 그게 최대값이 안된다.
    1. for문으로 첫번째 행부터 마지막 행까지 돈다.
        첫번째 행에서 모든 col의 요소를 담는다.
    2. queue 에 col과 sum을 담는다.
    3. 다음 row를 돌 때는 뽑아놨던 이전 col과 같은 col은 선택 할 수 없다.
    4. sum이 가장 큰 것을 갱신한다.

    <옳은 풀이> => dp로 풀어야한다.
    100,000 * 4 * 4 는 괜찮...
*/
class 정한슬 {
  static int[][] dp;
  static int maxSum;

  int solution(int[][] land) {
    dp = new int[land.length][4];

    for (int col = 0; col < 4; col++) {
      dp[0][col] = land[0][col]; // 첫번째 행의 초기 값 설정
    }

    for (int row = 1; row < land.length; row++) {
      for (int col = 0; col < 4; col++) {
        for (int beforeCol = 0; beforeCol < 4; beforeCol++) {
          if (col == beforeCol) continue;
          dp[row][col] = Math.max(dp[row - 1][beforeCol] + land[row][col], dp[row][col]);
        }
      }
    }

    maxSum = 0;
    for (int col = 0; col < 4; col++) {
      maxSum = Math.max(maxSum, dp[land.length - 1][col]);
    }

    return maxSum;
  }
}