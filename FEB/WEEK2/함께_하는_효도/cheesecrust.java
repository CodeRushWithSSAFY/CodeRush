import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N, M;
    private static int[][] map;
    private static int[][] visit;
    private static int[][] playerQ;
    private static int maxValue;
    private static int[] dy = {-1, 1, 0, 0};
    private static int[] dx = {0, 0, 1, -1};
    
    public static void dfs(int y, int x, int cnt, int total, int idx) {
        if (cnt == 3) {
            if (idx != M - 1) {
                int nxtY = playerQ[idx + 1][0];
                int nxtX = playerQ[idx + 1][1];
                int tmp = map[nxtY][nxtX];
                map[nxtY][nxtX] = 0;
                dfs(nxtY, nxtX, 0, total + tmp, idx + 1);
                map[nxtY][nxtX] = tmp;
            } else {
                maxValue = Math.max(total, maxValue);
            }
            return;
        }
        for (int i = 0; i < 4; i++) {
            int nxtY = y + dy[i];
            int nxtX = x + dx[i];
            if (nxtY <= 0 || N < nxtY || nxtX <= 0 || N < nxtX) continue;
            int tmp = map[nxtY][nxtX];
            map[nxtY][nxtX] = 0;
            dfs(nxtY, nxtX, cnt + 1, total + tmp, idx);
            map[nxtY][nxtX] = tmp;
        }
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N + 1][N + 1];
        visit = new int[N + 1][N + 1];
        playerQ = new int[M][2];
        
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            playerQ[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};            
        }

        int tmp = map[playerQ[0][0]][playerQ[0][1]];
        map[playerQ[0][0]][playerQ[0][1]] = 0;
        dfs(playerQ[0][0], playerQ[0][1], 0, tmp, 0);
        System.out.println(maxValue);
    }
}
