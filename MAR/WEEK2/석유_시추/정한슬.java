import java.util.*;

/*
    1. 1인 곳마다 bfs로 탐색하여 연결되어 있는 영역의 크기를 구한다.
    2. 영역의 크기를 구할때마다 새로운 col이면 set에 기록을 해둔다.
    3. Map을 만들어 col별로 영역의 크기를 저장한다.
    4. 1과 2에서 찾은 영역을 col별로 더해서 저장한다.
    ex> col 1, 2, 3에 걸친 영역 8을 map.get(1) + 8, map.get(2) + 8, .. 으로 갱신한다.
*/
class 정한슬 {
  static int[] DR = {1, 0 , -1, 0};
  static int[] DC = {0, 1, 0, -1};

  static Map<Integer, Integer> areasByCols; // col별로 영역의 크기를 저장하는 맵
  static int rowLength, colLength;
  static int curRow, curCol, nextRow, nextCol, curArea, curMaxArea;
  static boolean[][] isVisited;
  static int[][] land;

  public int solution(int[][] land) {
    rowLength = land.length;
    colLength = land[0].length;
    this.land = land;
    initMap();
    isVisited = new boolean[rowLength][colLength];

    for (int row = 0; row < rowLength; row++) {
      for (int col = 0; col < colLength; col++) {
        if (isVisited[row][col]) continue; // 이미 확인한 영역 중 하나면 pass

        if (land[row][col] == 1) {
          findArea(row, col);
        }
      }
    }
    return findMaxArea();
  }

  private static int findMaxArea() {
    int maxResult = 0;
    for (Map.Entry<Integer, Integer> areaEntry : areasByCols.entrySet()) {
      maxResult = Math.max(maxResult, areaEntry.getValue());
    }
    return maxResult;
  }

  private static void findArea(int row, int col) {
    Queue<int[]> queue = new ArrayDeque<>();
    queue.add(new int[]{row, col}); // 1로 해서 curArea로 같이 담으면 틀림 ,, .. 왜?
    isVisited[row][col] = true;
    Set<Integer> colsSet = new HashSet<>();
    colsSet.add(col);
    curMaxArea = 0;

    curArea = 0;
    while (!queue.isEmpty()) {
      int[] curStatus = queue.poll();
      curRow = curStatus[0];
      curCol = curStatus[1];

      curArea++;

      curMaxArea = Math.max(curMaxArea, curArea);

      for (int direction = 0; direction < 4; direction++) {
        nextRow = curRow + DR[direction];
        nextCol = curCol + DC[direction];

        if (!isInBoard(nextRow, nextCol)) continue;

        if (land[nextRow][nextCol] == 0) continue;

        if (isVisited[nextRow][nextCol]) continue;

        queue.add(new int[]{nextRow, nextCol, curArea + 1});
        isVisited[nextRow][nextCol] = true;
        if (!colsSet.contains(nextCol)) colsSet.add(nextCol);
      }
    }

    // 다 끝났으면 맵에 col별로 영역 추가로 저장
    for (Integer uniqueCol : colsSet) {
      int newArea = areasByCols.get(uniqueCol) + curMaxArea;
      areasByCols.put(uniqueCol, newArea);
    }
  }

  private static void initMap() {
    areasByCols = new HashMap<>();
    for (int col = 0; col < colLength; col++) {
      areasByCols.put(col, 0);
    }
  }

  private static boolean isInBoard(int row, int col) {
    return row >= 0 && row < rowLength && col >= 0 && col < colLength;
  }
}