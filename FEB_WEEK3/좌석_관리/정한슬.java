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
    2. 누군가 1명이라도 있을 때 가장 가까이 있는 거리 중 안전도 1 초과해야 앉을 수 있다.

    --> 배열의 크기가 총 400이라서 브루트포스로 가능?  ==> ,,

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
  static final String LEAVE_SEAT = "%d leaves from the seat (%d, %d).\n"; // (x, y) , 해당 사원은 점심 먹은 상태로 기록

  static BufferedReader br;
  static BufferedWriter bw;
  static StringTokenizer st;

  static int boardRow;
  static int boardCol;
  // 따로 클래스로 구분하지 않고 int[]에서 idx로 구분한다. 0 - row, 1 - col, 2 - lunch먹었는지 flag(0이면 안먹은거, 1이면 먹은것)
  static Map<Integer, int[]> seatForLunch = new HashMap<>(); // key 값은 member의 id
  // member를 좌석에 앉혔을때 해당 좌석에서의 상하좌우 좌석을 막기 위한 direction 델타 배열
  static int[] dr = {1, 0 , -1, 0};
  static int[] dc = {0, 1, 0, -1};
  static int[][] board; // 앉은 좌석 관리할 board. 이 보드로 안전도를 체크함

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    bw = new BufferedWriter(new OutputStreamWriter(System.out));

    st = new StringTokenizer(br.readLine().trim());
    boardRow = Integer.parseInt(st.nextToken());
    boardCol = Integer.parseInt(st.nextToken());
    int queryCount = Integer.parseInt(st.nextToken());

    seatForLunch = new HashMap<>();
    String[] queries = new String[queryCount];
    // 입력값 받아놓기
    for (int count = 0; count < queryCount; count++) {
      queries[count] = br.readLine().trim();
    }

    br.close();

    board = new int[boardRow + 1][boardCol + 1]; // index 1 base로 1씩 추가. 초기화

    for (int queryIdx = 0; queryIdx < queryCount; queryIdx++) {
      String[] querySplit =queries[queryIdx].split(" ");
      String command = querySplit[0];
      int memberId = Integer.parseInt(querySplit[1]);

      switch(command) {
        case IN:
          if(seatForLunch.get(memberId) == null) { // Map에서 key 조차 없으면 앉은적이 없으니 배치해야한다.
            int[] seat = setSeat(memberId); // rule에 맞게 배치
            // 안전도에 따라서 앉을 수 있는 좌석이 없는 경우 null 반환
            if (seat == null) {
              bw.write(NO_SEAT);
            } else {
              bw.write(String.format(GET_THE_SEAT, memberId, seat[0], seat[1]));
            }
          } else if (seatForLunch.get(memberId)[2] == 0) { // key값이 있을 땐 앉아있는 것인데 점심을 먹었는지는 idx 2 확인
            // flag가 0이면 아직 점심을 먹은 것은 X. 앉아있는 상황
            bw.write(String.format(ALREADY_SEATED, memberId));
          } else if (seatForLunch.get(memberId)[2] == 1) { //idx 2에서 flag가 1이면 이미 점심을 먹은 것
            bw.write(String.format(ALREADY_ATE_LUNCH, memberId));
          }
          break;
        case OUT:
          if(seatForLunch.get(memberId) == null) { // 맵에 key조차 없다면 앉지도 않은 사원
            bw.write(String.format(NOT_EAT_LUNCH, memberId));
          } else if (seatForLunch.get(memberId)[2] == 1) {
            // flag가 1이면 이미 점심을 먹고 떠난 것
            bw.write(String.format(ALREADY_LEFT_SEAT, memberId));
          } else if (seatForLunch.get(memberId)[2] == 0) { // flag가 0이면 점심을 먹은 건 아니고 이제 점심 먹었다고 처리해야함
            int[] seat = seatForLunch.get(memberId);
            bw.write(String.format(LEAVE_SEAT, memberId, seat[0], seat[1]));
            // 먹은 것으로 처리
            int row = seat[0];
            int col = seat[1];
            seatForLunch.put(memberId, new int[]{row, col, 1}); // 먹은 것으로 처리. flag를 1으로 바꾼다.
            board[row][col] = 0; // 비우기
            guardProcessing(row, col, -1); // 빈자리 처리
          }
          break;
      }
    }
    bw.flush();
    bw.close();
  }

  /*
      배치 룰
      1> board에 아무도 없으면 (1, 1)에 배치
      2-1>board에 누군가가 있으면 안전도가 가장 높은 좌석에 배치
      2-2>안전도가 가장 높은 좌석이 여러개면 X가 가장 낮은 좌석을, X도 모두 같다면 Y가 가장 낮은 좌석을 배정
          이때 안전도는 1 초과로 해야한다. 1이면 안된다.
      2-3> 안전도가 1이하이면 앉을 공간이 없다는 의미
  */
  private static int[] setSeat(int memberId){
    int usedRow = 1;
    int usedCol = 1;
    int safe = 0;
    for (int row = 1; row <= boardRow; row++) {
      for (int col =1; col <= boardCol; col++) {
        if (board[row][col] != 0) continue;

        int curPositionMinSafe = Integer.MAX_VALUE;

        for (int[] memberCondition : seatForLunch.values()) {
          if (memberCondition[2] == 1) continue; // 이미 먹은 사람은 나간거니까 pass

          int tmpSafe = (int) Math.pow(memberCondition[0] - row, 2) + (int) Math.pow(memberCondition[1] - col, 2);
          curPositionMinSafe = Math.min(curPositionMinSafe, tmpSafe);
        }
        if (curPositionMinSafe > safe && curPositionMinSafe > 1) { // 특정 좌석에서의 안전도가(가장 작은 값)이 제일 크다면 해당 좌석으로 배치해야함
          usedRow = row;
          usedCol = col;
          safe = curPositionMinSafe; // 갱신
        }
      }
    }

    if (safe < 1) { // 자리의 안전도가 1보다 작으면 앉을 공간이 없다는 의미
      return null;
    }

    board[usedRow][usedCol] = memberId; // 아니면 배치할 수 있음
    guardProcessing(usedRow, usedCol, 1);// 상하좌우 가드로 막기
    seatForLunch.put(memberId, new int[]{usedRow, usedCol, 0}); // 좌석 기록

    return new int[]{usedRow, usedCol};
  }

  private static void guardProcessing(int row, int col, int guardFlag) { // guardFlag 1, -1으로 할당한다.
    for (int d = 0; d < 4; d++) {
      int nextRow = row + dr[d];
      int nextCol = col + dc[d];
      if (!isInBoard(nextRow, nextCol)) continue; // board 밖이면 pass
      board[nextRow][nextCol] += guardFlag;
    }
  }

  private static boolean isInBoard(int row, int col) {
    return row >= 1 && col >= 1 && row <= boardRow && col <= boardCol;
  }
}