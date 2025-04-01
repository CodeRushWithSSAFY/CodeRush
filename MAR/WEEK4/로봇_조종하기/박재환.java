package MAR.WEEK4.로봇_조종하기;

import java.util.*;
import java.io.*;

public class 박재환 {
  static BufferedReader br;
  static BufferedWriter bw;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    bw = new BufferedWriter(new OutputStreamWriter(System.out));

    init();

    bw.flush();
    bw.close();
  }

  static int row, col;
  static int[][] map;
  static int maxValue;

  static void init() throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine().trim());

    row = Integer.parseInt(st.nextToken());
    col = Integer.parseInt(st.nextToken());

    map = new int[row][col];
    for (int x = 0; x < row; x++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int y = 0; y < col; y++) {
        map[x][y] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();

    getMaxValue();

    bw.write(String.valueOf(maxValue));
  }

  /*
   * 첫 번째 줄은 아래 방향을 제외하고, 오른쪽으로밖에 이동하지 못한다.
   * 
   * 두 번째 줄부터, -> 방향과 <- 방향에서의 최대 값을 각각 구해, 최대 값을 갱신한다.
   */
  static void getMaxValue() {
    int[][] dp = new int[row][col];

    // 첫 번쨰 값 설정
    dp[0][0] = map[0][0];

    // 첫 번쨰 줄 처리
    for (int y = 1; y < col; y++) {
      dp[0][y] = dp[0][y - 1] + map[0][y];
    }

    // -> 방향과 <- 방향의 각 최대 값들을 임시로 저장하기 위한 배열
    // [0] : ->
    // [1] : <-
    int[][] temp = new int[2][col];

    // 두 번쨰 줄 부터 처리한다.
    for (int x = 1; x < row; x++) {
      // 첫 번째 칸은, 위에서 내려오는 경우만 존재한다
      temp[0][0] = dp[x - 1][0] + map[x][0];
      // 오른족, 아래 방향
      for (int y = 1; y < col; y++) {
        temp[0][y] = Math.max(temp[0][y - 1], dp[x - 1][y]) + map[x][y];
      }

      // 첫 번째 칸은, 위에서 내려오는 경우만 존재한다
      temp[1][col - 1] = dp[x - 1][col - 1] + map[x][col - 1];
      // 왼쪽, 아래 방향
      for (int y = col - 2; y > -1; y--) {
        temp[1][y] = Math.max(temp[1][y + 1], dp[x - 1][y]) + map[x][y];
      }

      // 각 위치의 최대 값을 구한다.
      for (int y = 0; y < col; y++) {
        dp[x][y] = Math.max(temp[0][y], temp[1][y]);
      }
    }

    maxValue = dp[row - 1][col - 1];
  }

}

/*
 * N x M 크기의 격자
 * 왼쪽 오른쪽 아래쪽으로는 이동이 가능
 * 위쪽은 불가
 * 
 * 한 번 탐사한 구역은 재탐사 X
 * 
 * 각 지역은 탐사 가치가 있음
 * 최대 가치의 합을 구해라
 * 
 * 격자의 크기는 최대 1000 x 1000
 * 
 * (1,1) 에서 출발 (N,M) 으로 이동
 * DFS 불가 -> 배열이 너무 큼!
 * 
 * DP?
 * 각 위치마다 들어올 수 있는 방향을 3가지
 * 왼쪽, 위, 오른쪽
 */