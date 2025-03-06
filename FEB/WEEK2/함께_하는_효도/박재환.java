import java.io.*;
import java.util.*;


public class Main {
    static BufferedReader br;
    static BufferedWriter bw;
    static int mapSize, friendNum;
    static int[][] map;
    static int[][] friends;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Main problem = new Main();

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
