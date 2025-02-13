import java.io.*;
import java.util.*;

/**
 나무 섭지

 출구에 도착하는 순간 유령과 마주치는 경우에도 탈출 실패
 유령은 필요에 따라 움직이지 않아도 됨
 */
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int n, m;
    static char[][] board;
    static int minDist = Integer.MAX_VALUE;

    public static void bfs(int startRow, int startCol, int exitRow, int exitCol) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];
        int[] dx = new int[]{0, 0, -1, 1};
        int[] dy = new int[]{1, -1, 0, 0};

        q.add(new int[]{startRow, startCol, 1});
        visited[startRow][startCol] = true;

        while(!q.isEmpty()) {
            int[] front = q.poll();

            if (front[0] == exitRow && front[1] == exitCol) {
                minDist = front[2];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = front[0] + dx[i];
                int nextCol = front[1] + dy[i];

                if (nextRow < 0 || nextCol < 0 || nextRow >= n || nextCol >= m) continue;
                if (!visited[nextRow][nextCol] && board[nextRow][nextCol] != '#') {
                    visited[nextRow][nextCol] = true;
                    q.add(new int[]{nextRow, nextCol, front[2] + 1});
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];

        String str;
        int startRow = 0, startCol = 0, exitRow = 0, exitCol = 0;
        List<int[]> ghosts = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            str = br.readLine().trim();
            for (int col = 0; col < m; col++) {
                board[row][col] = str.charAt(col);

                // 남우
                if (board[row][col] == 'N') {
                    startRow = row;
                    startCol = col;
                }

                // 유령 좌표
                if (board[row][col] == 'G') {
                    ghosts.add(new int[]{row, col});
                }

                // 출구
                if (board[row][col] == 'D') {
                    exitRow = row;
                    exitCol = col;
                }
            }
        }

        // 남우가 출구까지 갈 수 있는 최소거리 구하기
        bfs(startRow, startCol, exitRow, exitCol);

        // 출구에 도달할 수 없는 경우
        if (minDist == Integer.MAX_VALUE) {
            System.out.println("No");
            return ;
        }

        // 유령 좌표 돌면서 남우의 최소거리보다 작거나 같으면 유령에게 잡힐 수 있다.
        for (int[] pos : ghosts) {
            int ghostDist = Math.abs(exitRow - pos[0]) + Math.abs(exitCol - pos[1]) + 1;
            if (ghostDist <= minDist) {
                System.out.println("No");
                return ;
            }
        }

        System.out.println("Yes");


    }
}
