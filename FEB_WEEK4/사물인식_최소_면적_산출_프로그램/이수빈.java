import java.io.*;
import java.util.*;

public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int dotCnt, colorCnt;
    static List<int[]>[] position; // position[color] = {{x, y}, {x, y}...}
    static int minArea;

    public static void init() throws Exception {
        st = new StringTokenizer(br.readLine().trim());

        dotCnt = Integer.parseInt(st.nextToken());
        colorCnt = Integer.parseInt(st.nextToken());
        position = new ArrayList[colorCnt];
        for (int i = 0; i < colorCnt; i++) position[i] = new ArrayList<>();

        minArea = Integer.MAX_VALUE;
        for (int idx = 0; idx < dotCnt; idx++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int color = Integer.parseInt(st.nextToken()) - 1;

            position[color].add(new int[]{x, y});
        }

    }

    public static void dfs(int color, int maxX, int maxY, int minX, int minY) {
        if (color == colorCnt) {
            minArea = Math.min((maxX - minX) * (maxY - minY), minArea);
            return ;
        }

        for (int[] pos : position[color]) {
            int curMinX = Math.min(minX, pos[0]);
            int curMaxX = Math.max(maxX, pos[0]);
            int curMinY = Math.min(minY, pos[1]);
            int curMaxY = Math.max(maxY, pos[1]);
            int area = (curMaxX - curMinX) * (curMaxY - curMinY);
            if (minArea <= area) continue;
            dfs(color + 1, curMaxX, curMaxY, curMinX, curMinY);
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        dfs(0, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.println(minArea);
    }
}
