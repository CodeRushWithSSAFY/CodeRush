import java.io.*;
import java.util.*;

/*
    n <= 20, 이므로 완전 탐색 가능 -> 각 친구들마다 완전 탐색 dfs를 해서 열매 수확량의 최대 합을 구하자

    처음 위치에서 0초부터 열매를 수확 할 수 있다.
    단, 처음 위치에서 진행 중에 친구들의 경로는 겹칠 수 있다.
    겹친 경우 (싸워서 이긴) 사람이 그 열매를 수확할 수 있다. (진 사람은 열매 수확 X)
*/
public class 정한슬 {
  static final int MAX_TIME = 3;
  static final int[] dr = {1, 0, -1, 0};
  static final int[] dc = {0, 1, 0, -1};

  // 최종적으로 비교해서 최대값으로 갱신할 static 변수
  static int fruitMaxSum = Integer.MIN_VALUE;
  static int[][] friendPositions;
  static int[][] tree;

  public static void main(String[] args)  throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int treeSize = Integer.parseInt(st.nextToken());
    int friendsCount = Integer.parseInt(st.nextToken());
    tree = new int[treeSize][treeSize];          // 열매값이 저장 되어 있는 나무 배열
    friendPositions = new int[friendsCount][2]; // 친구들의 초기 위치를 담는 배열

    // 입력 값으로 나무 배열 저장
    for (int row = 0; row < treeSize; row++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int col = 0; col < treeSize; col++) {
        tree[row][col] = Integer.parseInt(st.nextToken());
      }
    }

    // 열매 총 합을 저장
    int fruitSum = 0;
    // 입력 값으로 친구 위치 저장
    for (int friendIdx = 0; friendIdx < friendsCount; friendIdx++){
      st = new StringTokenizer(br.readLine().trim());
      int friendRow = Integer.parseInt(st.nextToken()) - 1;
      int friendCol = Integer.parseInt(st.nextToken()) - 1;
      friendPositions[friendIdx][0] = friendRow;
      friendPositions[friendIdx][1] = friendCol;
      // 0초 부터 열매를 수확할 수 있기 때문에 열매 총합에 미리 저장
      fruitSum += tree[friendRow][friendCol];
      tree[friendRow][friendCol] = 0; // 한 번 수확한 열매는 다시 수확할 수 없기 때문에 0으로 초기화
    }

    pickFruits(0, friendsCount, 0, friendPositions[0][0], friendPositions[0][1], fruitSum);

    br.close();
    bw.flush();
    bw.write(String.valueOf(fruitMaxSum));
    bw.close();
  }

  private static void pickFruits(int friendIdx, int friendsCount, int time, int row, int col, int fruitSum) {
    // 시간이 3초면 열매 수확 할 수 없음
    if (time == MAX_TIME) {
      // 열매 수확할 친구가 없을 때 최종으로 딴 열매 총 합 값과 max값 비교
      if (friendIdx == friendsCount - 1) {
        fruitMaxSum = Math.max(fruitMaxSum, fruitSum);
      } else {
        // 열매 수확할 친구가 남아 있을 때,
        pickFruits(friendIdx + 1, friendsCount, 0, friendPositions[friendIdx + 1][0], friendPositions[friendIdx + 1][1], fruitSum);
      }
      return;
    }

    for (int d = 0; d < 4; d++) {
      int nextR = row + dr[d];
      int nextC = col + dc[d];

      if (!isInBoard(nextR, nextC)) continue; // 범위가 벗어나면 pass

      // 해당 위치로 가서 열매를 수확한다. 그럼 그 나무는 열매값이 0으로 된다.
      int curFruit = tree[nextR][nextC];
      tree[nextR][nextC] = 0;
      pickFruits(friendIdx, friendsCount, time + 1, nextR, nextC, fruitSum + curFruit);
      // 다시 복구
      tree[nextR][nextC] = curFruit;
    }

  }

  private static boolean isInBoard(int row, int col) {
    return row >= 0 && row < tree.length && col >= 0 && col < tree.length;
  }
}
