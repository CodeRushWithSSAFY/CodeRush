import java.io.*;
import java.util.*;

/*
    A: 북 -> 남
    C: 남 -> 북
    B: 동 -> 서
    D: 서 -> 동

    <> 충돌
    A <> B or D
    B <> A or C
    C <> B or D
    D <> A or C


    교차로에 A, B, C, D 방향에 모두 차가 있는 경우에는 -1로 반환
    그 이후의 모든 차들도 -1 ?
*/
public class 정한슬 {
  static Map<Character, List<Character>> conflictMap; // 충돌 맵
  public static void main(String[] args) throws IOException {

    initConflictMap(); // 충돌 맵 저장
// test
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine().trim());

    StringTokenizer st = null;
    int maxTime = 0; // 가장 나중인 시간
    int[][] timeSplitSituation = new int[N][2]; // 가장 나중 시간을 저장하기 위해 시간별 상황(입력값)을 담음
    for (int count = 0; count < N; count++) {
      st = new StringTokenizer(br.readLine().trim());
      int time = Integer.parseInt(st.nextToken());
      char roadDirection = st.nextToken().charAt(0);
      timeSplitSituation[count] = new int[]{time, (int) roadDirection};
      maxTime = Math.max(maxTime, time); // max로 갱신
    }
    br.close();

    List<Queue<Character>> roadSituation = new ArrayList<>(); // 각 시간별로 차의 방향을 기록하기 위하여 List에 Queue를 담음
    for (int time = 0; time <= maxTime; time++) { // 앞전에 구한 maxTime까지만 갱신해놓기
      roadSituation.add(new ArrayDeque<>());
    }
    for (int count = 0; count < N; count++) {
      int[] timeSituation = timeSplitSituation[count];  // 앞전에 저장한 배열을 다시 꺼내 시간별로 Queue에 넣어놓음
      int time = timeSituation[0];
      char roadDirection = (char) timeSituation[1];
      roadSituation.get(time).add(roadDirection);
    }
    System.out.println(roadSituation);

    List<Integer> exitTimes = new ArrayList<>(); // 차량이 나간 시간 담기
    int realTime = 0;
    for (int time = 0; time <= maxTime; time++) {
      Queue<Character> carQueue = roadSituation.get(time);
      if (carQueue.isEmpty()) { // 해당 시간대에 차량이 존재하지 않을 때 skip
        continue;
      }

      if (exitTimes.size() == 0) {
        // 해당 시간대에 차량이 존재할때 이전의 나간 시간이 없으면 현재 시간을 realTime으로 갱신한다.
        realTime = time;
      } else {
        int beforeExitTime = exitTimes.get(exitTimes.size() - 1);

        if (beforeExitTime < time) {
          // 혹은 이미 나간 차량의 시간이 현재 시간보다 작으면 현재 시간을 realTime으로 갱신해도 된다.
          realTime = time;
        } else { // exitTimes.get(exitTimes.size() - 1) >= time
          // 이미 나간 차량의 시간이 현재 시간보다 같거나 크면 이미 밀린것.. 현재 차도 밀린 시간을 따라야함.
          realTime = beforeExitTime + 1;
        }
      }
      while (!carQueue.isEmpty()) {
        char firstCar = carQueue.poll();
        Character nextCar = carQueue.peek();
        if (nextCar == null && realTime != -1) {
          // 다음 차가 없으므로 그냥 바로 지나갈 수 있다.
          exitTimes.add(realTime);
          break;
        }
        if (realTime != -1 && (!conflictMap.get(firstCar).contains(nextCar))) {
          // 충돌하지 않는 맵에 저장되어있는 방향이거나 같은 방향이면 지나간다.
          exitTimes.add(realTime);
        } else if (conflictMap.get(firstCar).contains(nextCar)) {
          // 충돌이 발생한 경우
          realTime = -1;
          exitTimes.add(realTime);
        }
      }
    }

    for (int exitTime : exitTimes) {
      System.out.println(exitTime);
    }
  }

  private static void initConflictMap() {
    conflictMap = new HashMap<>();
    conflictMap.put('A', Arrays.asList('B', 'D')); // A와 충돌하는 도로
    conflictMap.put('B', Arrays.asList('A', 'C')); // B와 충돌하는 도로
    conflictMap.put('C', Arrays.asList('B', 'D')); // C와 충돌하는 도로
    conflictMap.put('D', Arrays.asList('A', 'C')); // D와 충돌하는 도로
  }
}