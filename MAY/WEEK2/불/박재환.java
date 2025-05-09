package MAY.WEEK2.불;

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
    static int row, col;
    static char[][] map;
    static int[] jihun; // 지훈이의 위치
    static Queue<int[]> fire;   // 불 위치
    static void init() throws IOException {
        fire = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine().trim());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());
        map = new char[row][col];

        for(int x= 0; x<row; x++) {
            String input = br.readLine().trim();
            for(int y=0; y<col; y++) {
                map[x][y] = input.charAt(y);

                if(map[x][y] == 'J') jihun = new int[] {x,y};
                else if(map[x][y] == 'F') fire.offer(new int[] {x,y,0});    // [x,y,시간]
            }
        }
        spreadFire();
        jihunRun();
    }

    /*
        지훈이가 도망친다.
        이때 격자의 가장자리로 이동하면 탈출한 것으로 간주한다.
     */
    static int[][] jihunTime;
    static void jihunRun() {
        Queue<int[]> q = new LinkedList<>();
        jihunTime = new int[row][col];
        // 방문 처리
        boolean[][] visited = new boolean[row][col];
        visited[jihun[0]][jihun[1]] = true;
        q.offer(new int[] {jihun[0],jihun[1],0});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int time = cur[2];

            if(x == 0 || y == 0 || x == row-1 || y == col-1) {  // 가장자리로 도착
                System.out.println(time+1);
                return;
            }

            // 발바닥에 불나게 뛰어라
            for(int dir=0; dir<4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                // 격자 내부인지
                if(isMap(nx, ny)) continue;
                // 이미 왔던 곳인지 혹은 벽으로 막혀있는지
                if(visited[nx][ny] || map[nx][ny] == '#') continue;

                // 불이 이미 퍼진 곳인지
                if(fireTime[nx][ny] <= time+1) continue;

                visited[nx][ny] = true;
                q.offer(new int[] {nx, ny, time+1});
            }
        }
        System.out.println("IMPOSSIBLE");
    }

    /*
        불이 각 칸으로 번지는 가장 빠른 시간을 기록한다.
     */
    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};
    static int[][] fireTime;
    static void spreadFire() {
        fireTime = new int[row][col];
        // 배열 초기화
        for(int x=0; x<row; x++) {
            Arrays.fill(fireTime[x], Integer.MAX_VALUE);
        }

        while(!fire.isEmpty()) {
            int[] cur = fire.poll();
            int x = cur[0];
            int y = cur[1];
            int time = cur[2];

            if(fireTime[x][y] < time) continue;

            fireTime[x][y] = time;

            for(int dir=0; dir<4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                // 범위를 벗어나는지 확인
                if(isMap(nx, ny)) continue;
                // 이전에 번진 구역인지 확인, 벽인지 확인
                if(fireTime[nx][ny] <= time+1 || map[nx][ny] == '#') continue;

                // 확산 가능
                fireTime[nx][ny] = time+1;
                fire.offer(new int[] {nx,ny,time+1});
            }
        }

//        for(int[] arr : fireTime) {
//            System.out.println(Arrays.toString(arr));
//        }
    }

    static boolean isMap(int x, int y) {
        return x < 0 || y < 0 || x >= row || y >= col;
    }
}

/*
    미로를 탈출한다.
    이동은 수직 수평 ( 상 하 좌 우 )

    미로의 가장자리에 접한 공간에서 탈출 가능
 */
