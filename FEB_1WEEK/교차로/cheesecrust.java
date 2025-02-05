import java.io.*;
import java.util.*;

/*
 * 1. 시간 별로 차량의 배열을 만든다.
 *  1-1. 모든 시간을 배열로 하기에는 너무 크니 map<Integer, List<>> 활용
 *  1-2. 최대의 시간을 입력 받을때에 저장해 놓는다.
 * 2. 각각의 교차로에 해당하는 큐를 만든다.
 *  2-1. 큐 순회의 단순성을 위해 큐를 하나의 배열로 묶어 놓는다.
 *  2-2. 큐에는 차 클래스를 넣는다.
 * 3. 최대의 시간 동안 for 돌면서 수행
 * 추가: 모두 비었을때도 생각해야합니다.
 *  3-1. 우선 차량 리스트를 돌면서 큐에 추가 -> 첫 입력시 바로 넣는것으로 수정
 *  3-2. A 교차로 부터 오른쪽의 교차로((i+1)%4)가 비었는지 확인하고 빈 경우 해당 차량 answer 배열에 시각 체크
 *  3-3. 중간에 빈 경우 바로 다음 시간으로 넘어가기
 *  3-4. 교착 일경우 바로 출력
 */
class Car{
    public int time;
    public int carIdx;

    Car(int time, int idx) {
        this.time = time;
        this.carIdx = idx;
    }
}

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer st;
    private static List<ArrayDeque<Car>> roadList = new ArrayList<>(4);
    private static int[] answer;

    // 모두 비어있으면 true
    public static boolean isRoadEmpty() {
        return roadList.get(0).isEmpty() && roadList.get(1).isEmpty() && roadList.get(2).isEmpty() && roadList.get(3).isEmpty();
    }

    public static void init() {
        for (int i = 0; i < 4; i++) {
            roadList.add(new ArrayDeque<>());
        }
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        
        answer = new int[N];
        for (int i = 0; i < N; i++) {
            answer[i] = -1;
        }
        init();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            char road = st.nextToken().charAt(0);
            roadList.get(road - 'A').add(new Car(time, i));
        }


        // 차가 아직 다 안 빠져 나간동안 순회
        // 빠져나갈때 마다 수 증가
        // 교착 상태인지 아예 다 비어있는 건지 확인
        int currentTime = -1;
        while (true) {

            // 모든 교차로 비어있으면 나가기
            if (isRoadEmpty()) {
                break;
            }
            
            // 사방 보면서 빈 교차로 찾기
            int minTime = Integer.MAX_VALUE;
            int[] tmp = new int[4];
            for (int i = 0; i < 4; i++) {
                if (!roadList.get(i).isEmpty()) {
                    int t = roadList.get(i).peek().time;
                    minTime = Math.min(t, minTime);
                    if(t <= currentTime)
                        tmp[i] = 1;
                }
            }
            int cnt = 0;
            for (int value : tmp) {
                cnt += value;
            }

            if (cnt == 4) {
                break;
            } else if (cnt == 0) {
                currentTime = minTime;
            } else {
                for(int i = 0; i < 4; ++i){
                    if(tmp[i] != 0 && tmp[(i+3) % 4] == 0){
                        answer[roadList.get(i).poll().carIdx] = currentTime;
                    }
                }
                currentTime += 1;
            }
        }
        for (int i = 0; i < N; i++){
            bw.write(answer[i] + "\n");
        }
        bw.flush();
    }
}
