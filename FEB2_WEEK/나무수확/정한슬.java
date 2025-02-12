import java.io.*;
import java.util.*;

/*
    (1, 1) -> (n, n) 까지 갈때 오른쪽 하고 아래로 밖에 못감.
    {1, 0}, {0, 1}
*/
public class 정한슬 {
  static final int[] dr = {1, 0};
  static final int[] dc = {0, 1};
  static int maxFruitSum = Integer.MIN_VALUE;
  static int maxFruitValue = 0;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    int boardLength = Integer.parseInt(br.readLine().trim());
    int[][] board = new int[boardLength + 1][boardLength + 1];

    for (int row = 1; row <= boardLength; row++) {
      StringTokenizer st = new StringTokenizer(br.readLine().trim());
      for (int col = 1; col <= boardLength; col++) {
        board[row][col] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();

    int[][][] dp =  new int[boardLength + 1][boardLength + 1][2]; // X, Y, {max, sum}

    for (int row = 1; row <= boardLength; row++) {
      for (int col = 1; col <= boardLength; col++) {
        if (row == boardLength && col == boardLength) {
          // 맨 마지막에는 오른쪽과 위에서 온 로직 중 maxFruitValue + maxFruitSum이 가장 높은 걸 선택해야 한다. 아니면 경로가 꼬인채로 최종값이 나오게된다.
          maxFruitValue = Math.max(dp[row - 1][col][0], dp[row][col - 1][0]);
          maxFruitSum = Math.max(dp[row - 1][col][0] + dp[row - 1][col][1],  dp[row][col - 1][0] +  dp[row][col - 1][1]);
          dp[row][col][0] = maxFruitValue; // 여기 꼬일 것 같은데 일단 어차피 최대 합만 구하는거니까 수정 안했습니다.. (수정하다가 틀려서 포기..)
          dp[row][col][1] = maxFruitSum + board[row][col]; // 구하려는 값
          continue;
        }

        if (dp[row - 1][col][1] > dp[row][col - 1][1]) { // 오른쪽보다 위쪽에서 온 sum이 더 클때는
          // maxValue는 오른쪽이랑 현재 값이랑만 비교 후 갱신해야함.
          maxFruitValue = Math.max(dp[row - 1][col][0], board[row][col]);
          maxFruitSum = dp[row - 1][col][1];
        } else if (dp[row - 1][col][1] < dp[row][col - 1][1]) { // 반대로 위쪽보다 오른쪽에서 온 sum이 클때는
          // maxValue는 위쪽 값이랑 현재 값이랑만 비교 후 갱신해야함.
          maxFruitValue = Math.max(dp[row][col - 1][0], board[row][col]);
          maxFruitSum = dp[row][col - 1][1];
        } else { // sum이 같을 경우 최대 값을 다 갱신한다.
          maxFruitValue = Math.max(dp[row - 1][col][0], dp[row][col - 1][0]);
          maxFruitValue = Math.max(maxFruitValue, board[row][col]);
          maxFruitSum = dp[row - 1][col][1]; // 같으니까 둘 중 아무 값이나 할당한다.
        }
        dp[row][col][0] = maxFruitValue;
        dp[row][col][1] = maxFruitSum + board[row][col];
      }
    }

    bw.flush();
    bw.write(String.valueOf(dp[boardLength][boardLength][1]));
    bw.close();
    // // visited는 필요 없다 어차피 오른쪽하고 아래로만 가니까
    // Queue<int[]> q = new ArrayDeque<>();
    // q.add(new int[]{0, 0, board[0][0], board[0][0]});

    // while (!q.isEmpty()) {
    //     int[] cur = q.poll();
    //     int r = cur[0];
    //     int c = cur[1];
    //     int curFruitSum = cur[2];
    //     int curMaxFruit = cur[3];

    //     if (r == boardLength - 1 && c == boardLength - 1) {
    //         curFruitSum += curMaxFruit;
    //         maxFruitSum = Math.max(maxFruitSum, curFruitSum);
    //         continue;
    //     }

    //     for (int d = 0; d < 2; d++) {
    //         int nr = r + dr[d];
    //         int nc = c + dc[d];

    //         if (nr < 0 || nr >= boardLength || nc < 0 || nc >= boardLength) continue;

    //         int nextFruit = board[nr][nc];
    //         q.add(new int[]{nr, nc, curFruitSum + nextFruit, Math.max(curMaxFruit, nextFruit)});
    //     }
    // }

  }
}