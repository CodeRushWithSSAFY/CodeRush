import java.util.Arrays;

class 정한슬 {
  static final int PASSWORD_LENGTH = 5;
  static int possiblePasswordCount = 0;
  static int[] selectNum;
  static int numbers;
  static boolean[] isSelected;
  static int[][] q;
  static int[] ans;

  public int solution(int n, int[][] q, int[] ans) {
    this.q = q;
    this.ans = ans;
    numbers = n + 1;
    selectNum = new int[PASSWORD_LENGTH];
    isSelected = new boolean[numbers];

    combi(0, 1); // 비밀번호는 1부터 시작하므로 idx 1부터 시작

    return possiblePasswordCount;
  }
  private static void combi(int selectCount, int selectIdx) {
    if (selectCount == PASSWORD_LENGTH) {
      // System.out.println(Arrays.toString(selectNum));
      if (matchSystemResponse()) {
        possiblePasswordCount++;
      }
      return;
    }

    for (int startIdx = selectIdx; startIdx < numbers; startIdx++) {
      if (isSelected[startIdx]) continue;

      isSelected[startIdx] = true;
      selectNum[selectCount] = startIdx;
      combi(selectCount + 1, startIdx + 1);
      isSelected[startIdx] = false;
    }
  }

  private static boolean matchSystemResponse() {
    for (int idx = 0; idx < q.length; idx++) {
      int[] curQ = q[idx];
      int curMatchCount = ans[idx];
      int checkMatchCount = 0;

      int answerIdx = 0;
      int checkIdx = 0;
      while (answerIdx < 5 && checkIdx < 5) {
        if (curQ[answerIdx] == selectNum[checkIdx]) {
          answerIdx++;
          checkIdx++;
          checkMatchCount++;
        } else if (curQ[answerIdx] > selectNum[checkIdx]){
          checkIdx++;
        } else if (curQ[answerIdx] < selectNum[checkIdx]){
          answerIdx++;
        }
      }

      if(checkMatchCount != curMatchCount) {
        return false;
      }
    }

    // 여기까지 오면 맞는 값.
    return true;
  }
}