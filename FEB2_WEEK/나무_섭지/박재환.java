package FEB_2WEEK.나무_섭지;

import java.io.*;
import java.util.*;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int height, width;
    static char[][] map;
    static int[] people;
    static int[] exit;
    static List<int[]> ghosts;
    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};
    public static void main(String[] args) throws IOException {
        박재환 problem = new 박재환();
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        problem.init();

        br.close();
        boolean answer = problem.humanMove();
        bw.write(answer ? "Yes" : "No");
        bw.flush();
        bw.close();
    }

    public void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        map = new char[height][width];

        ghosts = new LinkedList<>(); // 유령의 위치 (있을수도 있고, 없을 수도 있음)
        
        for(int x=0; x<height; x++) {
            String mapInput = br.readLine().trim();
            for(int y=0; y<width; y++) {
                map[x][y] = mapInput.charAt(y);

                if(map[x][y] == 'N') { // 남우의 위치
                    people = new int[] {x,y};
                } else if(map[x][y] == 'G') { // 유령의 위치 
                    ghosts.add(new int[]{x,y});
                } else if(map[x][y] == 'D') { // 출구의 위치
                    exit = new int[] {x,y};
                }
            }
        }
    }

    int[][] ghostMap;
    public void ghostsMove() { // 유령이 출구까지 이동하는데 걸리는 시간을 기록한다
        ghostMap = new int[height][width];
        boolean[][] ghostVistied = new boolean[height][width];
        for(int idx=0; idx<height; idx++) {
            Arrays.fill(ghostMap[idx], Integer.MAX_VALUE);
        }
        
        Queue<int[]> q = new ArrayDeque<>();

        for(int[] ghost : ghosts) { 
            q.offer(new int[] {ghost[0], ghost[1], 0}); // [x, y, 시간]
        }

        while(!q.isEmpty()) { // BFS
            int[] points = q.poll();

            int x = points[0];
            int y = points[1];
            int time = points[2];

            if(x == exit[0] && y == exit[1]){
                ghostMap[x][y] = Math.min(ghostMap[x][y], time);
                return; // 출구에 도착하면 종료 -> 더 이상 탐색 필요 없음   
            } 
            if(ghostVistied[x][y]) continue;

            ghostVistied[x][y] = true;
            ghostMap[x][y] = Math.min(ghostMap[x][y], time);

            for(int dir=0; dir<4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx<0 || ny<0 || nx >= height || ny >= width || ghostVistied[nx][ny]) continue;

                q.offer(new int[] {nx, ny, time+1});
            }
        }
    }

    public boolean humanMove() { // 남우가 출구까지 걸리는 시간을 확인한다
        ghostsMove();
        
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] humanVisited = new boolean[height][width];
        
        q.offer(new int[] {people[0], people[1], 0});

         while(!q.isEmpty()) {
            int[] points = q.poll();

            int x = points[0];
            int y = points[1];
            int time = points[2];

            if(x == exit[0] && y == exit[1]) {
                return ghostMap[x][y] > time; // 출구에 도착했을 때, 유령보다 빨리 도착했는지 확인 
            }
            if(humanVisited[x][y]) continue;

            humanVisited[x][y] = true;

            for(int dir=0; dir<4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx<0 || ny<0 || nx >= height || ny >= width || humanVisited[nx][ny] || map[nx][ny] == '#') continue;

                q.offer(new int[] {nx, ny, time+1});
            }
        } 
        return false;
    }
    
}
/*
문제 요약
남우는 유령보다 빨리 출구로 이동해야한다. 

유령은 있을수도 있고, 없을 수도 있다.
유령은 벽 통과가 가능하다

남우와 유령 모두 상하좌우 로만 움직인다.

접근 
유령과, 남우 모두 BFS 를 사용해 출구까지 걸리는 시간을 확인한다
-> 유령보다만 빨리 출구에 도착하면 된다
*/