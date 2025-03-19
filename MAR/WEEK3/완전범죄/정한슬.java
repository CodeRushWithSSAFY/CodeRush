
/*
    1. dfs
    2. dp
*/
class 정한슬 {
  static int maxA;
  static int maxB;
  static int[][] infos;

  // dp에서만 사용되는 변수들
  static int[][] maxBgetsDP;

  // dfs에서만 사용되는 변수들
  static boolean[][][] visited; // idx번째의 물건을 /a가 가져간 양/ b가 가져간 양 에 대해서 true/ false 저장
  static int result = Integer.MAX_VALUE;

  public int solution(int[][] info, int n, int m) {
    maxA = n;
    maxB = m;
    infos = info;

    visited = new boolean[infos.length][maxA + 1][maxB + 1];
    makeDFS(0, 0, 0);
    makeDP ();
    // return result == Integer.MAX_VALUE ? -1 : result;
    return maxBgetsDP[info.length - 1][m - 1] >= n ? -1 :  maxBgetsDP[info.length - 1][m - 1] ;
  }

  private static void makeDFS(int curIdx, int sumA, int sumB) {
    if (sumA >= maxA || sumB >= maxB) {
      return;
    }

    if (curIdx == infos.length) {
      result = Math.min(result, sumA);
      return;
    }

    if (visited[curIdx][sumA][sumB]) return;
    visited[curIdx][sumA][sumB] = true;

    makeDFS(curIdx + 1, sumA + infos[curIdx][0], sumB);
    makeDFS(curIdx + 1, sumA, sumB + infos[curIdx][1]);
  }

  private static void makeDP() {
    int thingsCount = infos.length;
    maxBgetsDP = new int[thingsCount][maxB]; // idx번째 물건을 B가 최대로 훔친 값을 저장할 배열, 값은 결국 A가 최소로 훔친값이 될것

    int totalAMax = 0;
    // 모든 물건들을 a가 다 훔쳤다고 가정했을때 최종 흔적들 저장
    for (int[] info : infos) {
      totalAMax += info[0];
    }

    // 첫번째 물건에서 b가 훔치는 경우를 갱신, dp 첫 시작
    for (int bGets = 0; bGets < maxB; bGets++) {
      if (bGets < infos[0][1]) maxBgetsDP[0][bGets] = totalAMax;
      else maxBgetsDP[0][bGets] = totalAMax - infos[0][0]; // a는 훔치지 않고 b만 다 훔치도록 갱신.
    }

    for (int idx = 1; idx < thingsCount; idx++) {
      for (int bGets = 0; bGets < maxB; bGets++) {
        // 이번 것을 훔치지 못하면 이전 값 그대로 가져간다.
        if (bGets < infos[idx][1]) maxBgetsDP[idx][bGets] = maxBgetsDP[idx - 1][bGets];
          // 이번 것을 B가 훔칠 수 있다면, B가 훔치지 않는 값 : maxBgetsDP[idx - 1][bGets]과
          // B가 훔치는 값 두개 중 작은 값으로 가져간다.
          // 현재 훔치는 값(bGet 총합)은 이전의 물건에서 훔친 값이랑 + 애초에 현재 훔치려는 값이다.
        else maxBgetsDP[idx][bGets] = Math.min(maxBgetsDP[idx - 1][bGets], maxBgetsDP[idx - 1][bGets - infos[idx][1]] - infos[idx][0]);
      }
    }
  }
}