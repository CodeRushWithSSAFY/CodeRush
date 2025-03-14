import java.util.Queue;
import java.util.ArrayDeque;

class Solution {
    int rowSize, colSize;
    boolean[][] visited;
    boolean[] visitedCol;
    int[] colSum;
    
    int[] dr = {0, 0, -1, 1};
    int[] dc = {1, -1, 0, 0};

    public int bfs(int row, int col, int[][] land) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {row, col});
        visited[row][col] = true;
        
        int cnt = 0;
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            cnt++;
            if (!visitedCol[cur[1]]) visitedCol[cur[1]] = true;
            
            
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = cur[0] + dr[dir];
                int nextCol = cur[1] + dc[dir];
                
                if (nextRow < 0 || nextCol < 0 || nextRow >= rowSize || nextCol >= colSize) continue;
                if (!visited[nextRow][nextCol] && land[nextRow][nextCol] == 1) {
                    q.offer(new int[] {nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                }
            }
        }
        return cnt;
    }
    
    public int solution(int[][] land) {
        int answer = 0;
        rowSize = land.length;
        colSize = land[0].length;
        visited = new boolean[rowSize][colSize];
        colSum = new int[colSize];
        
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                if (land[row][col] == 1 && !visited[row][col]) {
                    visitedCol = new boolean[colSize];
                    int amount = bfs(row, col, land);
                    
                    for (int idx = 0; idx < colSize; idx++) {
                        if (visitedCol[idx]) colSum[idx] += amount;
                    }
                }
            }
        }
        
        for (int idx = 0; idx < colSize; idx++) {
            answer = Math.max(answer, colSum[idx]);
        }
        return answer;
    }
}
