package FEB_2WEEK.함께하는_효도;

import java.io.*;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int mapSize, friendNum;
    static int[][] map;
    static int[][] friends;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        박재환 problem = new 박재환();

        problem.init();
        bw.write(String.valueOf(problem.getMax()));
        bw.flush();
        bw.close();
    }

    // 초기 입력 받기
    public void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        mapSize = Integer.parseInt(st.nextToken());
        friendNum = Integer.parseInt(st.nextToken());

        map = new int[mapSize][mapSize];
        friends = new int[friendNum][2];

        for(int x=0; x<mapSize; x++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int y=0; y<mapSize; y++) {
                map[x][y] = Integer.parseInt(st.nextToken());
            }
        }

        for(int x=0; x<friendNum; x++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int y=0; y<2; y++) {
                friends[x][y] = Integer.parseInt(st.nextToken())-1;
            }
        }
        br.close();
    }

    int answer;
    public int getMax() {
        answer = Integer.MIN_VALUE;
        // 첫번째 사람
        int x = friends[0][0];
        int y = friends[0][1];
        int defaultSum = map[x][y];
        map[x][y] = 0;
        work(x, y, 0, defaultSum, 0);

        return answer;
    }

    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};
    public void work(int x, int y, int workIdx, int sum, int depth) {
        if(depth == 3) {    // 시간 끝
            if(++workIdx < friendNum) {   // 더 일 할 사람이 남음
                int nx = friends[workIdx][0];
                int ny = friends[workIdx][1];

                int tmp = map[nx][ny];
                map[nx][ny] = 0;
                work(nx, ny, workIdx, sum + tmp, 0);
                map[nx][ny] = tmp;
            } else {    // 사람 끝
                answer = Math.max(answer, sum);
            }
            return;
        }

        for(int dir=0; dir<4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // 범위를 벗어나는 경우
            if(nx < 0 || ny < 0 || nx >= mapSize || ny >= mapSize) continue;

            // 수확 처리
            int tmp = map[nx][ny];
            map[nx][ny] = 0;
            work(nx, ny, workIdx, sum+tmp, depth+1);
            // 처리 해제
            map[nx][ny] = tmp;
        }
    }
}
/*
n x n 격자 모든 칸에 나무가 심어져 있다
상하좌우 인접한 칸으로 한 칸 이동가능
최종적으로 모든 열매 수확량의 합을 최대로 -> 3 초 동안
친구간 같은 칸 방문 X

제약조건
[조건 1] 2 ≤ n ≤ 20
[조건 2] 1 ≤ m ≤ 3
[조건 3] 1 ≤ 가능한 열매 수확량 ≤ 1,000


접근 방법
"동시에 움직인다" Queue 에 둘 다 넣고 돌리기? BFS?
목적지가 없음 -> DFS? 완탐?

움직일 수 있는 범위가 정해져있음

4 2
20 26 185 80
100 20 25 80
20 20 88 99
15 32 44 50
1 2
2 3
 */
