/*
    1. row -> row + 1로 내려갈때 갈 수 있는 범위는 col, col + 1이다.
    2. 모두 다 양수니까 최대한 바닥까지 sum
    3. 각 row에서 첫번째 col은 바로 전 row에서 첫번째 col의 값이 그대로 올 수 있다.
    4. 각 row에서 마지막 row는 바로 전 row에서 마지막 col 값이 그대로 올 수 있다.
    5. 나머지 중간에서는 바로 전 row의 col -1, col 값 중 큰 값을 선택한다.
*/
class 정한슬 {
  static int maxSum;
  static int beforeMaxCol;
  static int[][] dp;

  public int solution(int[][] triangle) {
    dp = new int[triangle.length][];
    for (int row = 0; row < triangle.length; row++) {
      dp[row] = new int[triangle[row].length];
    }
    dp[0][0] = triangle[0][0]; // 초기값 설정

    for (int row = 1; row < triangle.length; row++) {
      for (int col = 0; col < triangle[row].length; col++) {
        if (col == 0) {
          dp[row][col] = dp[row - 1][col] + triangle[row][col];
        } else if (col == triangle[row].length - 1) {
          beforeMaxCol = triangle[row - 1].length - 1;
          dp[row][col] = dp[row - 1][beforeMaxCol] + triangle[row][col];
        } else {
          dp[row][col] = Math.max(dp[row][col], dp[row - 1][col - 1] + triangle[row][col]);
          dp[row][col] = Math.max(dp[row][col], dp[row - 1][col] + triangle[row][col]);
        }
      }
    }

    maxSum = 0;
    for (int col = 0; col < dp[dp.length - 1].length; col++) {
      maxSum = Math.max(maxSum,dp[dp.length - 1][col]);
    }
    return maxSum;
  }
}