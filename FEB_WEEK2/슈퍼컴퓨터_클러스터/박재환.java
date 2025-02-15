import java.io.*;
import java.util.*;


public class Main {
    static BufferedReader br;
    static BufferedWriter bw;
    static long budget;
    static int computerNum;
    static long[] computers;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        init(); // 입력

        Main problem = new Main();
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
