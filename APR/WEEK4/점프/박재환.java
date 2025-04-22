package WEEK4.점프;

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
    static int mapSize;
    static int[][] map;
    static void init() throws IOException {
        mapSize = Integer.parseInt(br.readLine().trim());
        map = new int[mapSize][mapSize];

        for(int x=0; x<mapSize; x++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int y=0; y<mapSize; y++) {
                map[x][y] = Integer.parseInt(st.nextToken());
            }
        }

        // 입력 확인
//        for(int[] arr : map) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println();
        findAllRoute();
    }

    /*
        탐색 방향이 오른쪽, 아래쪽으로 탐색한다.
        각 칸에 적힌 수 만큼 점프를 할 수 있다.
        좌측과 상단에 마진을 두고 DP 를 탐색한다.
     */
    static void findAllRoute() {
        long[][] dpArr = new long[mapSize][mapSize];
        dpArr[0][0] = 1;
        for(int x=0; x<mapSize; x++) {
            for(int y=0; y<mapSize; y++) {
                int jump = map[x][y];

                // 점프를 할 수 없다면 해당 경로는 제외한다.
                if(map[x][y] == 0) continue;
                
                // 점프를 할 수 있는지
                if (x + jump < mapSize) {
                    dpArr[x + jump][y] += dpArr[x][y];
                }
                if (y + jump < mapSize) {
                    dpArr[x][y + jump] += dpArr[x][y];
                }
            }
        }

//        for(int[] arr : dpArr) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println();

        System.out.println(dpArr[mapSize-1][mapSize-1]);
    }
}