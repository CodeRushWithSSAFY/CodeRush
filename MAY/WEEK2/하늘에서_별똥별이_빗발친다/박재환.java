package MAY.WEEK2.하늘에서_별똥별이_빗발친다;

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
    static int width, height;   // 가로, 세로
    static int trampolineLen;   // 트램펄린 길이
    static int starCnt;         // 별의 개수
    static int[][] stars;       // 별의 위치
    static int maxStar;         // 최대 별의 개수
    static void init() throws IOException {
        maxStar = Integer.MIN_VALUE;

        st = new StringTokenizer(br.readLine().trim());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());
        trampolineLen = Integer.parseInt(st.nextToken());
        starCnt = Integer.parseInt(st.nextToken());
        stars = new int[starCnt][];
        for(int i=0; i<starCnt; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            stars[i] = new int[] {x,y};
        }
        Arrays.sort(stars, (a,b)->{
            if(a[0]==b[0]) return a[1]-b[1];
            return a[0]-b[0];
        });
        defenseMaxStar();
        System.out.println(starCnt-maxStar);
    }
    /*
        width, height 가 최악의 경우 50만 * 50만
        => 배열로 접근 X
        => 완탐 X

        별의 개수는 최대 100 개 -> 이거 이용해야할듯?
        사각형을 만들기 위해서 필요한거
        1. 4개 좌표? 100 C 4 -> 3921225
            -> 비스듬하게 놓일 수 없어서 복잡할듯
        2. 2개 좌표? 100 C 2 -> 4950
            -> 이게 되나
            minX, minY, maxX, maxY 로 사각형 구하기?
            근데 정사각형만 가능
            노노 안됨.. 모든 경우를 못 봄
     */
    static void defenseMaxStar() {
        /*
            각 좌표를 기준으로 x, y 를 뽑음
         */
        for(int i=0; i<starCnt; i++) {
            for(int ii=0; ii<starCnt; ii++) {
                /*
                    두 좌표를 이용해, 좌상단 꼭짓점을 만든다.
                    이후 트램펄린 길이를 더해, 우하단 꼭짓점을 만든다.
                 */
                int x1 = stars[i][0];
                int y1 = stars[ii][1];
                int x2 = x1+trampolineLen;
                int y2 = y1+trampolineLen;

                int count = 0;
                for(int[] star : stars) {
                    int x = star[0];
                    int y = star[1];
                    // 해당 범위 내에 있는지 확인
                    if(x >= x1 && y >= y1 && x <= x2 && y <= y2){
                        count++;
                    }
                }
                maxStar = Math.max(maxStar, count);
            }
        }
    }
}

/*
    N(가로) x M(세로) 의 영역에 별똥별이 떨어진다.
    L x L 크기의 트램펄린을 놓아, 최대한 많은 별똥별을 받아내야한다. -> 포함되지 않는 별의 개수를 반환

    트램펄린을 비스듬하게 배치할 수 없다.
 */
