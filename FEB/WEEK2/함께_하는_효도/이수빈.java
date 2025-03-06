import java.io.*;
import java.util.*;
/*
    함께하는 효도

    n * n 격자, 친구 m명
    3초 동안 최대로 얻을 수 있는 열매 수확량의 총 합 구하기
    
    - 한 나무에 열매는 딱 한 번만 수확이 가능
    - 동시에 같은 나무에 마주치면 안된다.

*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int n, m;
    static int[][] board;
    static boolean[][][] visitedTime;
    static int result = 0;
    static List<int[]> friends = new ArrayList<>();
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public static boolean checkFruits(int row, int col) {
        // 4가 아니라 curTime으로 했었는데 그냥 모두 돌면서 방문했는지 확인해야함..
        for (int i = 0; i < 4; i++) {
            if (visitedTime[row][col][i]) return false;
        }
        return true;
    }
    
    public static void initVisited(int n) {
        visitedTime = new boolean[n][n][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 4; k++)
                    visitedTime[i][j][k] = false;
            }
        }
    }
    
    public static void backTracking(int friendsIndex, int row, int col, int sum, int time) {
        if (time == 3) {
            if (friendsIndex < m - 1) {
                int nextFriendRow = friends.get(friendsIndex + 1)[0];
                int nextFriendCol = friends.get(friendsIndex + 1)[1];
                backTracking(friendsIndex + 1, nextFriendRow, nextFriendCol, sum + board[nextFriendRow][nextFriendCol], 0);
            } else {
                result = Math.max(result, sum);
            }
            return ;
        }

        for (int i = 0; i < 4; i++) {
            int nextRow = row + dx[i];
            int nextCol = col + dy[i];
            if (nextRow < 0 || nextCol < 0 || nextRow >= n || nextCol >= n) continue;

            // 다음 갈 위치에서 해당 시간에 방문하지 않은 경우만 dfs
            if (!visitedTime[nextRow][nextCol][time + 1]) {
                int nextSum = sum;
                // 열매가 남아있다면 열매 수확
                if (checkFruits(nextRow, nextCol)) nextSum += board[nextRow][nextCol];
                visitedTime[nextRow][nextCol][time + 1] = true;
                backTracking(friendsIndex, nextRow, nextCol, nextSum, time + 1);
                visitedTime[nextRow][nextCol][time + 1] = false;
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        initVisited(n);

        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            friends.add(new int[]{row, col});
            // 시작위치는 다 0초
            visitedTime[row][col][0] = true;
        }

        int startRow = friends.get(0)[0];
        int startCol = friends.get(0)[1];
        backTracking(0, startRow, startCol, board[startRow][startCol], 0);

        

        System.out.println(result);
    }
}
