import java.io.*;
import java.util.*;

/*
    1번째 줄 - N: 돌의 개수 -> rockCount
    2번째 줄 - A[i]: 각 돌의 높낮이 N개 출력
    5
    3 2 1 4 5

    모두 초기에는 1번은 밟을 수 있다
    3 2 1 4 5
    arr[] = 1 1 1 1 1

    두번째 인덱스부터 바로 뒤 인덱스랑 비교해서 오름차순이면 같이 밟을 수 있다. arr[i] = arr[i - 1] + 1
    > 이렇게하면 중간에 못밟은 것들로 리셋됨,,

    -> 그래서 다시 i 전의 모든 경우의 수를 max해서 찾음..

    그렇게 다 갱신한 다음 모든 돌의 개수를 다시 돌아서 값이 제일 큰거 return
*/
public class 정한슬 {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    int rocksCount = Integer.parseInt(br.readLine().trim());
    int[] rocks = new int[rocksCount];

    String[] rocksInput = br.readLine().trim().split(" ");
    for (int rock = 0; rock < rocksCount; rock++) {
      rocks[rock] = Integer.parseInt(rocksInput[rock]);
    }
    br.close();

    int[] resultArr = new int[rocksCount];
    Arrays.fill(resultArr, 1); // 다 1번씩은 밟을 수 있다.

    for (int idx = 0; idx < rocksCount; idx++) { // idx 번째 돌을 넘을 때 경우의 수 찾기
      for (int idx2 = 0; idx2 <= idx; idx2++) { // idx번째 전의 상황을 다시 훑는다..
        if (rocks[idx2] < rocks[idx]) { // 만일 idx번째 돌보다 idx2(이전)에 있는 돌들이 낮은 경우 해당 돌을 밟고 idx 돌도 밟는다.
          resultArr[idx] = Math.max(resultArr[idx], resultArr[idx2] + 1);
        }
      }
    }

    int maxValue = 0;
    for (int idx = 0; idx < rocksCount; idx++) { // idx 번째 돌을 넘을 때 경우의 수 찾기
      maxValue = Math.max(maxValue, resultArr[idx]);
    }
    bw.flush();
    bw.write(String.valueOf(maxValue));
    bw.close();
  }
}