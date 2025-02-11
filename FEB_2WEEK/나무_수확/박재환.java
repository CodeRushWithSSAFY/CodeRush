package FEB_2WEEK.나무_수확;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int mapSize;
    //    static int[][] map;
    static long[][][] map;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        박재환 problem = new 박재환();
        problem.init(); // input
        bw.write(String.valueOf(problem.findMax()));
        bw.flush();
        bw.close();
    }

    public void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        mapSize = Integer.parseInt(br.readLine().trim());
//        map = new int[mapSize][mapSize];
        map = new long[mapSize+1][mapSize+1][2]; // [x][y][0] : 해당 위치까지의 최대 누적 값, [x][y][1] : 스프링 쿨러 설치 좌표 값

        for(int x=1; x<=mapSize; x++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for(int y=1; y<=mapSize; y++){
//                map[x][y] = Integer.parseInt(st.nextToken());
                map[x][y][0] = Integer.parseInt(st.nextToken());
                map[x][y][1] = map[x][y][0];
            }
        }
        br.close();
    }

    // ❌ BFS 를 사용하면 시간 초과
    int[] dx = {0,1};
    int[] dy = {1,0};
    public long findMax() {
//        long maxValue = 0;
//
//        Queue<int[]> q = new ArrayDeque<>();    // [x,y,sum,max]
//        q.offer(new int[] {0,0,map[0][0],map[0][0]});
//
//        while(!q.isEmpty()) {
//            int[] cur = q.poll();
//            int x = cur[0];
//            int y = cur[1];
//            int sum = cur[2];
//            int max = cur[3];
//
//            if(x==mapSize-1 && y==mapSize-1) {
//                maxValue = Math.max(maxValue, sum+max);
//                continue;
//            }
//
//            for(int dir=0; dir<2; dir++) {
//                int nx = x + dx[dir];
//                int ny = y + dy[dir];
//
//                // 범위를 벗어난다면
//                if(nx < 0 || ny < 0 || nx >= mapSize || ny >= mapSize) continue;
//
//                q.offer(new int[] {nx, ny, sum+map[nx][ny], Math.max(max, map[nx][ny])});
//            }
//        }
//
//        return maxValue;

        // ⚠️ 부분합을 이용한 풀이
        getRangeSum();

        return map[mapSize][mapSize][0] +
                Math.max(
                        map[mapSize-1][mapSize][0] + Math.max(map[mapSize-1][mapSize][1], map[mapSize][mapSize][1]),
                        map[mapSize][mapSize-1][0] + Math.max(map[mapSize][mapSize-1][1], map[mapSize][mapSize][1])
                );
    }

    public void getRangeSum() {
        for(int x=1; x<=mapSize; x++){
            for(int y=1; y<=mapSize; y++) {
                if(x == mapSize && y == mapSize) { // 마지막 칸 제외 탐색 완료
                    return;
                }

                if(map[x-1][y][0] > map[x][y-1][0]) {   // 위에서 내려오는 값이, 좌측에서 오는 값보다 크다면
                    map[x][y][0] += map[x-1][y][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], map[x-1][y][1]);  // 최대값을 갱신한다.
                } else if(map[x-1][y][0] < map[x][y-1][0]){ // 위에서 내려오는 값이, 좌측에서 오는 값보다 작다면
                    map[x][y][0] += map[x][y-1][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], map[x][y-1][1]);  // 최대값을 갱신한다.
                } else {    // 같다면, 스프링쿨러 값이 더 큰 쪽으로 수로를 낸다.
                    map[x][y][0] += map[x][y-1][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], Math.max(map[x][y-1][1], map[x-1][y][1]));  // 최대값을 갱신한다.
                }
            }
        }
    }
}

/*
n x n 격자 각 칸에는 얻을 수 있는 열매 수확량이 주어진다.
씨앗을 키우기 위해 수로를 설치해야함 (1,1) -> 실 (0,0) 에서부터 수로 설치
우측 혹은 하단 선택하여 (n,n) -> 실(n-1,n-1) 까지 수로를 설치해야함

dfs/BFS 를 통해 최대값을 구한다?
n 의 범위가 좀 큰거같긴한데용
 */
