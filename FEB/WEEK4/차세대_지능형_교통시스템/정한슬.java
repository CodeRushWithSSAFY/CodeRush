import java.io.*;
import java.util.*;

/*
    N: 확인해야할 도로의 size. -> roadSize 교차로의 개수는 roadSize * roadSize 만큼 존재한다.
    T: 시간 -> time

    특정 교차로에서는 시간이 0부터 3까지 4가지의 신호 상태가 주어지는데 이것이 반복되는 구조이다.
    ex> 1번교차로의 intersctionSignal {1, 7, 11, 9} -> T=0: 1, T=1: 7, T=2: 11, T=3: 9, T=4: 1 ...

    예를들어 1번 교차로에서의 시간 1초때는 7번 신호 `{{0, -1}, {1, 0}}` 만 받을 수 있다. 이때 road밖이면 가지 못하고 끝난다.

    Queue(ArrayDeque)에 넣어서 시간 0초부터 time초까지 지나간 교차로를 boolean에 true로 설정해놓자.
    차는 맨 처음에 (1,1) 바로 아래에서 시작한다.(아직 아무런 교차로도 지나가지 않은 상황.)
    그냥 (1, 1) 부터 queue에 넣어놓는 다음,  intersectionSignal[1][1][(time + 1) % 4]의 신호값을 확인 후에 SIGNAL 목록에서 해당 신호를 받아 다음 교차로를 간다.
    그럼 그때 (1, 1)의 교차로를 카운팅하자.

    time 시간대까지 반복하여 최종적으로 지나갈 수 있는 교차로의 개수를 구하자.

    -> 처음 코드가 오답 및 메모리 초과가 남
    bfs에서 메모리 초과가 나는 것은 queue에 너무 많은 양이 쌓인 것.. 반복적으로 쌓이지 않게 조심하자 (중복된 상태를 큐에 쌓지 말아야한다)

    -> 처음 코드가 오답난 이유 현재 차량의 direction을 고려하지 않았음. 즉 아래에서 위로 오는 차의 방향을 0, 1, 2, 3

*/
public class 정한슬 {
  static final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 상, 우, 하, 좌
  static Map<Integer, int[][]> traffic = new HashMap<>();
  static BufferedReader br;
  static StringTokenizer st;
  static BufferedWriter bw;
  static int roadSize;
  static int time;
  static int passedIntersectionCount;
  static boolean[][] visitedIntersection; // 교차로 방문 여부
  static int[][][] intersectionSignal;

  public static void main(String[] args) throws IOException {
    bw = new BufferedWriter(new OutputStreamWriter(System.out));

    initInput();
    drive();

    bw.write(String.valueOf(passedIntersectionCount));
    bw.flush();
    bw.close();
  }

  private static void drive() {
    Queue<int[]> carStatus = new ArrayDeque<>();
    // 초기 상태: (1, 1)에서 아래쪽 방향으로 진입
    carStatus.add(new int[]{1, 1, 0, -1, 0}); // row, col, 시간, 이전 이동 방향

    while (!carStatus.isEmpty()) {
      int[] curStatus = carStatus.poll();
      int curRow = curStatus[0];
      int curCol = curStatus[1];
      int curTime = curStatus[2];
      int prevDr = curStatus[3];
      int prevDc = curStatus[4];
      int curSignalNumber = intersectionSignal[curRow][curCol][curTime % 4];
      int[][] moves = traffic.get(curSignalNumber);

      if (curTime > time) continue;

      if (!visitedIntersection[curRow][curCol]) {
        visitedIntersection[curRow][curCol] = true;
        passedIntersectionCount++;
      }


      // 진입 방향 체크
      int[] entryDir = moves[0];
      if (prevDr != entryDir[0] || prevDc != entryDir[1]) continue;


      // 가능한 이동
      for (int i = 1; i < moves.length; i++) {
        int[] move = moves[i];
        int nextRow = curRow + move[0];
        int nextCol = curCol + move[1];

        if (!isInRoad(nextRow, nextCol)) continue;

        carStatus.add(new int[]{nextRow, nextCol, curTime + 1, move[0], move[1]});
      }
    }
  }

  private static boolean isInRoad(int row, int col) {
    return row >= 1 && row <= roadSize && col >= 1 && col <= roadSize;
  }

  private static void initInput() throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine().trim());
    roadSize = Integer.parseInt(st.nextToken());
    time = Integer.parseInt(st.nextToken());
    visitedIntersection = new boolean[roadSize + 1][roadSize + 1];
    intersectionSignal = new int[roadSize + 1][roadSize + 1][4];

    for (int count = 0; count < roadSize * roadSize; count++) {
      st = new StringTokenizer(br.readLine().trim());
      int row = (count / roadSize) + 1;
      int col = (count % roadSize) + 1;
      for (int timeIdx = 0; timeIdx < 4; timeIdx++) {
        intersectionSignal[row][col][timeIdx] = Integer.parseInt(st.nextToken());
      }
    }
    br.close();

    traffic.put(1, new int[][]{{0, 1}, {-1, 0}, {0, 1}, {1, 0}});
    traffic.put(2, new int[][]{{-1, 0}, {0, -1}, {-1, 0}, {0, 1}});
    traffic.put(3, new int[][]{{0, -1}, {-1, 0}, {0, -1}, {1, 0}});
    traffic.put(4, new int[][]{{1, 0}, {0, 1}, {1, 0}, {0, -1}});
    traffic.put(5, new int[][]{{0, 1}, {-1, 0}, {0, 1}});
    traffic.put(6, new int[][]{{-1, 0}, {0, -1}, {-1, 0}});
    traffic.put(7, new int[][]{{0, -1}, {0, -1}, {1, 0}});
    traffic.put(8, new int[][]{{1, 0}, {1, 0}, {0, 1}});
    traffic.put(9, new int[][]{{0, 1}, {0, 1}, {1, 0}});
    traffic.put(10, new int[][]{{-1, 0}, {-1, 0}, {0, 1}});
    traffic.put(11, new int[][]{{0, -1}, {-1, 0}, {0, -1}});
    traffic.put(12, new int[][]{{1, 0}, {0, -1}, {1, 0}});
  }
}
