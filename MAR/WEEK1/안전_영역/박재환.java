package WEEK1.안전_영역;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        init();
    }

    static int mapSize; // 격자의 크기
    static int[][] map; // 격자
    static int minH, maxH; // 강수량의 최소 높이, 최대 높이 -> 탐색 범위를 줄이기 위함

    static void init() throws IOException {
        minH = 100;
        maxH = 0; // 높이는 1 이상 100 이하이다.

        mapSize = Integer.parseInt(br.readLine().trim());
        map = new int[mapSize][mapSize];

        for (int x = 0; x < mapSize; x++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for (int y = 0; y < mapSize; y++) {
                map[x][y] = Integer.parseInt(st.nextToken());
                // 최대값과 최소값을 갱신한다.
                minH = Math.min(minH, map[x][y]);
                maxH = Math.max(maxH, map[x][y]);
            }
        }

        getMaxSafeArea();
    }

    /*
     * 나올 수 있는 최대 안전 지대 수를 구한다.
     * minH -> maxH 까지의 각각 상황에서 안전지대의 수를 구한다.
     */
    static boolean[][] isDrown; // 물에 잠긴 구역과 잠기지 않은 구역을 구분한다.
    static int maxArea; // 구할 수 있는 최대 영역의 수

    static void getMaxSafeArea() {
        maxArea = 1; // 비가 오지 않았을 때, 최대 영역은 1이다.

        // 강우량의 모든 경우를 시뮬레이션한다.
        for (int height = minH; height <= maxH; height++) {
            // 물에 잠긴 지역을 체크한다.
            checkDrown(height);

            // 구역을 구한다.
            countArea();
        }

        System.out.println(maxArea);
    }

    // 현 상태의 안전 구역의 개수를 구한다.
    static void countArea() {
        int area = 0;
        /*
         * BFS 를 사용하여 인접 구역 체크를 하여 하나의 구역을 구한다.
         * 인접한 영역은 상하좌우 를 기준으로 한다.
         */
        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                // 물에 잠긴 지역은 넘어간다.
                if (isDrown[x][y])
                    continue;

                // 물에 잠기지 않은 지역이다. -> 인접한 지역을 체크한다.

                // 1. 현위치를 Queue 에 넣고, 체크처리를 해준다.
                Queue<int[]> q = new LinkedList<>();
                q.offer(new int[] { x, y });
                isDrown[x][y] = true;

                // 2. 현 위치를 기준으로 인접한 구역을 모두 체크한 뒤, 다음 구역으로 넘어간다.
                while(!q.isEmpty()) {
                    int[] curPoint = q.poll();

                    for (int dir = 0; dir < 4; dir++) {
                        int nx = curPoint[0] + dx[dir];
                        int ny = curPoint[1] + dy[dir];

                        // 격자의 범위를 벗어나는 경우
                        if(nx < 0 || ny < 0 || nx >= mapSize || ny >= mapSize) continue;
                        // 이미 체크한 지역의 경우
                        if(isDrown[nx][ny]) continue;

                        isDrown[nx][ny] = true;
                        q.offer(new int[] {nx, ny});
                    }
                }

                area++;
            }
        }

        maxArea = Math.max(area, maxArea);
    }

    // 현재 강우량에 잠긴 지역을 체크한다.
    static void checkDrown(int height) {
        // 매 상황마다 초기화한다.
        isDrown = new boolean[mapSize][mapSize];

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                // 현재 지역의 높이가 강수량보다 작거나 같은 경우 -> 잠긴다.
                if (map[x][y] <= height) {
                    isDrown[x][y] = true;
                }
            }
        }

    }
}

/*
 * N x N 크기의 격자가 주어지고, 각 칸은 높이를 나타낸다.
 * 비의 양에 따른 모든 경우를 조사하여 물에 잠기지 않는 안전 영역의 최대 수를 구하라.
 *
 * 1. 격자를 입력 받으며 최대값과 최소값을 기록한다.
 * 2. 최소값 -> 최대값으로 값을 증가시켜가며, 각 상황에 맞는 안전 지대의 수를 구한다.
 */

