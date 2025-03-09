import java.io.*;
import java.util.*;

public class Main {
    static int[][] map = new int[101][101];
    static int[][] flag = new int[101][101];
    static int[][] floodMap = new int[101][101];
    static int N;
    static int maxHeight;
    static int ans;
    static int[] moveY = {0, 1, 0, -1};
    static int[] moveX = {1, 0, -1, 0};
    
    static void bfs(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x});
        flag[y][x] = 1;
        
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int curY = current[0];
            int curX = current[1];
            
            for (int i = 0; i < 4; i++) {
                int nxtY = curY + moveY[i];
                int nxtX = curX + moveX[i];
                
                if (nxtX < 0 || nxtY < 0 || nxtX >= N || nxtY >= N) continue;
                if (flag[nxtY][nxtX] == 1 || floodMap[nxtY][nxtX] == 1) continue;
                
                flag[nxtY][nxtX] = 1;
                q.add(new int[]{nxtY, nxtX});
            }
        }
    }
    
    static int islandCnt() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (floodMap[i][j] == 0 && flag[i][j] == 0) {
                    bfs(i, j);
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    static void flood(int water) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == water) floodMap[i][j] = 1;
            }
        }
    }
    
    static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(floodMap[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    static void solution() {
        for (int i = 0; i <= maxHeight; i++) {
            // Reset flag array
            for (int y = 0; y < N; y++) {
                Arrays.fill(flag[y], 0);
            }
            
            flood(i);
            int tmp = islandCnt();
            if (tmp > ans) ans = tmp;
        }
    }
    
    static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (maxHeight < map[i][j]) maxHeight = map[i][j];
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(ans);
    }
}
