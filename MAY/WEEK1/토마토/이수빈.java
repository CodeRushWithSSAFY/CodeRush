import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int rowSize, colSize, height;
    static int[][][] box;
    static List<int[]> startList;

    static class Tomato {
        int row, col, h, day;

        Tomato(int row, int col, int h, int day) {
            this.row = row;
            this.col = col;
            this.h = h;
            this.day = day;
        }
    }
    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        colSize = Integer.parseInt(st.nextToken());
        rowSize = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        box = new int[rowSize][colSize][height];
        startList = new ArrayList<>();

        for (int h = 0; h < height; h++) {
            for (int row = 0; row < rowSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < colSize; col++) {
                    box[row][col][h] = Integer.parseInt(st.nextToken());
                    if (box[row][col][h] == 1) {
                        startList.add(new int[]{row, col, h});
                    }
                }
            }
        }
    }

    public static void bfs() {
        Queue<Tomato> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[rowSize][colSize][height];
        for (int idx = 0; idx < startList.size(); idx++) {
            int[] cur = startList.get(idx);
            q.offer(new Tomato(cur[0], cur[1], cur[2], 1));
            visited[cur[0]][cur[1]][cur[2]] = true;
        }

        int[] dr = {0, 0, -1, 1};
        int[] dc =  {1, -1, 0, 0};
        int[] dh = {-1, 1};

        while (!q.isEmpty()) {
            Tomato cur = q.poll();

            // 상 하 좌 우
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = cur.row + dr[dir];
                int nextCol = cur.col + dc[dir];

                if (nextRow < 0 || nextCol < 0 || nextRow >= rowSize || nextCol >= colSize) continue;

                if (!visited[nextRow][nextCol][cur.h] && box[nextRow][nextCol][cur.h] == 0) {
                    visited[nextRow][nextCol][cur.h] = true;
                    box[nextRow][nextCol][cur.h] = cur.day + 1;
                    q.offer(new Tomato(nextRow, nextCol, cur.h, cur.day + 1));
                }
            }
            // 위 아래
            for (int dir = 0; dir < 2; dir++) {
                int nextHeight = cur.h + dh[dir];

                if (nextHeight < 0 || nextHeight >= height) continue;

                if (!visited[cur.row][cur.col][nextHeight] && box[cur.row][cur.col][nextHeight] == 0) {
                    visited[cur.row][cur.col][nextHeight] = true;
                    box[cur.row][cur.col][nextHeight] = cur.day + 1;
                    q.offer(new Tomato(cur.row, cur.col, nextHeight, cur.day + 1));
                }
            }
        }
    }

    public static int checkTomato() {
        int maxDay = 1;
        for (int h = 0; h < height; h++) {
            for (int row = 0; row < rowSize; row++) {
                for (int col = 0; col < colSize; col++) {
                    if (box[row][col][h] == 0) return -1;
                    maxDay = Math.max(maxDay, box[row][col][h]);
                }
            }
        }
        return maxDay - 1;
    }
    public static void main(String[] args) throws IOException {
        init();
        bfs();
        System.out.println(checkTomato());
    }
}
