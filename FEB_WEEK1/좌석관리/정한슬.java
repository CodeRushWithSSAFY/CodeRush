import java.io.*;
import java.util.*;

/*
    N: ROW 길이
    M: COL 길이
    Q: 작업 횟수
    점심 식사는 단 한번. 이미 먹은 사람이면 돌려보내야한다.
    board에 모든 좌석이 비어있으면 들어오는 사람을 (1,1)로 배정한다.

    배치 방식:
    1. 아무도 없을 때 (1, 1)로 배정
    2. 누군가 1명이라도 있을 때 가장 가까이 있는 거리 중 맨하튼 거리 1 초과로 배정
    3. 1이상인 자리밖에 없을 경우 가득 찬 경우

*/
public class 정한슬 {
  // 작업 명령어
  static final String IN = "In";
  static final String OUT = "Out";

  // 작업 명령어가 In 인 경우
  //{id}
  static final String ALREADY_SEATED = "%d already seated.\n";
  static final String ALREADY_ATE_LUNCH = "%d already ate lunch.\n";
  static final String NO_SEAT = "There are no more seats.\n";
  static final String GET_THE_SEAT = "%d gets the seat (%d, %d).\n"; // (x, y)

  // 작업 명령어가 Out 인 경우
  //{id}
  static final String NOT_EAT_LUNCH = "%d didn't eat lunch.\n";
  static final String ALREADY_LEFT_SEAT = "%d already left seat.\n";
  static final String LEAVE_SEAT = "%d gets the seat (%d, %d).\n"; // (x, y) , 해당 사원은 점심 먹은 상태로 기록

  static boolean[] hasEatLunch;
  static Map<Integer, int[]> seatForLunch;
  static int[][] board;

  // TODO: 좌석 로직 완성  + bw로 바꾸기
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());

    board = new int[N + 1][M + 1]; //1-base 2차원 배열 0 row, 0 col 은 버림
    hasEatLunch = new boolean[10001];
    seatForLunch = new HashMap<>();

    for (int workCount = 0; workCount < Q; workCount++) {
      st = new StringTokenizer(br.readLine().trim());
      String[] queries = st.nextToken().split(" ");
      String command = queries[0];
      String member = queries[1];

      switch(command) {
        case IN:
          if(!seatForLunch.containsKey(member)) { // Map에서 key 조차 없으면 앉은적이 없으니 배치해야한다.

            int[] results = setSeat(board); // rule에 맞게 배치
            // 안전도에 따라서 앉을 수 있는 좌석이 없는 경우 null 반환
            if (results == null) {
              System.out.printf(NO_SEAT);
            } else {
              System.out.printf(GET_THE_SEAT, results[0], results[1]);
            }
          } else if (seatForLunch.containsKey(member)
              && seatForLunch.get(member) != null) { // Map에서 null이 아닐 땐 앉아있는것이다.
            System.out.printf(ALREADY_SEATED, member);
          } else if (hasEatLunch[member]) { // hasEatLunch가 true 일때는 이미 먹었다.
            System.out.printf(ALREADY_ATE_LUNCH, member);
          }
          break;
        case OUT:
          if(!seatForLunch.containsKey(member)) { // 맵에 key조차 없다면 점심을 먹지도 않은 사원
            System.out.printf(NOT_EAT_LUNCH, member);
          } else if (hasEatLunch[member] && seatForLunch.get(member) == null){
            // hasEatLunch[member]에 true이고 점심 먹은 사원의 위치가 null인 경우 이미 먹고 떠났다.
            System.out.printf(ALREADY_LEFT_SEAT, member);
          } else { // null이 아닌 경우 앉은 좌석 출력
            int[] curPos = seatForLunch.get(member);
            System.out.printf(LEAVE_SEAT, member, curPos[0], curPos[1]);
            // 먹은 것으로 처리
            seatForLunch.put(member, null);
            hasEatLunch[member] = true;
          }
          break;
      }

    }
  }

  /*
      배치 룰
      1> board에 아무도 없으면 (1, 1)에 배치
      2-1>board에 누군가가 있으면 안전도가 가장 높은 좌석에 배치
      2-2>안전도가 가장 높은 좌석이 여러개면 X가 가장 낮은 좌석을, X도 모두 같다면 Y가 가장 낮은 좌석을 배정
          이때 안전도는 1 초과로 해야한다. 1이면 안된다.
  */
  private static int[] setSeat(int[][] board){
    return null; // 내일 다시 생각..
  }
}