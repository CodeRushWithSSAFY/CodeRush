import java.util.*;


/*
    1. 0시부터 23시까지 증설된 서버의 수를 저장할 int[] 배열을 만든다.
    2. players 배열을 돌면서, 게임 이용자 수가 m 이상 일때  players[i] /m 몫 만큼의 서버가 증설된다.
        -> 증설된 서버의 수를 저장
    3. 증설된 서버는 연속된 뒤의 k - 1 index까지 유지가 된다.
*/
class 정한슬 {
  static int addServerCount;
  static int curAddServerCount;
  static int[] hours;

  public int solution(int[] players, int m, int k) {
    addServerCount = 0;
    hours = new int[24];

    for (int idx = 0; idx < players.length; idx++) {
      if (players[idx] < m) continue;

      int q = players[idx] / m;
      if (hours[idx] >= q) continue;

      if (hours[idx] != 0) {
        curAddServerCount =  q - hours[idx];
      } else {
        curAddServerCount =  q;
      }

      if (q > 0) {
        for (int count = 0; count < k; count++) {
          if (idx + count >= 24) break;
          hours[idx + count] += curAddServerCount;
        }
      }
      addServerCount += curAddServerCount;
    }

    return addServerCount;
  }
}