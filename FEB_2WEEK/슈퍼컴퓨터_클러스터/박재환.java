package FEB_2WEEK.슈퍼컴퓨터_클러스터;

import java.util.*;
import java.io.*;

// Softeer Lv.3
// [HSAT 4회 정기 코딩 인증평가 기출] 슈퍼컴퓨터 클러스터
// https://softeer.ai/practice/6252
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static long budget;
    static int computerNum;
    static long[] computers;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        init(); // 입력

        박재환 problem = new 박재환();
        bw.write(String.valueOf(problem.getMax()));
        bw.flush();
        bw.close();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        computerNum = Integer.parseInt(st.nextToken());
        budget = Long.parseLong(st.nextToken());
        computers = new long[computerNum];
        st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<computerNum; idx++) {
            computers[idx] = Long.parseLong(st.nextToken());
        }
        br.close();
    }

    private long getMax() {
        long answer = Long.MIN_VALUE;

        Arrays.sort(computers); // 이분 탐색을 위해 배열을 정렬한다.

        long min = computers[0];    // 가장 낮은 성능
        long max = computers[computerNum-1] + (long)Math.sqrt(budget);  // 가장 높은 성능을 최대로 끌어올린값

        while(min <= max) {
            long mid = min+(max-min)/2;    // 혹시 모를 오버플로우 방지

            if(upgrade(mid)) {
                answer = Math.max(mid, answer);
                min = mid+1;
            } else {
                max = mid-1;
            }
        }
        return answer;
    }

    private boolean upgrade(long mid) {
        long totalCost = 0;

        for(long power : computers) {
            if(power >= mid) {  // 오름차순 정렬 -> 더 이상 탐색할 필요 없음
                break;
            }

            totalCost += (long)Math.pow(mid-power, 2);
            if(totalCost > budget) return false;    // 예산을 초과하면 false
        }

        return true;
    }
}
/*
N 개의 컴퓨터가 있고, 각 컴퓨터의 성능이 주어진다.
성능을 d 만큼 향상시키는데 드는 비용은 d^2 이다.

업그레이드는 하지 않아도 되지만, 업그레이드는 최대 한 번이다.

업그레이드를 위한 예산 B 원이 책정되어있다. B원 이하의 금액으로 업그레이드를 수행하며,
성능이 가장 낮은 컴퓨터의 성능을 최대화 하여라

-> 예산을 효율적으로 사용했을 때, 성능이 가장 낮은 컴퓨터의 성능으로 가능한 최대값을 출력해라

제약조건
1≤N≤10^5인 정수
1≤a[i]≤10^9인 정수
1≤B≤10^18인 정수

1. 이분탐색을 통해 범위를 구하기 -> 정렬필요 nlogn -> 25
   a. 성능의 중간값을 기준으로 한다.
        i. 기준보다 낮은 값을 모두 중간값으로 맞출 수 있다면 탐색 범위를 늘린다.
        ii. 맞출 수 없다면 탐색 범위를 줄인다.
=> 중간값을 어떻게 할 것인가?
최소 : min(배열)
최대 : max(배열) + sqrt(d) -> 가장 높은 성능의 컴퓨터를 최대로 업그레이드 했을 때가 가장 큰 범위

ex)
4 10
5 5 6 1
-> 1 5 5 6 -> mid 2 = 5 -> 1, 5

 */