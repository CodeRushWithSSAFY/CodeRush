import java.io.*;
import java.util.*;

/*
    총 음식 2개이고 각 음식의 재료는 인접하면 안되므로 인접되지 않도록 최소 공간 1개를 띄워야한다.
    -> 공간을 기준을 잡아서 앞 -> / 뒤 <-로 최대값을 찾아보자 ??
    공간의 기준은 index가 0 base일 때 1부터 N - 2이다.. 왜냐? 재료는 최소 1개이상 써서 요리를 만들어야 하기 때문에.
*/
public class 박재환 {
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

        // ⚠️ 해당 부분은
        // 비워야하는 공간의 idx를 기준으로 첫번째 메뉴와 두번째 메뉴의 최대값의 합을 구한 뒤
        // 최대값인 값으로 갱신한다.
        long maxSum = Long.MIN_VALUE;
        for (int spaceIdx = 1; spaceIdx < ingredientsCount - 1; spaceIdx++) {
            maxSum = Math.max(maxSum, makeMenu(spaceIdx, ingredients));
        }

        bw.flush();
        bw.write(String.valueOf(maxSum));
        bw.close();
    }
    private static long makeMenu(int spaceIdx, int[] ingredients) {
        // 비워야하는 공간 idx 이전 재료 값들에 대해서
        // firstMenu로 만들 수 있는데 이때 firstMenuSatisfy를 최대값으로 만들어야한다.
        long firstMenuSatisfy = 0;
        // 0 ~ spaceIdx - 1의 배열에서 최댓값의 합 구하기
        // 1. 슬라이딩 윈도우 ??
        // 2. 투포인터 ??
        // 1과 2는 구간이 정해져있을 때하기 편한 듯?
        // 3. 카데인 알고리즘
        long maxCurSum = ingredients[0];
        long maxTotalSum = ingredients[0];
        for (int idx = 1; idx < spaceIdx; idx++) {
            maxCurSum = Math.max(ingredients[idx], maxCurSum + ingredients[idx]);
            maxTotalSum = Math.max(maxTotalSum, maxCurSum);
        }
        firstMenuSatisfy = maxTotalSum;

        // 비워야하는 공간 idx 이후 재료 값들에 대해서
        // secondMenu로 만들 수 있는데 이때 secondMenuSatisfy 최대값으로 만들어야한다.
        // spaceIdx + 1 ~ N - 1(배열의 길이 N)의 배열에서 최댓값의 합 구하기
        long secondMenuSatisfy = 0;
        maxCurSum = ingredients[spaceIdx + 1];
        maxTotalSum = ingredients[spaceIdx + 1];
        for (int idx = spaceIdx + 2; idx < ingredients.length; idx++) {
            maxCurSum = Math.max(ingredients[idx], maxCurSum + ingredients[idx]);
            maxTotalSum = Math.max(ma환xTotalSum, maxCurSum);
        }
        secondMenuSatisfy = maxTotalSum;

        return firstMenuSatisfy + secondMenuSatisfy;
    }
}
