import java.io.*;
import java.util.*;

/**
 성능이 가장 낮은 컴퓨터의 성능으로 가능한 최댓값 구하기
 */
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int computerCnt;
    static long totalCost;

    // midPower가 최저성능이 될 때 필요한 비용 구하는 함수
    public static long getCost(long[] computerPower, long midPower) {
        long cost = 0;
        for (int i = 0; i < computerCnt; i++) {
            if (computerPower[i] >= midPower) break;
            cost += (midPower - computerPower[i]) * (midPower - computerPower[i]);
            if (cost > totalCost) return cost;
        }
        return cost;
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        computerCnt = Integer.parseInt(st.nextToken());
        totalCost = Long.parseLong(st.nextToken());

        long[] computerPower = new long[computerCnt];
        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < computerCnt; index++) {
            computerPower[index] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(computerPower);

        // 최소 성능 값으로 이분 탐색
        long left = computerPower[0];
        // 업그레이드 할 수 있는 최댓값
        // -> 성능을 d만큼 향상시키는 데 d의 제곱이 들기 때문에 총비용의 제곱근 만큼이 최대 낼 수 있는 성능
        long right = computerPower[computerCnt - 1] + (long)Math.sqrt(totalCost);
        long result = 0;
        while (left <= right) {
            long mid = (left + right) / 2;

            long needCost = getCost(computerPower, mid);
            if (needCost > totalCost)
                right = mid - 1;
            else if (needCost <= totalCost) {
                result = mid;
                left = mid + 1;
            }
        }

        System.out.println(result);
    }
}
