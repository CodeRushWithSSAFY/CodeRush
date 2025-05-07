import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static int[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String line = st.nextToken();
            for (int j = 0; j < n; j++) {
                board[i][j] = line.charAt(j) - '0';
            }
        }

        System.out.println(bfs(n));
    }

    private static int bfs(int n) {
        int[][][] visited = new int[2][n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(visited[0][row], 300);
            Arrays.fill(visited[1][row], 300);
        }
        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 0});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int curRow = cur[0];
            int curCol = cur[1];
            int curCnt = cur[2];

            for (int dir = 0; dir < 4; dir++) {
                int nxtRow = curRow + dy[dir];
                int nxtCol = curCol + dx[dir];

                if (nxtRow < 0 || n <= nxtRow || nxtCol < 0 || n <= nxtCol) continue;
                // 다음 칸이 빈칸
                if (board[nxtRow][nxtCol] == 1) {
                    if (visited[0][nxtRow][nxtCol] <= curCnt) continue;
                    q.add(new int[]{nxtRow, nxtCol, curCnt});
                    visited[0][nxtRow][nxtCol] = curCnt;
                } else if (board[nxtRow][nxtCol] == 0) {
                    if (visited[1][nxtRow][nxtCol] <= curCnt + 1) continue;
                    q.add(new int[]{nxtRow, nxtCol, curCnt + 1});
                    visited[1][nxtRow][nxtCol] = curCnt + 1;
                }
            }
        }

        return (board[n - 1][n - 1] == 1) ? visited[0][n - 1][n - 1] : visited[1][n - 1][n - 1];
    }
}
