package 통근버스_출발순서_검증하기;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int arrSize;
    static int[] bus;
    public static void main(String[] args) throws IOException {
        박재환 problem = new 박재환();

        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 수열의 크기
        arrSize = Integer.parseInt(br.readLine().trim());
        bus = new int[arrSize];

        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<arrSize; idx++) {
            bus[idx] = Integer.parseInt(st.nextToken());
        }
        br.close();

        bw.write(String.valueOf(problem.findTriplePair()));
        bw.flush();
        bw.close();

    }

    // 문제 조건
    // a[i] < a[j] && a[i] > a[k]
    // 5000 * 5000 * 5000 -> 5000^3 -> 1250억?
    // 시간 초과
    public int bruteForce() {
        int answer = 0;

        for(int i=0; i<arrSize; i++) {
            for(int j=i+1; j<arrSize; j++) {
                if(bus[i] > bus[j]) continue;

                for(int k=j+1; k<arrSize; k++) {
                    if(bus[i] > bus[k]) answer++;
                }
            }
        }

        return answer;
    }

    public long findTriplePair() {
        // ⚠️ 자료형 주의 int X -> long
        long answer = 0;

        // 1. 탐색 수 줄이기
        // i, j, k 총 3개의 원소가 필요
        // arrSize - 2 까지만 탐색
        for(int i=0; i<arrSize-2; i++) {
            // 각 조합을 기록할 리스트
            List<Integer> list = new ArrayList<>();

            // 조건 대입
            for(int j=i+1; j<arrSize; j++) {
                if(bus[i] < bus[j]) {   // 1번 조건 ai < aj 만족
                    list.add(1);
                } else {    // 1번 조건 만족 X -> 즉 2번 조건 ai > ak 만족
                    list.add(0);
                }
            }

            int satisfy = 0;
            for(int s : list) {
                if(s == 1) satisfy++;   // 1번 조건을 만족하는 개수를 세어준다. (여기서 이미 2쌍을 구할 수 있다 i,j)
                else answer += satisfy; // 2 (i,j) 와 만들 수 있는 k 의 개수를 세어 정답에 카운트해준다.
            }
        }

        return answer;
    }
}
