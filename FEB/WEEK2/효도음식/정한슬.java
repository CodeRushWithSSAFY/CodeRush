import java.io.*;
import java.util.*;

/*
    총 음식 2개이고 각 음식의 재료는 인접하면 안되므로 인접되지 않도록 최소 공간 1개를 띄워야한다.
    -> 공간을 기준을 잡아서 앞 -> / 뒤 <-로 최대값을 찾아보자 ??
    공간의 기준은 index가 0 base일 때 1부터 N - 2이다.. 왜냐? 재료는 최소 1개이상 써서 요리를 만들어야 하기 때문에.
*/
public class Main {

  public static void main(String[] args)  throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    int ingredientsCount = Integer.parseInt(br.readLine().trim());
    String[] ingredientsStringSplit = br.readLine().trim().split(" ");
    int[] ingredients = new int[ingredientsCount];
    for (int idx = 0; idx < ingredientsCount; idx++) {
      ingredients[idx] = Integer.parseInt(ingredientsStringSplit[idx]);
    }

    br.close();
    if (ingredientsCount == 3) {
      // 재료가 3개밖에없으면 무조건 재료 각 1개씩 만들어야함..
      System.out.println(ingredients[0] + ingredients[2]);
      return;
    }

    int[] firstMenu = new int[ingredientsCount];
    int[] secondMenu = new int[ingredientsCount];
    // 앞 -> 뒤에서 만드는 첫번째 메뉴의 재료의 최대 합을 배열에 저장해둔다
    // i번째에 저장되는 것은 i번째 재료를 사용할 수 있을 때 최대의 값이다.
    // 4, -6, 1, 2, ... 에서 2인 idx=3까지 쓸 수 있다해도 4가 최대값인 것 처럼 0 -> idx로 진행될 때 최대값,,
    // -6/ 4 + (-6) -> -2: 합치는게 더 값이 크다. | 그러나 첫번째 재료 4만 선택하는게 더 이득이다.
    // 1/ -2 + 1 -> 1 : 이전 재료들은 버리고 현재 재료만 선택하는게 이득이다. | 그러나 마찬가지로 첫번째 재료 4만 선택하는게 더 이득이다.
    // -> 즉 연속된 합의 최대값이 아니라 어디선가 단절된 상황이라 해도 idx 재료 안에서는 그게 최대값이다.. 그러니까 투포인터나 슬라이딩 윈도우 개념에 집착 X
    int kadaneValue = ingredients[0];
    firstMenu[0] = ingredients[0];
    for (int idx = 1; idx < ingredientsCount - 2; idx++) {
      // 어차피 첫번째 메뉴는 뒤의 재료 2개를 사용 못한다.
      kadaneValue = Math.max(kadaneValue + ingredients[idx], ingredients[idx]); // 이전 카데인 값과 현재 재료 값을 비교해서 현재 값이 더 크면 현재 재료로만 선택
      firstMenu[idx] = Math.max(firstMenu[idx - 1], kadaneValue); // 이전의 총 재료의 max 값과 카데인 값과 비교해서 큰 값으로만 갱신 후 해당 재료만 선택된 상황 유지
    }

    // 뒤 -> 앞으로 만드는 두번째 메뉴도 재로의 최대합을 저장한다.
        /* ...1 2 -2 3 에서도 n-1 -> idx로 옮겨질 때
            재료의 합의 최대값은
            -2/ 3+ (-2) -> 1: kadane값 연속으로 합치는게 더 값이 크다. | 그러나 맨 마지막 값인 3으로만 선택하는게 현재는 이득이다
            2/ 1+2 -> 3 : kadane 값 갱신으로 봤을 때 연속으로 합치는게 더 값이 크다 | 맨 마지막 값 3만 선택하는 것과 여기까지 계속 합하는 것과 같다.
            1/ 3+1 -> 4: kadane 값 갱신으로 봤을 때 연속으로 합치는게 더 값이 크다 | 맨 마지막 값 3만 선택하는 것보다 연속으로 여기까지 합하는게 더 이득이다. 갱신.
        */
    kadaneValue = ingredients[ingredientsCount - 1];
    secondMenu[ingredientsCount - 1] = ingredients[ingredientsCount - 1];
    for (int idx = ingredientsCount - 2; idx > 1; idx--) {
      // 어차피 두번째 메뉴는 앞의 재료 2개를 사용 못한다.(0, 1)
      kadaneValue = Math.max(kadaneValue + ingredients[idx], ingredients[idx]); // 이전 카데인 값과 현재 재료 값을 비교해서 현재 값이 더 크면 현재 재료로만 선택
      secondMenu[idx] = Math.max(secondMenu[idx + 1], kadaneValue); // 이전의 총 재료의 max 값과 카데인 값과 비교해서 큰 값으로만 갱신 후 해당 재료만 선택된 상황 유지
    }

    // 최대값인 값으로 갱신한다.
    // 극단적으로 합해도 100,000,000이라 Integer range로 가능
    int maxSum = Integer.MIN_VALUE;
    for (int spaceIdx = 1; spaceIdx < ingredientsCount - 2; spaceIdx++) {
      // 비워야하는 공간을 기준으로 첫번째 메뉴, 두번째 메뉴의 합의 최대를 갱신한다
      maxSum = Math.max(maxSum, firstMenu[spaceIdx - 1] + secondMenu[spaceIdx + 1]);
    }

    bw.flush();
    bw.write(String.valueOf(maxSum));
    bw.close();
  }

  // 집착,,
  // private static long makeMenu(int spaceIdx, int[] ingredients) {
  //     // 비워야하는 공간 idx 이전 재료 값들에 대해서
  //     // firstMenu로 만들 수 있는데 이때 firstMenuSatisfy를 최대값으로 만들어야한다.
  //     long firstMenuSatisfy = 0;
  //     // 0 ~ spaceIdx - 1의 배열에서 최댓값의 합 구하기
  //     // 1. 슬라이딩 윈도우 ??
  //     // 2. 투포인터 ??
  //     // 1과 2는 구간이 정해져있을 때하기 편한 듯?
  //     // 3. 카데인 알고리즘
  //     long maxCurSum = ingredients[0];
  //     long maxTotalSum = ingredients[0];
  //     for (int idx = 1; idx < spaceIdx; idx++) {
  //         maxCurSum = Math.max(ingredients[idx], maxCurSum + ingredients[idx]);
  //         maxTotalSum = Math.max(maxTotalSum, maxCurSum);
  //     }
  //     firstMenuSatisfy = maxTotalSum;

  //     // 비워야하는 공간 idx 이후 재료 값들에 대해서
  //     // secondMenu로 만들 수 있는데 이때 secondMenuSatisfy 최대값으로 만들어야한다.
  //     // spaceIdx + 1 ~ N - 1(배열의 길이 N)의 배열에서 최댓값의 합 구하기
  //     long secondMenuSatisfy = 0;
  //     maxCurSum = ingredients[spaceIdx + 1];
  //     maxTotalSum = ingredients[spaceIdx + 1];
  //     for (int idx = spaceIdx + 2; idx < ingredients.length; idx++) {
  //         maxCurSum = Math.max(ingredients[idx], maxCurSum + ingredients[idx]);
  //         maxTotalSum = Math.max(maxTotalSum, maxCurSum);
  //     }
  //     secondMenuSatisfy = maxTotalSum;

  //     return firstMenuSatisfy + secondMenuSatisfy;
  // }
}