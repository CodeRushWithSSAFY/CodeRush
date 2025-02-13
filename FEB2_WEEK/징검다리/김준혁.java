package feb2_week_rockBridge;

import java.io.*;
import java.util.*;

/**
* 최장 증가 수열 찾기
*/

public class Main {

    static int rockNum;                  // 바위 개수
    static int max = Integer.MIN_VALUE;  // 최장 길이 저장할 변수
    
    static int[] rocks;       // 바위 높이를 저장할 배열
    static int[] lengths;     // 각 바위까지의 최장 길이를 기록할 배열 
    
    
    public static void main(String[] args) throws IOException{
    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int rockNum = Integer.parseInt(st.nextToken());

        rocks = new int[rockNum];
        lengths = new int[rockNum];
        
        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < rockNum; idx++){
            rocks[idx] = Integer.parseInt(st.nextToken());
        }

        for(int idx = 0; idx < rockNum; idx++){
            for(int j = idx; j < rockNum; j++){

                // 현재 위치보다 높은 바위 발견 -> 새로운 경로와 기존 경로 중 더 긴 경로를 선택
                if(rocks[idx] < rocks[j]) lengths[j] = Math.max(lengths[j], lengths[idx] + 1);

                // 최장 길이 값 업데이트
                max = Math.max(max, lengths[j]);
            }
        }

        // lengths가 0으로 초기화 되어있으므로 +1을 해줘야 정답 
        System.out.println(max + 1);

    }
}

