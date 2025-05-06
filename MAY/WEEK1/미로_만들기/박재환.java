package MAY.WEEK1.미로_만들기;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static int minChange;
    static int mapSize;
    static char[][] map;
    static void init() throws IOException {
        minChange = Integer.MAX_VALUE;

        mapSize = Integer.parseInt(br.readLine().trim());
        map = new char[mapSize][mapSize];

        for(int x=0; x<mapSize; x++) {
            String input = br.readLine().trim();
            for(int y=0; y<mapSize; y++) {
                map[x][y] = input.charAt(y);
            }
        }

        findMinChange();
        System.out.println(minChange == Integer.MAX_VALUE ? 0 : minChange);
    }

    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};
    static void findMinChange() {
        Queue<int[]> q = new LinkedList<>();    // [x, y, cnt]
        boolean[][][] visited = new boolean[mapSize][mapSize][mapSize*mapSize];
        visited[0][0][0] = true;
        q.offer(new int[] {0,0,0});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int cnt = cur[2];

            if(x == mapSize-1 && y == mapSize-1) {
                minChange = Math.min(cnt, minChange);
                continue;
            }

            if(cnt >= minChange) continue;

            for(int dir=0; dir<4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int nCnt = cnt;
                if(nx < 0 || ny < 0 || nx >= mapSize || ny >= mapSize) continue;

                if(map[nx][ny] == '0') nCnt++;
                if(visited[nx][ny][nCnt]) continue;

                visited[nx][ny][nCnt] = true;
                q.offer(new int[] {nx, ny, nCnt});
            }
        }

     }
}

/*
    N x N 크기의 격자 ( N 은 최대 50 )
    흰방      : 이동 가능
    검은방    : 이동 불가

    검은방 -> 흰방으로 바꾸어서 목적지로 도달
    최소로 바꿀 수 있는 수
 */
