import java.io.*;
import java.util.*;

/*
    N: 컴퓨터 수 -> computerCount
    B: 예산 -> maxBudget  : 10^18으로 큰 수니 long 타입으로 받는다.
    ai: a1, a2, .. aN : 컴퓨터 성능 예산이 매우 커서 기존 컴퓨터 성능을 64비트 정수형으로 변경 가능하니(2^64) long 배열로 두자

    브루트포스처럼 특정 컴퓨터 성능을 최소값으로 두고 판단하기에는 무리인 제약 조건.
    대게 이런 류들(대기시간, 최대로 지날 수 있는 돌다리 등등,,)은 보통 이분탐색으로 뭉뚱그려서 값을 찾아보는 듯,,
    이 값으로 충분히 할수있어? 그럼 더 늘려봐(혹은 더 줄여봐) -> 안되면 아까 그 값 return
    이 값으로 충분하지 않아? 그럼 더 줄여봐(혹은 더 늘려봐)
    그런데 혼자 생각해서 풀진 못했을 것 같다.

    그리고 이분탐색은 대게 sort후에 해야 한다.(?)
*/
public class 정한슬 {
  static long maxValueForMin성능 = Long.MIN_VALUE; // 가장 작은값으로 설정하고 max값 갱신. 만약 이 값이 갱신되지 않았으면 컴퓨터 튜닝 못하는 거. -> 그럼 가장 큰 min값은 기존에 가장 작은 값일것.

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int computerCount = Integer.parseInt(st.nextToken());
    long maxBudget = Long.parseLong(st.nextToken());

    st = new StringTokenizer(br.readLine().trim());
    long[] a = new long[computerCount];
    for (int idx = 0; idx < computerCount; idx++) {
      a[idx] = Long.parseLong(st.nextToken());
    }
    Arrays.sort(a); // 작은 순으로 sort

    br.close(); // 입력 닫기

    long leftValue = a[0]; // 가장 작은 값: 튜닝 안한 제일 작은 값
    long rightValue = a[computerCount - 1] + (long) Math.sqrt(maxBudget); // 제일 큰 값을 예산 다 써서 튜닝 한 값

    // 이분 탐색의 조건은 left에 있는 값이 right에 있는 값보다 작을때만 진행 가능하다.
    // 같거나 right보다 left값이 크다면 이미 다 확인 한 것.
    while (leftValue <= rightValue) {
      // long midValue = (leftValue + rightValue) / 2;
      long midValue = leftValue + (rightValue - leftValue) / 2;

      if (camMakeInBudget(midValue, maxBudget, a)) {
        // max 예산 안에 만들 수 있는 최대의 최소 성능이다
        // maxValueForMin성능 갱신하고
        // 더 오른쪽에서 찾아보자.
        maxValueForMin성능 = midValue;
        leftValue = midValue + 1;
      } else {
        // max 예산 안에 만들 수 없는 최대의 최소 성능이므로
        // 더 왼쪽에서 찾아보자.
        rightValue = midValue - 1;
      }

    }

    bw.write(String.valueOf(maxValueForMin성능));
    bw.flush();
    bw.close();
  }
  private static boolean camMakeInBudget(long minValue, long maxBudget, long[] a) {
    long cost = 0;

    for (int idx = 0; idx < a.length; idx++) {
      if (a[idx] >= minValue) {
        // 내가 만들려는 최소의 값보다 크거나 같으면 예산 필요없음. 이후에 더 볼 거 없음.
        break;
      }

      long diff = minValue - a[idx];
      cost += diff * diff;
      if (cost > maxBudget) return false;  // 예산을 넘어가는 cost일 경우 만들 수 없다.
    }
    return true; // 위에서 끝나지 않으면 만들 수 있는 최소의 성능인 것 true return
  }
}