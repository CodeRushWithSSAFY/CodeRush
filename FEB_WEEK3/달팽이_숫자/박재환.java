package 달팽이_숫자;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder sb = new StringBuilder();
        int TC = Integer.parseInt(br.readLine().trim());
        for(int testCase=1; testCase <= TC; testCase++) {
            sb.append('#').append(testCase).append('\n');

            // 입력
            init();

            snail();
            sb.append(printSnail());
        }



        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int mapSize;	// N * N 사이즈
    static int[][] map;	// 달팽이 숫자를 출력할 배열
    private static void init() throws IOException {
        mapSize = Integer.parseInt(br.readLine().trim());
        map = new int[mapSize][mapSize];
    }

    // 진행 방향은 오른쪽, 아래, 왼쪽, 위
    /*
     * dir
     * 0 : 오른쪽
     * 1 : 아래
     * 2 : 왼쪽
     * 3 : 위
     */
    private static int[] dx = {0,1,0,-1};
    private static int[] dy = {1,0,-1,0};
    private static void snail() {
        int number = 1;	// 1부터 채워넣는다.

        int x=0, y=0;	// 현재의 위치를 나타낸다
        int dir = 0;	// 숫자를 채워넣을 방향을 기록한다.
        while(number <= mapSize*mapSize) {	// 모든 숫자를 채워넣을때까지 반복한다.
            // 현위치에 값을 넣어준다
            map[x][y] = number++;

            // 다음으로 이동할 위치
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // 만약 다음 위치가 map 의 범위를 벗어난다면
            // 혹은 이미 값을 넣은 곳이라면
            // 방향을 전환 시켜주어야한다.
            if(nx < 0 || ny < 0 || nx >= mapSize || ny >= mapSize || map[nx][ny] > 0) {
                dir = (dir+1)%4;	// 방향을 전환해준다.
                // 다음 이동 위치를 새로 갱신해준다.
                nx = x + dx[dir];
                ny = y + dy[dir];
            }
            // 현 위치를 갱신한다.
            x = nx;
            y = ny;
        }
    }

    private static String printSnail() {
        StringBuilder sb = new StringBuilder();
        for(int[] numbers : map) {
            for(int number : numbers) {
                sb.append(number).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
