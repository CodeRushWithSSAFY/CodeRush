package MAY.WEEK.토마토;

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
    static int height, width, depth;        // 세로, 가로, 높이
    static int[][][] box;                   // 상자
    static int totalTomato;                 // 전체 토마토의 개수
    static Queue<int[]> q;                  // BFS 로 사용할 큐 [x,y,z,time]
    static void init() throws IOException {
        q = new LinkedList<>();
        totalTomato = 0;

        st = new StringTokenizer(br.readLine().trim());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());
        depth = Integer.parseInt(st.nextToken());
        box = new int[depth][height][width];

        /*
             1 : 익은 토마토
             0 : 익지 않은 토마토
            -1 : 빈칸
         */
        for(int d=0; d<depth; d++) {
            for(int h=0; h<height; h++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int w=0; w<width; w++) {
                    box[d][h][w] = Integer.parseInt(st.nextToken());

                    if(box[d][h][w] == 0) totalTomato++;
                    else if(box[d][h][w] == 1) { // 익은 토마토라면 Queue 에 삽입
                        q.offer(new int[] {h,w,d,0});
                    }
                }
            }
        }
//        System.out.println(totalTomato);
//        for(int[] arr : q) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println();
        getMinDay();
    }

    /*
        BFS 를 기반으로 토마토가 전파되어 나가는 방식으로 탐색

        이동은 상 하 앞 뒤 좌 우
     */
    static int[] dx = {0,1,0,-1,0,0};
    static int[] dy = {1,0,-1,0,0,0};
    static int[] dz = {0,0,0,0,1,-1};
    static void getMinDay() {
        int minDay = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int z = cur[2];
            int time = cur[3];

            minDay = Math.max(minDay, time);

            // 현 위치를 기준으로 익을 수 있는 토마토를 탐색
            for(int dir=0; dir<6; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int nz = z + dz[dir];

                // 범위를 벗어나는지
                if(inBound(nx, ny, nz)) continue;
                // 빈칸이거나, 이미 익은 경우
                if(box[nz][nx][ny] == -1 || box[nz][nx][ny] == 1) continue;

                // 익을 수 있음
                box[nz][nx][ny] = 1;
                totalTomato--;
                q.offer(new int[] {nx, ny, nz, time+1});
            }

//            for(int[] arr : q) {
//                System.out.println(Arrays.toString(arr));
//            }
//            System.out.println();
        }

        System.out.println(totalTomato == 0 ? minDay : -1);
    }

    /*
        현 위치가 범위를 벗어나는지 확인
        true : 범위 벗어남
        false : 범위 내
     */
    static boolean inBound(int x, int y, int z) {
        return x < 0 || y < 0 || z < 0 || z >= depth || x >= height || y >= width;
    }
}

/*
    익은 토마토와 익지 않은 토마토가 있다.
    하루가 지나면, 익은 토마토의 인접한 곳에 있는 익지 않은 토마토를 익힌다.
    인접 : 앞 뒤 양 옆 상 하 ( 3차원 평면 )

    모든 토마토가 익기까지 걸리는 최소 시간
    다 익을 수 없다면 -1 출력
 */
