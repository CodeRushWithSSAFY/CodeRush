import java.io.*;
import java.util.*;

/*
    N M K
    N의 정수: 첫 번째 버튼 조작
    M의 정수: 두 번째 버튼 조작
    모두 K이하 1 이상인 정수이다.
*/
public class 정한슬 {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int firstButtonPlayCount = Integer.parseInt(st.nextToken());
    int secondButtonPlayCount = Integer.parseInt(st.nextToken());
    int maxValue = Integer.parseInt(st.nextToken()); // 이게 어느 부분에서 필요한지 모르겠어서 안썼습니다,,

    int[] firstPlay = new int[firstButtonPlayCount];
    st = new StringTokenizer(br.readLine().trim());
    for (int count = 0; count < firstButtonPlayCount; count++) {
      firstPlay[count] = Integer.parseInt(st.nextToken());
    }

    int[] secondPlay = new int[secondButtonPlayCount];
    st = new StringTokenizer(br.readLine().trim());
    for (int count = 0; count < secondButtonPlayCount; count++) {
      secondPlay[count] = Integer.parseInt(st.nextToken());
    }

    int[][] dp = new int[firstButtonPlayCount + 1][secondButtonPlayCount + 1];
    int maxLength = 0;
    for (int index1 = 1; index1 <= firstButtonPlayCount; index1++) {
      for (int index2 = 1; index2 <= secondButtonPlayCount; index2++) {
        if (firstPlay[index1 - 1] == secondPlay[index2 - 1]) {
          dp[index1][index2] = dp[index1 - 1][index2 - 1] + 1;
          maxLength = Math.max(maxLength, dp[index1][index2]);
        }else {
          dp[index1][index2] = 0;
        }
      }
    }

    System.out.println(maxLength);

    br.close();
  }
}