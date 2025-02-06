import java.io.*;
import java.util.*;

/**
 교차로

 반시계방향

 각 길 위치마다 큐 할당
 큐에는 차량의 정보(class)가 들어간다.
 초마다 반복문
 -> 4개 큐의 앞부분 time <= 현재 time 이면 통과 가능
 -> 조건에 맞다면 오른쪽에 차량 있는지 확인
 -> 있으면 ,
 -> 없으면 poll 하고 배열의 해당 차량 번호에 현재 time 저장
 -> 교착상태

 */

// 위 오른쪽 아래 왼쪽

class CarInfo {
    int index;
    int enterTime;

    CarInfo(int index, int enterTime) {
        this.index = index;
        this.enterTime = enterTime;
    }
}

public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int ROAD_COUNT = 4;

    public static void main(String[] args) throws IOException {
        // 차 수 입력
        int carCount = Integer.parseInt(br.readLine().trim());

        Queue<CarInfo>[] road = new ArrayDeque[ROAD_COUNT];
        for (int index = 0; index < ROAD_COUNT; index++) {
            road[index] = new ArrayDeque<>();
        }

        // 차 정보 입력 (초, 도로)
        int time = 0;
        for (int index = 0; index < carCount; index++) {
            st = new StringTokenizer(br.readLine().trim());

            int enterTime = Integer.parseInt(st.nextToken());
            int roadIndex = st.nextToken().charAt(0) - 'A'; // A, B, C, D

            CarInfo car = new CarInfo(index, enterTime);
            road[roadIndex].add(car);

            if (index == 0) time = enterTime;

        }

        int[] carPassTime = new int[carCount];
        for (int index = 0; index < carCount; index++) {
            carPassTime[index] = -1;
        }

        boolean[] checkPassRoad = new boolean[ROAD_COUNT];

        // 초마다 반복문
        int nextTime;
        while (true) {
            boolean isAllRoadEmpty = true;
            boolean isDeadLock = true;

            // 시간초과나기 때문에 건너뛰어야함
            nextTime = Integer.MAX_VALUE;

            // ROAD 체크
            for (int position = 0; position < ROAD_COUNT; position++) {
                if (!road[position].isEmpty()) {
                    CarInfo frontCar = road[position].peek();
                    nextTime = Math.min(nextTime, frontCar.enterTime);

                    if (frontCar.enterTime <= time) {
                        // 오른쪽에 차가 있는지 확인
                        int rightPosition = ((position - 1) + 4) % 4;

                        // 아예 비어있거나 / 맨앞의 요소의 시간이 현재 시간보다 클때
                        if (road[rightPosition].isEmpty() || (!road[rightPosition].isEmpty() && road[rightPosition].peek().enterTime > time)) {
                            carPassTime[frontCar.index] = time;
                            checkPassRoad[position] = true;
                        }
                    } else isDeadLock = false; // 비어있지 않고 맨앞의 요소가 현재 시간보다 클 때

                    isAllRoadEmpty = false;

                } else isDeadLock = false; // 비어있을 때
            }

            // 교착상태거나 모든 길이 다 빈 경우
            if (isDeadLock || isAllRoadEmpty) break;


            // 통과할 차가 있는지 확인
            for (int position = 0; position < ROAD_COUNT; position++) {
                // true 이면 pop
                if (checkPassRoad[position]) {
                    road[position].poll();
                }

                // 초기화
                checkPassRoad[position] = false;
            }

            time++;
            if (nextTime > time) {
                time = nextTime; // time 점프
            }
        }

        // 출력
        for (int passTime : carPassTime) {
            System.out.println(passTime);
        }

    }
}
