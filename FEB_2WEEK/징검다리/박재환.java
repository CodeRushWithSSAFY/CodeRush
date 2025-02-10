package 징검다리;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int stoneNum;
    static long[] stones;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        stoneNum = Integer.parseInt(br.readLine().trim());
        stones = new long[stoneNum];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int idx=0; idx < stoneNum; idx++) {

            stones[idx] = Long.parseLong(st.nextToken());
        }
        br.close();

        박재환 problem = new 박재환();
        bw.write(String.valueOf(problem.getMaxStone()));
        bw.flush();
        bw.close();
    }

    private int[] maxStone;
    private int max;
    private int getMaxStone() {
        maxStone = new int[stoneNum];
        Arrays.fill(maxStone, 1);    // 초기값 설정 ( 자기 자신 )
        max = Integer.MIN_VALUE;
        for(int i=0; i<stoneNum; i++) {
            for(int j=0; j<i; j++) {
                if(stones[i] > stones[j]) {  // 기준 값(i) 가 현재값(j) 보다 크다
                    // 최장 증가 부분 수열의 값을 갱신한다.
                    maxStone[i] = Math.max(maxStone[i], maxStone[j]+1);
                }
            }
        }
        return maxValue();
    }

    private int maxValue(){
        for(int value : maxStone) {
            max = Math.max(max, value);
        }
        return max;
    }
}
