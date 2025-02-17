import java.io.*;
import java.util.*;

/*
    가장 빠른 경로를 찾는거니까 완전 탐색이 아니라 bfs
    방문한 곳을 다시 방문하지 않으니 시간 복잡도는 O(N*M)
    그러나 유령의 경로까지 확인해야하면 유령의 개수가 K일때 O(N*M*K) 인건가?

    남우 따로, 유령들 따로 bfs를 하지 말고 한번에 bfs 해버리면(Multi-source BFS) O(NM) ? => 같이 못함.
    왜?? 같이 하려면 visted 배열이 복잡해진다. 유령의 갯수가 정해져있으면 할만한데 0개 또는 1개 이상으로만 나와있으니 굳이 List형식을 가져갈 필요 없을 듯

    queue에 넣을 정보: r, c, t, flag(flags는 0이면 남수 1이면 유령)
    ==> 유령과 남수를 구분할거라서 flag 생략함

    배열 안에서 로직을 집착하지 말고 무조건 D에서 시간 확인!! 유령의 시간이 작으면 남수는 무조건 실패

    !! 유령은 벽을 통과하니까 bfs를 굳이 하지말고 좌표값으로 간단히 구하고 min시간만 저장해둔다.
*/
public class 정한슬 {
  static final char DESTINATION = 'D';
  static final char GHOST = 'G';
  static final char NAMMU = 'N';
  static final char WALL = '#';
  static final String YES = "Yes";
  static final String NO = "No";
  static final int[] dr = {1, 0, -1, 0};
  static final int[] dc = {0, 1, 0, -1};
  static int R, C;
  static char[][] board;
  static int[] timeBoard;
  static int ghostMinTime = Integer.MAX_VALUE;


  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());

    board = new char[R][C];
    timeBoard = new int[2];
    int[] moveNammu = new int[2];
    List<int[]> moveGhost = new ArrayList<>(); // int[] 배열에 row, col
    int[] end = new int[2];
    for (int row = 0; row < R; row++) {
      String line = br.readLine().trim();
      for (int col = 0; col < C; col++) {
        char input = line.charAt(col);
        board[row][col] = input;
        if (input == NAMMU){
          moveNammu = new int[]{row, col}; // row, col
        } else if (input == GHOST) {
          moveGhost.add(new int[]{row, col});
        } else if (input == DESTINATION) {
          end = new int[]{row, col};
        }
      }
    }

    for (int[] ghost : moveGhost) {
      ghostMinTime = Math.min(ghostMinTime, Math.abs(ghost[0] - end[0]) +  Math.abs(ghost[1] - end[1]));
    }
    // System.out.println(ghostMinTime);

    // System.out.println("flag: " + timeBoard[1]);
    // System.out.println("time: " + timeBoard[0]);
    br.close();
    bw.flush();
    bw.write(goToDestination(moveNammu[0], moveNammu[1], end));
    bw.close();
  }
  private static String goToDestination(int row, int col, int[] end) {
    Queue<int[]> q = new ArrayDeque<>();
    boolean[][] nammuVisited = new boolean[R][C];
    q.add(new int[]{row, col, 0});
    nammuVisited[row][col] = true;
    // System.out.printf("현재 %d, %d에서 flag: %d ----goToDestination----- \n", row, col, flag);
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int curR = cur[0];
      int curC = cur[1];
      int curTime = cur[2];

      // System.out.printf("현재 %d, %d flag: %d time: %d\n", curR, curC, curFlag, curTime);
      for (int d = 0; d < 4; d++) {
        int nextR = curR + dr[d];
        int nextC = curC + dc[d];

        if (!isInBoard(nextR, nextC)) continue; // board 밖이면 처리 X
        if (nammuVisited[nextR][nextC]) continue;  // 방문한 곳이면 처리 X
        if (board[nextR][nextC] == WALL) continue; // 남수는 벽을 통과하지 못함

        if (nextR == end[0] && nextC == end[1]) { // 출구에 도착했을 때
          if (ghostMinTime <= curTime + 1) {
            return NO;
          } else {
            return YES;
          }
        }

        q.add(new int[]{nextR, nextC, curTime + 1});
        nammuVisited[nextR][nextC] = true;
      }
    }
    return NO;
  }

  private static boolean isInBoard(int row, int col) {
    return row >= 0 && row < R && col >= 0 && col < C;
  }
}