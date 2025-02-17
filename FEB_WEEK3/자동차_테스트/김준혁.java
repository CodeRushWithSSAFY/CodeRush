import java.io.*;
import java.util.*;

/**

타겟 중앙값의 경우의 수 : (더 작은 값 개수) * (더 큰 값 개수)
- * 결국 더 작은 값과 더 큰 값을 어떻게 셀 것인지가 관건 *
- 이것 저것 여러번 시도해봤는데, 결국 차 배열을 오름차순 정렬하고 인덱스를 가지고 알아내는게 가장 빨랐다
    - 정렬만 끝나면 인덱스로 더 작은/더 큰 값을 찾는 것은 굉장히 빠르기 때문인 듯
    - n의 값도 최대 50,000으로 엄청 크지는 않아서 정렬하는데에 시간이 많이 안 들었나 보다

연비가 최대 10억이라 혹시 몰라서 전체적인 데이터 타입을 long으로 함
- long 타입에 익숙하지 않아서 에러가 많이 났다 -> gpt와 대화로 해결

*/

public class 김준혁 {

    public static int carNum;        // 자동차 개수
    public static int questionNum;   // 질의 개수
    public static long targetMid;    // 목표 중앙값

    public static Long[] carList;    // 차 배열
    
    public static Map<Long, Long> smallerNumMap = new HashMap<>();  // 더 작은 값의 개수 보관
    public static Map<Long, Long> biggerNumMap = new HashMap<>();   // 더 큰 값의 개수 보관

    public static long result;       // 결과값
    

    public static void main(String[] args) throws IOException{

        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        carNum = Integer.parseInt(st.nextToken());
        questionNum = Integer.parseInt(st.nextToken());

        carList = new Long[carNum];

        st = new StringTokenizer(br.readLine().trim());
        for(int idx = 0; idx < carNum; idx++) {
            carList[idx] = Long.parseLong(st.nextToken());
        }

        // ======================== 핵심 : 더 작은/더 큰 값의 개수 세기 ===========================
        
        // 정렬
        Arrays.sort(carList);

        for(int carIdx = 0; carIdx < carNum; carIdx++){
            Long currCar = carList[carIdx];

            // 더 적은 차의 개수 == 현재 차의 인덱스
            smallerNumMap.put(currCar, (long)carIdx);

            // 더 큰 차의 개수 == 전체 차 개수 - 현재 차의 인덱스 - 1 (1은 현재 차까지 빼주기 위함)
            biggerNumMap.put(currCar, (long)(carNum - carIdx - 1));
        }

        
        // ========================= 정답 계산 및 출력 =============================

        
        for(int questionIdx = 0; questionIdx < questionNum; questionIdx++){

            // 타겟 중앙값 입력받기
            targetMid = Long.parseLong(br.readLine().trim());

            // smallerNumMap에 targetMid값이 있다면 biggerNumMap에도 무조건 있음 -> 하나만 확인해도 됨
            if(smallerNumMap.containsKey(targetMid)) {
                // 전체 경우의 수 : 더 적은 차의 개수 * 더 큰 차의 개수
                result = smallerNumMap.get(targetMid) * biggerNumMap.get(targetMid);
            }
            else {
                result = 0;   // targetMid가 없다면 결과는 0
            }

            // 결과 출력  
            System.out.println(result);
        }
        
    }
}

