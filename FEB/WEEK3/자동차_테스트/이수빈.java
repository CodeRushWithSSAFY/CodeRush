import java.io.*;
import java.util.*;

/*
    자동차 테스트

    n대의 자동차 중 3대만
    3대 골라서 중앙값이 mi가 나오는 서로 다른 경우의 수 구하기
*/
public class 이수빈 {
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        int carCnt = Integer.parseInt(st.nextToken());
        int questions = Integer.parseInt(st.nextToken());

        int[] cars = new int[carCnt];
        st = new StringTokenizer(br.readLine().trim());
        for (int i = 0; i < carCnt; i++) cars[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(cars);

        for (int q = 0; q < questions; q++) {
            int goalMid = Integer.parseInt(br.readLine().trim()); // 목표 중앙값
            int goalIdx = -1; // 목표 중앙값의 인덱스

            // 연비가 goalMid인 차의 위치(인덱스) 찾기
            int left = 0;
            int right = carCnt - 1;
            while (left <= right) {
                int mid = (left + right) / 2;

                if (cars[mid] == goalMid) {
                    goalIdx = mid;
                    break;
                }

                if (cars[mid] > goalMid) {
                    right = mid - 1;
                }

                if (cars[mid] < goalMid) {
                    left = mid + 1;
                }
            }
            
            int ans = 0;
            if (goalIdx != -1) { // 목표 중앙값을 가진 차가 있는 경우
                ans = goalIdx * (carCnt - goalIdx - 1);
            }

            System.out.println(ans);
        }
    }
}
