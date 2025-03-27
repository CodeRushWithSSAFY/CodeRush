import java.util.*;

/*
	코너링은 500원
    직선은 100원 비용이 들때, (0, 0) -> (N - 1, N - 1) 까지 가는 경주로를 최소 비용으로 설치하자

    <해결법>
    1. 최단 경로를 계산한다.
    2. queue에 int[]{row, col, 이전 방향, 직선 갯수, 코너링 갯수}을 넣는다.
    	1> 다음으로 갈수있을때 (벽이 아니고, board 밖이 아닐때) 직선 갯수 + 1를 해준다.
    	2> 이전 방향과 현재 direction 방향이 다를때는 코너링 갯수 + 1을 해준다.
*/
class 정한슬 {
  static final int[] DR = new int[]{1, 0, -1, 0};
  static final int[] DC = new int[]{0, 1, 0, -1};
  static final int CONER_COST = 500;
  static final int LINE_COST = 100;

  static int length;
  static int minCost = Integer.MAX_VALUE;
  static int[][] board;
  static int curRow, curCol, curDirection, curCost, nextRow, nextCol, newCost;
  static int[][][] cost;

  public int solution(int[][] board) {
    length = board.length;
    this.board = board;

    findMinCost();

    return minCost;
  }

  private static void findMinCost() {
    cost = new int[length][length][4];
    for (int[][] arrs : cost) {
      for (int[] arr: arrs) {
        Arrays.fill(arr, Integer.MAX_VALUE);
      }
    }

    Queue<int[]> queue = new ArrayDeque<>();
    for (int direction = 0; direction < 4; direction++) {
      queue.add(new int[]{0, 0, direction, 0});
      cost[0][0][direction] = 0;
    }


    while (!queue.isEmpty()) {
      int[] curStatus = queue.poll();
      curRow = curStatus[0];
      curCol = curStatus[1];
      curDirection = curStatus[2];
      curCost = curStatus[3];

      if (curRow == length - 1 && curCol == length - 1) {
        minCost = Math.min(minCost, curCost);
        continue;
      }

      for (int direction = 0; direction < 4; direction++) {
        nextRow = curRow + DR[direction];
        nextCol = curCol + DC[direction];

        if (!isInBoard(nextRow, nextCol)) continue;
        if (board[nextRow][nextCol] == 1) continue;

        if (curDirection == direction) {
          newCost =  curCost + LINE_COST;
        } else {
          newCost = curCost + LINE_COST + CONER_COST;
        }

        if (cost[nextRow][nextCol][direction] > newCost) {
          cost[nextRow][nextCol][direction] = newCost;
          queue.add(new int[]{nextRow, nextCol, direction, newCost});
        }
      }

    }
  }

  private static boolean isInBoard(int row, int col) {
    return row >= 0 && row < length && col >= 0 && col < length;
  }
}