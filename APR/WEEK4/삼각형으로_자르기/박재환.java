package WEEK4.삼각형으로_자르기;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static StringTokenizer st;
    static int pointCnt;        // 주어지는 다각형 점의 수
    static int[][] points;      // 각 다각형의 점을 저장
    static double maxValue;        // 최대 넓이
    static void init() throws IOException {
        maxValue = 0;
        pointCnt = Integer.parseInt(br.readLine().trim());
        points = new int[pointCnt][2];

        for(int i=0; i<pointCnt; i++) {
            st = new StringTokenizer(br.readLine().trim());
            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
        }
        pointsComb(0,0, new int[3][2]);
        System.out.println(maxValue);
    }

    /*
        점의 개수가 35 개
        35 개 중 3 개를 뽑는 경우의 수 35!/(3!(35-3)!)
        => 35 * 34 * 33 / 3 * 2 * 1 = 6545 가지밖에 없다.
        조합으로 3개의 점을 구한다.
     */
    static void pointsComb(int pointIdx, int selectIdx, int[][] comb) {
        if(selectIdx == 3) {    // 모든 조합을 구한 경우
            // 넓이를 구한다.
            calcTriangle(comb);
            return;
        }
        // 더 이상 탐색이 불가
        if(pointIdx == pointCnt) return;

        // 해당 점을 선택하거나, 하지 않는다.
        comb[selectIdx] = points[pointIdx];
        pointsComb(pointIdx+1, selectIdx+1, comb);
        pointsComb(pointIdx+1, selectIdx, comb);
    }

    /*
        📌 세 점으로 삼각형의 넓이 구하기
            파생 공식 이용하기
            https://m.blog.naver.com/eandimath/221760895905
     */
    static void calcTriangle(int[][] comb) {
        int[] a = comb[0];
        int[] b = comb[1];
        int[] c = comb[2];

        double result = Math.abs(a[0]*b[1] + b[0]*c[1] + c[0]*a[1]
                - b[0]*a[1] - c[0]*b[1] - a[0]*c[1]) / 2.0;

        maxValue = Math.max(result, maxValue);
    }
}