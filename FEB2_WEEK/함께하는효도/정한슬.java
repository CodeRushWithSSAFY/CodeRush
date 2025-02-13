import java.io.*;
import java.util.*;

/*
    n <= 20, 이므로 완전 탐색 가능 -> 각 친구들마다 완전 탐색 dfs를 해서 열매 수확량의 최대 합을 구하자

    처음 위치에서 0초부터 열매를 수확 할 수 있다.
    단, 처음 위치에서 진행 중에 친구들의 경로는 겹칠 수 있다.
    겹친 경우 (싸워서 이긴) 사람이 그 열매를 수확할 수 있다. (진 사람은 열매 수확 X)
*/
public class Main {
  private static final int[] dr = {1, 0, -1, 0};
  private static final int[] dc = {0, 1, 0, -1};
  private static int n;
  private static int m;
  private static int[][] appleTrees;
  private static int[][] friendPos;
  private static int maxAppleSum = Integer.MIN_VALUE;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine().trim());

    n = Integer.parseInt(st.nextToken());
    m = Integer.parseInt(st.nextToken());

    appleTrees = new int[n][n];
    for (int row = 0; row < n; row++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int col = 0; col < n; col++) {
        appleTrees[row][col] = Integer.parseInt(st.nextToken());
      }
    }

    int appleSum = 0;
    friendPos = new int[m][2];
    for (int friend = 0; friend < m; friend++) {
      st = new StringTokenizer(br.readLine().trim());
      int r = Integer.parseInt(st.nextToken()) - 1;
      int c = Integer.parseInt(st.nextToken()) - 1;
      friendPos[friend] = new int[]{r, c};
      appleSum += appleTrees[r][c];
      appleTrees[r][c] = 0; // 이미 먹은 사과는 0으로 처리, 0초부터 사과를 딸 수 있으니 미리 처리함
    }

    pickAppleInThreeSecond(0, 0, friendPos[0][0], friendPos[0][1], appleSum);

    System.out.println(maxAppleSum);
  }
  private static boolean isInBoard(int row, int col) {
    return row >= 0 && col >= 0 && row < n && col < n;
  }
  private static void pickAppleInThreeSecond(int time, int friendCnt, int r, int c, int tmpApplSum) {
    if (time == 3) {// 3초이내만 사과를 딸 수 있다.
      if (friendCnt == m - 1) {
        maxAppleSum = Math.max(maxAppleSum, tmpApplSum);
        return;
      }
      pickAppleInThreeSecond(0, friendCnt + 1, friendPos[friendCnt + 1][0], friendPos[friendCnt + 1][1], tmpApplSum);
      return;
    }

    for (int d = 0; d < 4; d++) {
      int nr = r + dr[d];
      int nc = c + dc[d];

      if (isInBoard(nr, nc)) {
        int appleValue = appleTrees[nr][nc];
        appleTrees[nr][nc] = 0;
        pickAppleInThreeSecond(time + 1, friendCnt, nr, nc, tmpApplSum + appleValue);
        appleTrees[nr][nc] = appleValue;
      }
    }
  }
}