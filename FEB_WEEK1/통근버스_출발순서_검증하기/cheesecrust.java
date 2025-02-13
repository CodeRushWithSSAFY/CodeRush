import java.io.*;
import java.util.*;

/*
 * 누적합
 * 초기에는 조합을 시도 했으나 n^3 으로 실패
 * 3 n^2 도 실패
 * n^2 으로 해결해야합니다.
 * 첫번째 조건 (i < j)을 만족시키는 수를 센 후 두 번째 조건(k < i) 을 만족시키는 순간 모두 더함
 * 
 * 4 2 5 3 1
 * 4 에서 출발하여 5를 만나면 cnt 1 증가 후 4 보다 작은 3, 1을 만나면 answer 을 cnt 만큼 증가 
 * 2 에서 출발하여 5 3 을 만나면서 1씩 증가 시킨 후 1을 만나는 순간 answer에 2 증가 (2,5,1) (2,3,1)에 해당하는 경우
 */
public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int[] busArr;
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine().trim());
        int N = Integer.parseInt(st.nextToken());
        busArr = new int[N];

        st = new StringTokenizer(br.readLine().trim());
        for (int i = 0; i < N; i++) {
            busArr[i] = Integer.parseInt(st.nextToken());    
        }

        long answer = 0;
        for (int i = 0; i < N; i++) {
            int cnt = 0;
            for (int j = i + 1; j < N; j++) {
                // 첫번째 조건
                if (busArr[i] < busArr[j]) {
                    cnt++;
                } else {
                    // 두번째 조건
                    answer += cnt;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(answer);
        bw.write(sb.toString());
        bw.flush();
    }
}
