import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Main {
    static int boardSize;
    static int[][] board;
    static int result;

    static class Pos {
        int row, col, blackCnt;
        Pos (int row, int col, int blackCnt) {
            this.row = row;
            this.col = col;
            this.blackCnt = blackCnt;
        }
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boardSize = Integer.parseInt(br.readLine().trim());
        board = new int[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            String line = br.readLine().trim();
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = line.charAt(col) - '0';
            }
        }
    }

    public static void bfs() {
        Deque<Pos> q = new ArrayDeque<>();
        q.offerFirst(new Pos(0, 0, 0));
        int[][] visited = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            Arrays.fill(visited[i], Integer.MAX_VALUE);
        }
        visited[0][0] = 0;

        int[] dr = {0, 0, -1, 1};
        int[] dc = {1, -1, 0, 0};

        while (!q.isEmpty()) {
            Pos cur = q.pollFirst();

            if (cur.row == boardSize - 1 && cur.col == boardSize - 1) {
                result = cur.blackCnt;
                return ;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nextRow = cur.row + dr[dir];
                int nextCol = cur.col + dc[dir];

                if (nextRow < 0 || nextCol < 0 || nextRow >= boardSize || nextCol >= boardSize) continue;

                int nextBlackCnt = cur.blackCnt;
                if (board[nextRow][nextCol] == 0) nextBlackCnt++;

                if (visited[nextRow][nextCol] <= nextBlackCnt) continue;

                visited[nextRow][nextCol] = nextBlackCnt;
                if (board[nextRow][nextCol] == 0) {
                    q.offerLast(new Pos(nextRow, nextCol, nextBlackCnt));
                } else {
                    q.offerFirst(new Pos(nextRow, nextCol, nextBlackCnt));
                }

            }
        }
    }
    public static void main(String[] args) throws IOException {
        init();
        bfs();
        System.out.println(result);
    }
}
