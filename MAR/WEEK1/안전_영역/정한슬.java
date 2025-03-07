import java.io.*;
import java.util.*;

/*
 * 꽃가루 안전 점수 정보가 n by n 2차원 배열에 저장되어있을 때,
 * 꽃가루 농도에 따라 꽃가루 알레르기에 안전한 구역을 count 할 수 있다.
 *
 * 예를 들어 꽃가루 농도가 4이면, board에 4 초과인 칸은 안전하다.
 * 이때 상,하,좌,우 4방향이 인접한 칸들이 있다면 연결하여 하나의 영역으로 둔다.
 *
 * 이때 꽃가루 알레르기가 언제일 때 가장 최대의 영역을 갖게되는지 구하여라. (영역의 개수만)
 *
 * <해결법>
 * 2차원 배열 flowerBoard에 기록할 때 꽃가루 안전점수가 가장 작은것과 큰것을 기록해둔다.
 * 작은 것부터 큰거 까지 완전탐색을 하여 그때마다 영역의 개수를 기록해두고 최대 영역의 개수와 비교하여 갱신할 수 있으면 갱신한다.
 * 영역은 어떻게 구하냐?
 * 1. board에서 특정 꽃가루 농도보다 안전한(초과, 높은) point를 List에 담는다.
 * 2. List를 순회하면서 visited를 방문한거면 넘어가고 방문하지 않은거면 queue에 담아 4방향 인접한 칸들을 모두 셀 수 있도록한다.
 * 3. keyIdx를 구분하여 map에 연결된 것을 담는다.
 * 4. map의 key 개수가 영역의 개수이다.
 * */
public class 정한슬 {
  static final int[] DR = {1, 0, -1, 0};
  static final int[] DC = {0, 1, 0, -1};
  static BufferedReader br;
  static BufferedWriter bw;
  static StringTokenizer st;

  static int boardSize;
  static int[][] flowerBoard;
  static int maxSafeZoneCount;
  static int minFlowerScore = Integer.MAX_VALUE;
  static int maxFlowerScore = Integer.MIN_VALUE;
  static List<int[]> safePoint;
  static Queue<int[]> safePointNext;
  static boolean[][] visited;

  static int curRow, curCol, nextRow, nextCol, curKeyIdx;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    bw = new BufferedWriter(new OutputStreamWriter(System.out));

    boardSize = Integer.parseInt(br.readLine().trim());

    flowerBoard = new int[boardSize][boardSize];
    for (int row = 0; row < boardSize; row++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int col = 0; col < boardSize; col++) {
        flowerBoard[row][col] = Integer.parseInt(st.nextToken());
        minFlowerScore = Math.min(minFlowerScore, flowerBoard[row][col]);
        maxFlowerScore = Math.max(maxFlowerScore, flowerBoard[row][col]);
      }
    }

    br.close();

    if (minFlowerScore == maxFlowerScore) {
      bw.write(String.valueOf(1));
      bw.flush();
      bw.close();
      return;
    }

    for (int flowerScore = minFlowerScore; flowerScore <= maxFlowerScore; flowerScore++) {
      safePoint = new ArrayList<>();
      visited = new boolean[boardSize][boardSize];
      maxSafeZoneCount = Math.max(maxSafeZoneCount, findSafeZone(flowerScore));
    }

    bw.write(String.valueOf(maxSafeZoneCount));
    bw.flush();
    bw.close();
  }

  private static int findSafeZone(int flowerScore) {
    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        if (flowerBoard[row][col] > flowerScore) { // 안전한 row, col 지점 저장
          safePoint.add(new int[] {row, col});
        }
      }
    }

    if (safePoint.size() == 0) return 0; // 안전한 지점이 하나도 없으면 모두 알레르기반응이 일어난다. 안전한 영역의 개수는 0개

    safePointNext = new ArrayDeque<>();
    int keyIdx = 1;
    Map<Integer, List<int[]>> map = new HashMap<>();
    // list에 담은 point들에서 각 4방향 인접한 칸들을 확인할 수 있도록 체크한다.
    for (int safePointIdx = 0; safePointIdx < safePoint.size(); safePointIdx++) {
      int[] curPoint = safePoint.get(safePointIdx);

      curRow = curPoint[0];
      curCol = curPoint[1];

      if (visited[curRow][curCol]) continue;

      safePointNext.add(new int[] {curRow, curCol, keyIdx++});
      while (!safePointNext.isEmpty()) {
        int[] curCheckPoint = safePointNext.poll();
        curRow = curCheckPoint[0];
        curCol = curCheckPoint[1];
        curKeyIdx = curCheckPoint[2];

        if (visited[curRow][curCol]) continue;

        visited[curRow][curCol] = true;
        if (!map.containsKey(curKeyIdx)) {
          map.put(curKeyIdx, new ArrayList<>());
        }
        map.get(curKeyIdx).add(new int[] {curRow, curCol});

        for (int direction = 0; direction < 4; direction++) {
          nextRow = curRow + DR[direction];
          nextCol = curCol + DC[direction];

          if (!insInBoard(nextRow, nextCol)) continue;

          if (visited[nextRow][nextCol]) continue;
          if (flowerBoard[nextRow][nextCol] <= flowerScore) continue;

          safePointNext.add(new int[] {nextRow, nextCol, curKeyIdx});
          map.get(curKeyIdx).add(new int[] {nextRow, nextCol});
        }
      }
    }

    return map.keySet().size();
  }

  private static boolean insInBoard(int row, int col) {
    return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
  }
}
