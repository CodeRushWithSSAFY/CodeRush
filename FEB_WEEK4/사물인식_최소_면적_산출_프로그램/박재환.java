package 사물인식_최소_면적_산출_프로그램;

import java.util.*;
import java.io.*;

// Softeer Lv.3
// [HSAT 2회 정기 코딩 인증평가 기출] 사물인식 최소 면적 산출 프로그램
// https://softeer.ai/practice/6277
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        init();

        
    }

    static int[][] map;  // 좌표를 2차원 배열로 표현
    static List<int[]>[] colorDots;    // 각 색에 대한 점의 정보를 정보
    static int dotNums, colorNums;  // 점의 개수, 색상의 종류
    static int minArea; // 최소 면적
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        dotNums = Integer.parseInt(st.nextToken());
        colorNums = Integer.parseInt(st.nextToken());

        map = new int[41][41];  // 여유롭게 좌표 설정 (-20,-20) -> (20,20)
        // 1-base 배열 사용
        colorDots = new ArrayList[colorNums+1];
        for(int colorDot=0; colorDot<=colorNums; colorDot++) {
            colorDots[colorDot] = new ArrayList<>();
        }

        for(int color=0; color<dotNums; color++) {
            st = new StringTokenizer(br.readLine().trim());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int colorNo = Integer.parseInt(st.nextToken());

            map[y+20][x+20] = colorNo;
            // 각 색상의 점 정보를 저장한다.
            colorDots[colorNo].add(new int[] {x,y});
        }

        minArea = Integer.MAX_VALUE;

        findMinArea(1,-1000,-1000,1000,1000);

        bw.write(String.valueOf(minArea));
        bw.flush();
        bw.close();
        br.close();
    }

    /*
    최소 면적을 찾는다
    (현재 탐색중인 색상 idx, x의 최대, y의 최대, x의 최소, y의 최소)
     */
    private static void findMinArea(int colorIdx, int maxX, int maxY, int minX, int minY) {
        // 현재 면적이 최소 면적보다 작으면 종료
        // 더 이상 탐색할 가치가 없다
        if(minArea < (maxX-minX) * (maxY-minY)) {
            return;
        }

        // 모든 색상을 탐색한 경우
        if(colorIdx == colorNums+1) {
            // 최소값을 갱신한다.
            minArea = Math.min(minArea, (maxX-minX) * (maxY-minY));
            return;
        }

        // 더 탐색할 색상이 남아있다면
        for(int[] point : colorDots[colorIdx]) {
            int nMaxX = Math.max(maxX, point[0]);
            int nMaxY = Math.max(maxY, point[1]);
            int nMinX = Math.min(minX, point[0]);
            int nMinY = Math.min(minY, point[1]);

            // 다음 탐색을 진행한다.
            findMinArea(colorIdx+1, nMaxX, nMaxY, nMinX, nMinY);
        }
    }
}

/*
    인식된 정도는 평면에 N 개의 점으로 주어진다
    각 점들을 K 개의 색 중 하나를 가진다.

    주어진 K 개의 색에 대해 {1,2,3,...,K}
    해당 색깔을 가지는 점들을 적어도 하나씩 포함하는 사물 중
    넓이가 가장 작은 것을 찾아 반환한다. ( 직사각형 )

    내부와 경계 모두 직사각형으로 포함
    가로와 세로가 0이되어 선분 혹은 점으로 나타나는 경우도 직사각형으로 인정 -> 넓이 0

    제약조건
    1 ≤ N ≤ 10
    1 ≤ K ≤ 20
    -1,000 ≤ x, y ≤ 1,000
    -1,000 ≤ x, y ≤ 1,000 -> [2000][2000]????
    1 ≤ k ≤ K
  */