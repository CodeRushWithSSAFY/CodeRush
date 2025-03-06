package 교차로;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static Queue<int[]>[] roads;    // 도로별 [차량 인덱스, 접근 시간]
    static int cars;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        roads = new Queue[4];
        for(int idx=0; idx<4; idx++) {  // 각 배열에 ArrayDeque 할당
            roads[idx] = new ArrayDeque<>();
        }

        cars = Integer.parseInt(br.readLine().trim());
        for(int i=0; i<cars; i++) { // 차량 입력
            StringTokenizer st = new StringTokenizer(br.readLine().trim());

            int time = Integer.parseInt(st.nextToken());
            int roadNum = st.nextToken().charAt(0) - 'A';

            roads[roadNum].offer(new int[]{i, time});
        }
        br.close();

        StringBuilder sb = new StringBuilder();
        for(int time : moveCar()) {
            sb.append(time).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int[] moveCar() {
        int[] timeSet = new int[cars];
        Arrays.fill(timeSet, -1);   // -1 로 초기값 세팅 ( 교착 상태 )

        int cur = -1;   // 현재 시간을 기록할 변수
        // 모든 교차로에 차량이 없을 때 까지 반복한다.
        while(!roads[0].isEmpty() || !roads[1].isEmpty() || !roads[2].isEmpty() || !roads[3].isEmpty()){
            int[] nowRoad = new int[4];    // 현재 도로에 대기중인 차량을 기록

            int minTime = Integer.MAX_VALUE;

            for(int idx=0; idx<4; idx++) {  // 각 교차로를 확인한다
                if(!roads[idx].isEmpty()) { // 차량이 존재한다면
                    int inTime = roads[idx].peek()[1];  // 해당 차량이 진입한 시간을 확인

                    minTime = Math.min(inTime, minTime);    // 각 교차로의 첫 번째 차량의 진입 시간 중 가장 빠른 시간을 확인

                    if(inTime <= cur) nowRoad[idx]=1;
                }
            }

            int count = Arrays.stream(nowRoad).sum();   // 현재 대기중인 차량의 수

            if(count == 0) {  // 대기중인 차량이 없음
                cur = minTime;
            } else if(count == 4) {
                // 교착상태 -> 더 이상 아무 이동도 할 수 없음
                // -> 탐색 끝
                break;
            } else {
                for(int idx=0; idx<4; idx++) {
                    // 현 위치에 차량이 있고, 우측에는 없는 경우 -> 통과 가능
                    if(nowRoad[idx] == 1 && nowRoad[(idx+3)%4] == 0){
                        timeSet[roads[idx].poll()[0]] = cur;
                    }
                }
                cur++;  // 모든 처리가 끝났다면 시간 증가
            }
        }

        return timeSet;
    }
}
