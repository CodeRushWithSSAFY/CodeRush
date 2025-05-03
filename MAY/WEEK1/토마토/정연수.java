import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int col = Integer.parseInt(st.nextToken());
        int row = Integer.parseInt(st.nextToken());
        int height = Integer.parseInt(st.nextToken());

        int[][][] box = new int[height][row][col];

        int goal = 0;
        ArrayDeque<int[]> tomatoes = new ArrayDeque<>();
        for (int h = 0; h < height; h++) {
            for (int r = 0; r < row; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < col; c++) {
                    box[h][r][c] = Integer.parseInt(st.nextToken());
                    if (box[h][r][c] == 1) {
                        tomatoes.add(new int[]{h, r, c});
                    }
                    if (box[h][r][c] == 0) {
                        goal++;
                    }
                }
            }
        }

        bfs(box, tomatoes, goal);
    }

    private static void bfs(int[][][] box, ArrayDeque<int[]> tomatoes, int goal) {
        int[][][] visited = new int[box.length][box[0].length][box[0][0].length];
        int[][] directions = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};
        int day = 0;

        while (!tomatoes.isEmpty()) {
            int size = tomatoes.size();
            for (int i = 0; i < size; i++) {
                int[] tomato = tomatoes.poll();
                for (int[] direction : directions) {
                    int h = tomato[0] + direction[0];
                    int r = tomato[1] + direction[1];
                    int c = tomato[2] + direction[2];

                    if (h >= 0 && h < box.length && r >= 0 && r < box[0].length && c >= 0 && c < box[0][0].length
                            && box[h][r][c] == 0 && visited[h][r][c] == 0) {
                        visited[h][r][c] = 1;
                        tomatoes.add(new int[]{h, r, c});
                        goal--;
                    }
                }
            }
            day++;
        }

        System.out.println(goal == 0 ? day - 1 : -1);
    }
}
