package WEEK4.기지국_설치;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        init();
        bw.flush();
        bw.close();
    }

    static void init() throws IOException {
        int n = Integer.parseInt(br.readLine().trim());
        List<Integer> list = new ArrayList<>();
        int num;
        while((num=Integer.parseInt(br.readLine().trim())) > 0) {
            list.add(num);
        }
        int[] station = list.stream().mapToInt(i->i).toArray();
        int w = Integer.parseInt(br.readLine().trim());

        br.close();

        bw.write(String.valueOf(new 박재환().solution(n,station,w)));
    }

    /*
        1. 전파가 닿지 않는 영역의 범위를 구한다.
        2. 기지국이 커버할 수 있는 영역은 2*w+1 이다.
        -> 영역을 구해, 영역의 크기와, 커버할 수 있는 구역을 나눈다.
        O(N) -> 2초
        ❌ 정확도 통과, 효율성 시간초과

        ✅ 배열을 사용해 기록하지 말고, 상수 계산을 이용한다.
     */
    public int solution(int n, int[] stations, int w) {
        int coverArea = 2*w+1;
        int point = 1;  // Idx 를 기록한다.
        int answer = 0;

        for(int station : stations) {   // 기지국을 교체한다.
            // 현재 기지국에서 왼쪽으로 전파가 닿는 마지노선을 구한다.
            int end = station-w;
            int range = end-point;    // 전파가 닿지 않는 영역의 크기

            if(range > 0) { // 전파가 중복되는 부분을 구하기 위함
                answer += (range%coverArea==0 ? range/coverArea : range/coverArea+1);
            }
            // 영역의 시작 부분을 이동
            point = station+w+1;
        }


        // 마지막 영역까지 전파가 닿지 않는 경우
        if(point <= n) {
            int range = n-point+1;    // 전파가 닿지 않는 영역의 크기
            answer += (range%coverArea==0 ? range/coverArea : range/coverArea+1);
        }
        return answer;
    }
}

/*
    N 개의 아파트가 일렬로 늘어서 있다.
    일부 아파트의 옥상에는 기지국이 있다.
    기지국을 교체하려 한다. -> 기존 기지국보다 신규 기지국의 전파 범위가 더 적다.

    기지국 교체를 제외, 신규 기지국을 최소한으로 적게 설치하며 모든 범위에 전파하고싶다.

    N 의 최대 범위는 2억 이하 -> 완탐 X
    stations 는 오름차순으로 주어짐
 */
